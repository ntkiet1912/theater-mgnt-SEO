package com.theatermgnt.theatermgnt.notification.listener;

import com.theatermgnt.theatermgnt.authentication.dto.request.ResetPasswordRequest;
import com.theatermgnt.theatermgnt.authentication.event.PasswordResetEvent;
import com.theatermgnt.theatermgnt.notification.dto.request.Recipient;
import com.theatermgnt.theatermgnt.notification.dto.request.SendEmailRequest;
import com.theatermgnt.theatermgnt.notification.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationEventListener {
    EmailService emailService;
    @NonFinal
    @Value("${otp.valid-duration}")
    protected long OTP_VALID_DURATION;

    @Async
    @EventListener
    public void handlePasswordResetEvent(PasswordResetEvent event) {
        log.info("Handling password reset OTP event for email: {}", event.getAccount().getEmail());
        String subject = "Cifastar: Mã OTP đặt lại mật khẩu của bạn";
        // Nội dung email chỉ chứa mã OTP
        String htmlContent = "<h1>Yêu cầu Đặt lại Mật khẩu</h1>"
                + "<p>Chào " + event.getAccount().getUsername() + ",</p>" // Hoặc tên Customer
                + "<p>Mã OTP để đặt lại mật khẩu của bạn là:</p>"
                + "<h2 style='font-size: 24px; letter-spacing: 2px;'>" + event.getOtpCode() + "</h2>" // Gửi mã OTP
                + "<p>Mã này sẽ hết hạn sau " + OTP_VALID_DURATION +  " phút.</p>"
                + "<p>Nếu bạn không yêu cầu điều này, vui lòng bỏ qua email này.</p>";

        Recipient recipient = Recipient.builder()
                .email(event.getAccount().getEmail())
                .name(event.getAccount().getUsername())
                .build();

        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .to(recipient)
                .subject(subject)
                .htmlContent(htmlContent)
                .build();

        try {
            emailService.sendEmail(sendEmailRequest);
            log.info("Password reset email sent successfully to {}", event.getAccount().getEmail());
        } catch (Exception e) {
            log.error("Failed to send password reset email to {}: {}", event.getAccount().getEmail(), e.getMessage());
        }

    }
}
