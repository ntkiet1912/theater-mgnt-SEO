package com.theatermgnt.theatermgnt.mapper;


import com.theatermgnt.theatermgnt.dto.request.RoleRequest;
import com.theatermgnt.theatermgnt.dto.response.RoleResponse;
import com.theatermgnt.theatermgnt.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    void updateRole(RoleRequest request, @MappingTarget Role role);
}
