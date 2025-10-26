package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.CustomerAccountResponse;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.entity.Customer;
import com.theatermgnt.theatermgnt.exception.AppException;
import com.theatermgnt.theatermgnt.exception.ErrorCode;
import com.theatermgnt.theatermgnt.mapper.AccountMapper;
import com.theatermgnt.theatermgnt.repository.AccountRepository;
import com.theatermgnt.theatermgnt.repository.CustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    CustomerRepository customerRepository;

    @Transactional
    public CustomerAccountResponse createCustomerAccount(CustomerAccountCreationRequest request) {
        // Check existed username
        if (accountRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        Account account = accountMapper.toAccount(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        Account savedAccount = accountRepository.save(account);

        // Save customer info
        Customer customer = accountMapper.toCustomer(request);
        customer.setAccount(savedAccount);
        Customer savedCustomer = customerRepository.save(customer);
        return accountMapper.toCustomerAccountResponse(savedCustomer);

    }
}
