package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.constant.PredefinedRole;
import com.theatermgnt.theatermgnt.dto.request.UserAccountCreationRequest;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.entity.Role;
import com.theatermgnt.theatermgnt.entity.User;
import com.theatermgnt.theatermgnt.mapper.UserMapper;
import com.theatermgnt.theatermgnt.repository.RoleRepository;
import com.theatermgnt.theatermgnt.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;

    public User createUserProfile(UserAccountCreationRequest request, Account account) {
        User user = userMapper.toUser(request);
        user.setAccount(account);

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.ADMIN_ROLE).ifPresent(roles::add);
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
