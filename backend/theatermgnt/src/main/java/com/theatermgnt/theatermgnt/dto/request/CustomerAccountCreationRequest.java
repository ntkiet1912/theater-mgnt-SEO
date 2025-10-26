package com.theatermgnt.theatermgnt.dto.request;

import com.theatermgnt.theatermgnt.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerAccountCreationRequest {
    String username;
    String password;
    String email;
    String phoneNumber;
    String firstname;
    String lastname;
    String address;
    Gender gender;
    LocalDate dob;
}
