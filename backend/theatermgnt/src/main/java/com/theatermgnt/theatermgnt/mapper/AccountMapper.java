package com.theatermgnt.theatermgnt.mapper;

import org.mapstruct.*;

import com.theatermgnt.theatermgnt.dto.request.IAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.IAccountUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.CustomerAccountResponse;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.entity.Customer;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "password", ignore = true) // Password is handled separately
    @Mapping(target = "accountType", ignore = true)
    Account toAccount(IAccountCreationRequest request);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.phoneNumber", target = "phoneNumber")
    CustomerAccountResponse toCustomerAccountResponse(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true) // Password is handled separately
    @Mapping(target = "accountType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAccount(@MappingTarget Account account, IAccountUpdateRequest request);
}
