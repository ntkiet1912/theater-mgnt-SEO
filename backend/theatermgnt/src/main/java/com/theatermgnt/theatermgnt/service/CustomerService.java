package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.dto.request.CustomerProfileUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.CustomerResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.BaseUserResponse;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    /// CREATE CUSTOMER PROFILE
    @Transactional
    public Customer createCustomerProfile(CustomerAccountCreationRequest request, Account account) {

        Customer customer = customerMapper.toCustomer(request);
        customer.setAccount(account);
        return customerRepository.save(customer);
    }

    /// GET CUSTOMER PROFILE BY ID
    public CustomerResponse getCustomerProfileById(String customerId) {
        Customer customer =
                customerRepository.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return customerMapper.toCustomerResponse(customer);
    }

    /// GET MY CUSTOMER PROFILE
    public CustomerResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String accountId = context.getAuthentication().getName();

        Customer customer = customerRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return customerMapper.toCustomerResponse(customer);
    }

    /// GET ALl CUSTOMERS
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }

    /// UPDATE CUSTOMER PROFILE
    @Transactional
    public CustomerResponse updateCustomerProfile(String customerId, CustomerProfileUpdateRequest request) {

        Customer customerToUpdate =
                customerRepository.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        customerMapper.updateCustomerProfile(customerToUpdate, request);
        return customerMapper.toCustomerResponse(customerRepository.save(customerToUpdate));
    }
}
