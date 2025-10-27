package com.theatermgnt.theatermgnt.dto.request;

import java.time.LocalDate;

import com.theatermgnt.theatermgnt.enums.Gender;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerAccountUpdateRequest implements IAccountUpdateRequest {
    String phoneNumber;
    String firstName;
    String lastName;
    String address;
    Gender gender;
    LocalDate dob;
}
