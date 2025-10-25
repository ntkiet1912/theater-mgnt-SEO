package com.theatermgnt.theatermgnt.entity;

import com.theatermgnt.theatermgnt.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String accountId;
    String cinemaId;

    String firstName;
    String lastName;
    String jobTitle;
    String address;

    @Enumerated(EnumType.STRING)
    Gender gender;

}
