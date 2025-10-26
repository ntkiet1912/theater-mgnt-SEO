package com.theatermgnt.theatermgnt.configuration;

import com.theatermgnt.theatermgnt.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
//
//    @Autowired
//    private CustomJwtDecoder customJwtDecoder;
//
//    @Bean
//    public JwtDecoder jwtDecoder() {}
}
