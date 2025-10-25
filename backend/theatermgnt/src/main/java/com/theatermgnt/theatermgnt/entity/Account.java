package com.theatermgnt.theatermgnt.entity;

import com.theatermgnt.theatermgnt.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String email;
    String username;
    String password;
    String phoneNumber;

    @Enumerated(EnumType.STRING)
    AccountType accountType;

    Boolean isActive;
    Instant createdAt;
    Instant updatedAt;
}
