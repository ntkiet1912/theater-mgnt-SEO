package com.theatermgnt.theatermgnt.mapper;

import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.AccountResponse;
import com.theatermgnt.theatermgnt.entity.Account;
import com.theatermgnt.theatermgnt.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "accountType", expression = "java(com.theatermgnt.theatermgnt.enums.AccountType.CUSTOMER)")
    @Mapping(target = "isActive", constant = "true")
    Account toAccount(CustomerAccountCreationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountId", ignore = true)
    Customer toCustomer(CustomerAccountCreationRequest request);

    AccountResponse toAccountResponse(Account account);
}
