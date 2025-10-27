package com.theatermgnt.theatermgnt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theatermgnt.theatermgnt.service.AccountService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountController {
    AccountService accountService;

    //    @PostMapping("/registration")
    //    public ApiResponse<CustomerAccountResponse> createCustomerAccount(@RequestBody CustomerAccountCreationRequest
    // request) {
    //        return ApiResponse.<CustomerAccountResponse>builder()
    //                .result(accountService.createCustomerAccount(request))
    //                .build();
    //    }
}
