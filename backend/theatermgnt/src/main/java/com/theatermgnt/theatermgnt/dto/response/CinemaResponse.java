package com.theatermgnt.theatermgnt.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaResponse {
     String id;
     String name;
     String address;
     String city;
     String phoneNumber;
     String managerId;
     LocalDateTime createdAt;
}
