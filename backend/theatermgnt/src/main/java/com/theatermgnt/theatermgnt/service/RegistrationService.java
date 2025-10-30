package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.dto.request.UserAccountCreationRequest;
import com.theatermgnt.theatermgnt.entity.User;
import com.theatermgnt.theatermgnt.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.AccountResponse;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.entity.Customer;
import com.theatermgnt.theatermgnt.enums.AccountType;
import com.theatermgnt.theatermgnt.mapper.AccountMapper;
import com.theatermgnt.theatermgnt.mapper.CustomerMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegistrationService {
    AccountService accountService;
    CustomerService customerService;
    UserService userService;
    AccountMapper accountMapper;
    CustomerMapper customerMapper;
    UserMapper userMapper;

    @Transactional
    public AccountResponse registerCustomerAccount(CustomerAccountCreationRequest request) {
        Account savedAccount = accountService.createAccount(request);
        savedAccount.setAccountType(AccountType.CUSTOMER);

        Customer savedCustomer = customerService.createCustomer(request, savedAccount);
        return customerMapper.toCustomerAccountResponse(savedCustomer);
    }

    @Transactional
    public AccountResponse registerNewUser(UserAccountCreationRequest request) {
        Account savedAccount = accountService.createAccount(request);
        savedAccount.setAccountType(AccountType.USER);

        User savedUser = userService.createUser(request, savedAccount);
        return userMapper.toUserAccountResponse(savedUser);
    }
}
