package com.theatermgnt.theatermgnt.controller;

import org.springframework.web.bind.annotation.*;

import com.theatermgnt.theatermgnt.dto.ApiResponse;
import com.theatermgnt.theatermgnt.dto.request.CustomerAccountUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.AccountResponse;
import com.theatermgnt.theatermgnt.service.CustomerService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CustomerController {
    CustomerService customerService;

//    @PutMapping("/{customerId}")
//    ApiResponse<AccountResponse> updateCustomer(
//            @PathVariable String customerId, @RequestBody CustomerAccountUpdateRequest request) {
//        return ApiResponse.<AccountResponse>builder()
//                .result(customerService.updateCustomer(customerId, request))
//                .build();
//    }
}
