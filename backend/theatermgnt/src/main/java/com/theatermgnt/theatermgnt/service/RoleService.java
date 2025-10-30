package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.dto.request.RoleRequest;
import com.theatermgnt.theatermgnt.dto.response.RoleResponse;
import com.theatermgnt.theatermgnt.exception.AppException;
import com.theatermgnt.theatermgnt.exception.ErrorCode;
import com.theatermgnt.theatermgnt.mapper.RoleMapper;
import com.theatermgnt.theatermgnt.repository.PermissionRepository;
import com.theatermgnt.theatermgnt.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    /// CREATE A ROLE
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    /// GET ALL ROLES
    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    /// UPDATE A ROLE
    public RoleResponse update(String roleId, RoleRequest request) {
        var role = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        roleMapper.updateRole(request, role);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    /// DELETE A ROLE
    public void delete(String roleId) {
        roleRepository.deleteById(roleId);
    }
}
