package com.theatermgnt.theatermgnt.customer.dto.request;

import java.time.LocalDate;

import com.theatermgnt.theatermgnt.account.dto.request.IAccountCreationRequest;
import com.theatermgnt.theatermgnt.common.enums.Gender;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerAccountCreationRequest implements IAccountCreationRequest {
    String username;
    String password;
    String email;
    String phoneNumber;

    String firstName;
    String lastName;
    String address;
    Gender gender;
    LocalDate dob;
}
