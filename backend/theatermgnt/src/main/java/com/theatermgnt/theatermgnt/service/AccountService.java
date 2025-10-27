package com.theatermgnt.theatermgnt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.theatermgnt.theatermgnt.dto.request.IAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.IAccountUpdateRequest;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.exception.AppException;
import com.theatermgnt.theatermgnt.exception.ErrorCode;
import com.theatermgnt.theatermgnt.mapper.AccountMapper;
import com.theatermgnt.theatermgnt.repository.AccountRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder;

    public Account createAccount(IAccountCreationRequest request) {
        if (accountRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Account account = accountMapper.toAccount(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        return accountRepository.save(account);
    }

    public void updateAccount(Account account, IAccountUpdateRequest request) {
        if (accountRepository.existsByUsername(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        accountMapper.updateAccount(account, request);
        accountRepository.save(account);
    }
}
