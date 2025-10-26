package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.exception.AppException;
import com.theatermgnt.theatermgnt.exception.ErrorCode;
import com.theatermgnt.theatermgnt.mapper.AccountMapper;
import com.theatermgnt.theatermgnt.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;

    public CustomerAccountCreationRequest createAccount(CustomerAccountCreationRequest request) {
        // Check existed username
        if (accountRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        Account account = accountMapper.toAccount(request);


        return null;
    }
}
