package com.theatermgnt.theatermgnt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theatermgnt.theatermgnt.dto.ApiResponse;
import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.CustomerAccountResponse;
import com.theatermgnt.theatermgnt.service.RegistrationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegistrationController {
    RegistrationService registrationService;

    @PostMapping("/registration")
    public ApiResponse<CustomerAccountResponse> registerAccount(@RequestBody CustomerAccountCreationRequest request) {
        return ApiResponse.<CustomerAccountResponse>builder()
                .result(registrationService.registerCustomerAccount(request))
                .build();
    }
}
