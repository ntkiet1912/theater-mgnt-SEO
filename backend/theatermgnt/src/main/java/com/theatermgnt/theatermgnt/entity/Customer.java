package com.theatermgnt.theatermgnt.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import com.theatermgnt.theatermgnt.enums.Gender;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    Account account;

    String firstName;
    String lastName;
    String address;

    @Enumerated(EnumType.STRING)
    Gender gender;

    LocalDate dob;
}
