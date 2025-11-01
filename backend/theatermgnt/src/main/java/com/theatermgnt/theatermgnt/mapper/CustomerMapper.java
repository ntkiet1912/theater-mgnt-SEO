package com.theatermgnt.theatermgnt.mapper;

import com.theatermgnt.theatermgnt.dto.request.CustomerProfileUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.CustomerResponse;
import org.mapstruct.*;

import com.theatermgnt.theatermgnt.dto.request.CustomerAccountCreationRequest;
import com.theatermgnt.theatermgnt.dto.response.BaseUserResponse;
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
    @Mapping(source = "id", target = "customerId")
    CustomerResponse toCustomerResponse(Customer customer);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerProfile(@MappingTarget Customer customer, CustomerProfileUpdateRequest request);
}
