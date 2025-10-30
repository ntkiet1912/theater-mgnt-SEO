package com.theatermgnt.theatermgnt.dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.theatermgnt.theatermgnt.enums.Gender;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    String accountId;
    String username;
    String password;
    String email;
    String phoneNumber;
    String firstName;
    String lastName;
    String address;
    Gender gender;
    LocalDate dob;
    Set<RoleResponse> roles;
}
