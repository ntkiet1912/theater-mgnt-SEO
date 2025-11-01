package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.constant.PredefinedRole;
import com.theatermgnt.theatermgnt.dto.request.StaffAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.CustomerResponse;
import com.theatermgnt.theatermgnt.dto.response.StaffResponse;
import com.theatermgnt.theatermgnt.entity.Role;
import com.theatermgnt.theatermgnt.entity.Staff;
import com.theatermgnt.theatermgnt.mapper.StaffMapper;
import com.theatermgnt.theatermgnt.repository.RoleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.entity.Customer;
import com.theatermgnt.theatermgnt.enums.AccountType;
import com.theatermgnt.theatermgnt.mapper.CustomerMapper;

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
public class RegistrationService {
    AccountService accountService;
    CustomerService customerService;
    StaffService staffService;
    CustomerMapper customerMapper;
    StaffMapper staffMapper;
    RoleRepository roleRepository;

    @Transactional
    public CustomerResponse registerCustomerAccount(CustomerAccountCreationRequest request) {
        Account savedAccount = accountService.createAccount(request);
        savedAccount.setAccountType(AccountType.CUSTOMER);

        Customer savedCustomer = customerService.createCustomerProfile(request, savedAccount);
        return customerMapper.toCustomerResponse(savedCustomer);
    }


    /// Create staff account
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public StaffResponse registerStaffAccount(StaffAccountCreationRequest request) {
        Set<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.STAFF_ROLE).ifPresent(roles::add);
        return internalCreateStaff(request,roles);
    }

    /// Only use for initial admin account creation
    @Transactional
    public StaffResponse createAdminAccount(StaffAccountCreationRequest request) {
        Set<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.ADMIN_ROLE).ifPresent(roles::add);
        return internalCreateStaff(request,roles);
    }

    private StaffResponse internalCreateStaff(StaffAccountCreationRequest request, Set<Role> roles) {
        Account savedAccount = accountService.createAccount(request);
        savedAccount.setAccountType(AccountType.INTERNAL);

        Staff savedStaff = staffService.createStaffProfile(request, savedAccount, roles);
        return staffMapper.toStaffResponse(savedStaff);
    }
}
