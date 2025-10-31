package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.constant.PredefinedRole;
import com.theatermgnt.theatermgnt.dto.request.StaffAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.StaffProfileUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.AccountResponse;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.entity.Customer;
import com.theatermgnt.theatermgnt.entity.Role;
import com.theatermgnt.theatermgnt.entity.Staff;
import com.theatermgnt.theatermgnt.enums.AccountType;
import com.theatermgnt.theatermgnt.exception.AppException;
import com.theatermgnt.theatermgnt.exception.ErrorCode;
import com.theatermgnt.theatermgnt.mapper.StaffMapper;
import com.theatermgnt.theatermgnt.repository.RoleRepository;
import com.theatermgnt.theatermgnt.repository.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.experimental.NonFinal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StaffService {
    StaffRepository staffRepository;
    StaffMapper staffMapper;
    RoleRepository roleRepository;


    /// CREATE STAFF PROFILE
    @Transactional
    public Staff createStaffProfile(StaffAccountCreationRequest request, Account account, Set<Role> roles) {
        Staff staff = staffMapper.toStaff(request);
        staff.setAccount(account);
        staff.setRoles(roles);
        return staffRepository.save(staff);
    }

    /// GET ALL STAFF
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountResponse> getAll() {
        return staffRepository.findAll().stream()
                .map(staffMapper::toStaffAccountResponse)
                .toList();
    }

    /// GET STAFF BY ID
    public AccountResponse getStaffProfile(String staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return staffMapper.toStaffAccountResponse(staff);
    }

    /// GET MY INFO
    public AccountResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String accountId = context.getAuthentication().getName();

        Staff staff = staffRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return staffMapper.toStaffAccountResponse(staff);
    }

    /// UPDATE STAFF PROFILE
    public AccountResponse updateStaffProfile(String staffId, StaffProfileUpdateRequest request) {

        Staff staffToUpdate = staffRepository.findById(staffId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        staffMapper.updateStaffProfile(staffToUpdate, request);
        var roles = roleRepository.findAllById(request.getRoles());
        staffToUpdate.setRoles(new HashSet<>(roles));
        return staffMapper.toStaffAccountResponse(staffRepository.save(staffToUpdate));
    }
    /// DELETE STAFF
    public void deleteStaff(String staffId) {
        staffRepository.deleteById(staffId);
    }
}
