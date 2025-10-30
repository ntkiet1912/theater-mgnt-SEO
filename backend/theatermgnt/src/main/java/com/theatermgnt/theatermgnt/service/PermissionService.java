package com.theatermgnt.theatermgnt.service;

import com.theatermgnt.theatermgnt.dto.request.PermissionRequest;
import com.theatermgnt.theatermgnt.dto.response.PermissionResponse;
import com.theatermgnt.theatermgnt.entity.Permission;
import com.theatermgnt.theatermgnt.mapper.PermissionMapper;
import com.theatermgnt.theatermgnt.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    ///  CREATE A PERMISSION
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }
    ///  GET ALL PERMISSIONS
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }
    /// DELETE A PERMISSION
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
