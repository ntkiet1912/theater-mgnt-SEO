package com.theatermgnt.theatermgnt.dto.request;

import com.theatermgnt.theatermgnt.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffProfileUpdateRequest{
    String firstName;
    String lastName;
    String jobTitle;
    String cinemaId;
    String address;
    Gender gender;
    LocalDate dob;
    List<String> roles;
}
