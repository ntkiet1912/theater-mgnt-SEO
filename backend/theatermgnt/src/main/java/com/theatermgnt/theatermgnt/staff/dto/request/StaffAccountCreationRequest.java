package com.theatermgnt.theatermgnt.staff.dto.request;

import com.theatermgnt.theatermgnt.account.dto.request.IAccountCreationRequest;
import com.theatermgnt.theatermgnt.common.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffAccountCreationRequest implements IAccountCreationRequest {
    String username;
    String password;
    String email;
    String phoneNumber;

    String firstName;
    String lastName;
    String jobTitle;
    String cinemaId;
    String address;
    Gender gender;
    LocalDate dob;
}
