package com.theatermgnt.theatermgnt.repository;

import com.theatermgnt.theatermgnt.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    Optional<Staff> findByAccountId(String accountId);
}
