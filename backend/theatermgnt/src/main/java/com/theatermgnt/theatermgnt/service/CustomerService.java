package com.theatermgnt.theatermgnt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.CustomerAccountUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.AccountResponse;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.entity.Customer;
import com.theatermgnt.theatermgnt.exception.AppException;
import com.theatermgnt.theatermgnt.exception.ErrorCode;
import com.theatermgnt.theatermgnt.mapper.CustomerMapper;
import com.theatermgnt.theatermgnt.repository.CustomerRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;
    AccountService accountService;

    public Customer createCustomer(CustomerAccountCreationRequest request, Account account) {
        Customer customer = customerMapper.toCustomer(request);
        customer.setAccount(account);
        return customerRepository.save(customer);
    }

    @Transactional
    public AccountResponse updateCustomer(String customerId, CustomerAccountUpdateRequest request) {
        Customer customerToUpdate =
                customerRepository.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Account accountToUpdate = customerToUpdate.getAccount();
        customerMapper.updateCustomer(customerToUpdate, request);
        customerRepository.save(customerToUpdate);

        accountService.updateAccount(accountToUpdate, request);

        return customerMapper.toCustomerAccountResponse(customerToUpdate);
    }
}
