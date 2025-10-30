 package com.theatermgnt.theatermgnt.configuration;

 import com.theatermgnt.theatermgnt.dto.request.UserAccountCreationRequest;
 import com.theatermgnt.theatermgnt.entity.Account;
 import com.theatermgnt.theatermgnt.enums.Role;
 import com.theatermgnt.theatermgnt.repository.AccountRepository;
 import com.theatermgnt.theatermgnt.service.RegistrationService;
 import lombok.AccessLevel;
 import lombok.RequiredArgsConstructor;
 import lombok.experimental.FieldDefaults;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.boot.ApplicationRunner;
 import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.crypto.password.PasswordEncoder;

 import java.util.HashSet;

 @Configuration
 @RequiredArgsConstructor
 @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
 @Slf4j
 public class ApplicationInitConfig {

    private PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring.datasource",
            name = "driver-class-name",
            havingValue = "org.postgresql.Driver")
    ApplicationRunner applicationRunner(AccountRepository accountRepository, RegistrationService registrationService) {
        return args -> {
            // Check existed username "admin"
            if(accountRepository.findByUsername("admin").isEmpty()) {

                var adminRequest = UserAccountCreationRequest.builder()
                        .username("admin")
                        .password("admin")
                        .build();
                registrationService.registerNewUser(adminRequest);
                log.warn("Admin account has been created with default password: admin, please change it");
            }
        };
    }
 }
