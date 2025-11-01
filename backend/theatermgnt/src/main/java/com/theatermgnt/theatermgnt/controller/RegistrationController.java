package com.theatermgnt.theatermgnt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.theatermgnt.theatermgnt.dto.ApiResponse;
import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.BaseUserResponse;
import com.theatermgnt.theatermgnt.service.RegistrationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegistrationController {
    RegistrationService registrationService;

    @PostMapping("/register")
    public ApiResponse<BaseUserResponse> registerCustomerAccount(@RequestBody CustomerAccountCreationRequest request) {
        return ApiResponse.<BaseUserResponse>builder()
                .result(registrationService.registerCustomerAccount(request))
                .build();
    }

}
