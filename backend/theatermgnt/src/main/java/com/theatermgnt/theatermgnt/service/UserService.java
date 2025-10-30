package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.UserAccountCreationRequest;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.entity.Customer;
import com.theatermgnt.theatermgnt.entity.User;
import com.theatermgnt.theatermgnt.mapper.CustomerMapper;
import com.theatermgnt.theatermgnt.mapper.UserMapper;
import com.theatermgnt.theatermgnt.repository.CustomerRepository;
import com.theatermgnt.theatermgnt.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    AccountService accountService;

    public User createUser(UserAccountCreationRequest request, Account account) {
        User user = userMapper.toUser(request);
        user.setAccount(account);
        return userRepository.save(user);
    }
}
