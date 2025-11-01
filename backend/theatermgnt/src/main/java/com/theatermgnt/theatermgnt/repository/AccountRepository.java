package com.theatermgnt.theatermgnt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.theatermgnt.theatermgnt.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Account> findByUsername(String username);
}
