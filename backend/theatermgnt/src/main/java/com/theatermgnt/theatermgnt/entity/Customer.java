package com.theatermgnt.theatermgnt.entity;


import com.theatermgnt.theatermgnt.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne
    @JoinColumn(name="account_id", referencedColumnName = "id")
    Account account;

    String firstName;
    String lastName;
    String address;

    @Enumerated(EnumType.STRING)
    Gender gender;

    LocalDate dob;

}
