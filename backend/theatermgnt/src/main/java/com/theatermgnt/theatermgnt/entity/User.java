package com.theatermgnt.theatermgnt.entity;

import jakarta.persistence.*;

import com.theatermgnt.theatermgnt.enums.Gender;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    Account account;

    String cinemaId;

    String firstName;
    String lastName;
    String jobTitle;
    String address;
    LocalDate dob;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @ManyToMany
    Set<Role> roles;
}
