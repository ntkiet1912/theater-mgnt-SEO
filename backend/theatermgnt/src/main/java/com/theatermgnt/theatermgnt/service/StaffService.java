package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.constant.PredefinedRole;
import com.theatermgnt.theatermgnt.dto.request.StaffAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.AccountResponse;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.entity.Customer;
import com.theatermgnt.theatermgnt.entity.Role;
import com.theatermgnt.theatermgnt.entity.Staff;
import com.theatermgnt.theatermgnt.enums.AccountType;
import com.theatermgnt.theatermgnt.mapper.StaffMapper;
import com.theatermgnt.theatermgnt.repository.RoleRepository;
import com.theatermgnt.theatermgnt.repository.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StaffService {
    StaffRepository staffRepository;
    StaffMapper userMapper;
    RoleRepository roleRepository;
    AccountService accountService;

    @Transactional
    public Staff createStaffProfile(StaffAccountCreationRequest request, Account account, Set<Role> roles) {
        Staff staff = userMapper.toStaff(request);
        staff.setAccount(account);
        staff.setRoles(roles);
        return staffRepository.save(staff);
    }

}
