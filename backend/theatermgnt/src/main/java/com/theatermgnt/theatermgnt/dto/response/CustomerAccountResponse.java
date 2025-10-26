package com.theatermgnt.theatermgnt.dto.response;

import com.theatermgnt.theatermgnt.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerAccountResponse {
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
}
