package com.theatermgnt.theatermgnt.authentication.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.theatermgnt.theatermgnt.authentication.dto.request.*;
import com.theatermgnt.theatermgnt.authentication.dto.response.AuthenticationResponse;
import com.theatermgnt.theatermgnt.authentication.dto.response.IntrospectResponse;
import com.theatermgnt.theatermgnt.account.entity.Account;
import com.theatermgnt.theatermgnt.authentication.entity.InvalidatedToken;
import com.theatermgnt.theatermgnt.authentication.entity.OtpToken;
import com.theatermgnt.theatermgnt.authentication.event.PasswordResetEvent;
import com.theatermgnt.theatermgnt.authentication.repository.OtpTokenRepository;
import com.theatermgnt.theatermgnt.staff.entity.Staff;
import com.theatermgnt.theatermgnt.authentication.enums.AccountType;
import com.theatermgnt.theatermgnt.common.exception.AppException;
import com.theatermgnt.theatermgnt.common.exception.ErrorCode;
import com.theatermgnt.theatermgnt.account.repository.AccountRepository;
import com.theatermgnt.theatermgnt.authentication.repository.InvalidatedTokenRepository;
import com.theatermgnt.theatermgnt.staff.repository.StaffRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    AccountRepository accountRepository;
    StaffRepository staffRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    ApplicationEventPublisher eventPublisher;
    OtpTokenRepository otpTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${otp.valid-duration}")
    protected long OTP_VALID_DURATION;

    /// INTROSPECT
    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        boolean isValid = true;

        // SIGNED JWT = Verify token
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            log.error("Token verification failed: {}", e.getMessage());
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();

    }
    /// AUTHENTICATE
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var account = accountRepository.findByUsernameOrEmailOrPhoneNumber(request.getLoginIdentifier(),
                request.getLoginIdentifier(), request.getLoginIdentifier())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());
        if(!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(account);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    /// LOGOUT METHOD
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try{
            var signToken = verifyToken(request.getToken(), true);
            String jti = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder()
                            .id(jti)
                            .expiryTime(expiryTime)
                            .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token already expired: {}", e.getMessage());
        }
    }

    /// REFRESH TOKEN
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken) throws ParseException, JOSEException {
        // Step 1: Check validation of the token.
        var signedJWT = verifyToken(refreshToken.getToken(), true);

        // Step 2: Invalidate old token
        var jti = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder()
                        .id(jti)
                        .expiryTime(expiryTime)
                        .build();
        invalidatedTokenRepository.save(invalidatedToken);

        // Step 3: Generate new token based on current user
        var username = signedJWT.getJWTClaimsSet().getSubject();
        var account = accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        var token = generateToken(account);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }


    /// FORGOT PASSWORD
    public void forgotPassword(ForgotPasswordRequest request) {
        var account = accountRepository.findByUsernameOrEmailOrPhoneNumber(
                request.getLoginIdentifier(),
                request.getLoginIdentifier(),
                request.getLoginIdentifier()
        );

        // Does not throw exception if not found
        if(account.isEmpty()) {
            log.info("Password reset requested for non-existing account: {}", request.getLoginIdentifier());
            return; // Exit silently to prevent user enumeration
        }

        Account acc = account.get();
        otpTokenRepository.findByAccount(acc).ifPresent(otpTokenRepository::delete);

        // Generate otp code
        String otpCode = generateOtpCode();
        Instant expiryTime = Instant.now().plus(OTP_VALID_DURATION, ChronoUnit.MINUTES);

        // Save OTP into database
        OtpToken newOtpToken = OtpToken.builder()
                .account(acc)
                .code(otpCode)
                .expiryTime(expiryTime)
                .build();
        otpTokenRepository.save(newOtpToken);

        // Publish event to send email
        try{
            eventPublisher.publishEvent(new PasswordResetEvent(
                    this,
                    account.get(),
                    otpCode
            ));
            log.info("Password reset requested for account: {}", account.get().getEmail());
        } catch (Exception e){
            // Log error but do not throw to avoid user enumeration
            log.error("Error publishing password reset event: {}", e.getMessage());
        }
    }

    /// RESET PASSWORD
    public void resetPassword(ResetPasswordRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var account = accountRepository.findByUsernameOrEmailOrPhoneNumber(
                request.getLoginIdentifier(),
                request.getLoginIdentifier(),
                request.getLoginIdentifier())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Get saved OTP from database
        OtpToken savedOtpToken = otpTokenRepository.findByAccount(account)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        // Validate OTP code and expiry
        if(savedOtpToken.getExpiryTime().isBefore(Instant.now())) {
            otpTokenRepository.delete(savedOtpToken);
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }

        if(!savedOtpToken.getCode().equals(request.getOtpCode())) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }
        // OTP is valid - proceed to reset password
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);

        // Delete used OTP
        otpTokenRepository.delete(savedOtpToken);
        log.info("Password has been reset for user: {}", account.getEmail());
    }

    /// VERIFY TOKEN
    private SignedJWT verifyToken(String token, boolean isRefreshToken) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime =(isRefreshToken)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.MILLIS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if(!(verified && expiryTime.after(new Date())) ) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Case:User has logged out -> token is invalidated
        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    /// GENERATE TOKEN
    private String generateToken(Account account) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        String scope = "";
        if(account.getAccountType() == AccountType.INTERNAL) {
            Staff staff = staffRepository.findByAccountId(account.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            if(staff != null) {
                scope = buildScope(staff);
            }else{
                log.warn("Account {} has USER type but no matching User profile found.", account.getId());
            }
        } else{
            scope = "ROLE_CUSTOMER";
        }

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("theater-mgnt.com")
                .subject(account.getId())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                )
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", scope)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
    /// BUILD SCOPE
    private String buildScope(Staff staff) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(staff.getRoles()))
            staff.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());

                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
            });

        return stringJoiner.toString();
    }


    /// GENERATE OTP CODE
    private String generateOtpCode() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

}

