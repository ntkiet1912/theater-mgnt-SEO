package com.theatermgnt.theatermgnt.mapper;

import com.theatermgnt.theatermgnt.dto.request.PermissionRequest;
import com.theatermgnt.theatermgnt.dto.response.PermissionResponse;
import com.theatermgnt.theatermgnt.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper{
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
