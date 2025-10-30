package com.theatermgnt.theatermgnt.mapper;

import com.theatermgnt.theatermgnt.dto.request.UserAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.AccountResponse;
import com.theatermgnt.theatermgnt.entity.Customer;
import com.theatermgnt.theatermgnt.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    User toUser(UserAccountCreationRequest request);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.phoneNumber", target = "phoneNumber")
    AccountResponse toUserAccountResponse(User user);
}
