package com.theatermgnt.theatermgnt.mapper;

import com.theatermgnt.theatermgnt.dto.request.StaffAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.StaffProfileUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.BaseUserResponse;
import com.theatermgnt.theatermgnt.dto.response.StaffResponse;
import com.theatermgnt.theatermgnt.entity.Staff;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    Staff toStaff(StaffAccountCreationRequest request);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.phoneNumber", target = "phoneNumber")
    @Mapping(source = "account.accountType", target = "accountType")
    @Mapping(source = "id", target = "staffId")
    StaffResponse toStaffResponse(Staff staff);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    void updateStaffProfile(@MappingTarget Staff staff, StaffProfileUpdateRequest request);
}
