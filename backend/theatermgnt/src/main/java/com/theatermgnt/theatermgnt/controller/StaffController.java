package com.theatermgnt.theatermgnt.controller;

import com.theatermgnt.theatermgnt.dto.ApiResponse;
import com.theatermgnt.theatermgnt.dto.request.StaffAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.AccountResponse;
import com.theatermgnt.theatermgnt.service.RegistrationService;
import com.theatermgnt.theatermgnt.service.StaffService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StaffController {
    StaffService staffService;
    RegistrationService registrationService;

    @PostMapping
    public ApiResponse<AccountResponse> createStaff(@RequestBody StaffAccountCreationRequest request) {
        return ApiResponse.<AccountResponse>builder()
                .result(registrationService.registerStaffAccount(request))
                .build();
    }
}
