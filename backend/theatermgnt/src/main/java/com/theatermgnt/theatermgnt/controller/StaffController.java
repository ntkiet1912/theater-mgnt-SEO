package com.theatermgnt.theatermgnt.controller;

import com.theatermgnt.theatermgnt.dto.ApiResponse;
import com.theatermgnt.theatermgnt.dto.request.StaffAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.StaffProfileUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.AccountResponse;
import com.theatermgnt.theatermgnt.service.RegistrationService;
import com.theatermgnt.theatermgnt.service.StaffService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{staffId}")
    public ApiResponse<AccountResponse> getStaffProfile(@PathVariable String staffId) {
        return ApiResponse.<AccountResponse>builder()
                .result(staffService.getStaffProfile(staffId))
                .build();
    }

    @GetMapping
    public ApiResponse<List<AccountResponse>> getAll() {
        return ApiResponse.<List<AccountResponse>>builder()
                .result(staffService.getAll())
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<AccountResponse> getMyInfo() {
        return ApiResponse.<AccountResponse>builder()
                .result(staffService.getMyInfo())
                .build();
    }

    @PutMapping("/{staffId}")
    public ApiResponse<AccountResponse> updateStaffProfile(
            @PathVariable String staffId, @RequestBody StaffProfileUpdateRequest request) {
        return ApiResponse.<AccountResponse>builder()
                .result(staffService.updateStaffProfile(staffId, request))
                .build();
    }
    @DeleteMapping("/{staffId}")
    public ApiResponse<Void> deleteStaff(@PathVariable String staffId) {
        staffService.deleteStaff(staffId);
        return ApiResponse.<Void>builder().build();
    }
}
