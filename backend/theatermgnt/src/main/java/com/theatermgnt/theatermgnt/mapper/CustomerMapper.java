package com.theatermgnt.theatermgnt.mapper;

import org.mapstruct.*;

import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.CustomerAccountUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.AccountResponse;
import com.theatermgnt.theatermgnt.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    Customer toCustomer(CustomerAccountCreationRequest request);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.phoneNumber", target = "phoneNumber")
    AccountResponse toCustomerAccountResponse(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomer(@MappingTarget Customer customer, CustomerAccountUpdateRequest request);
}
