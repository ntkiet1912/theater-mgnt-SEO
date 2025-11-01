package com.theatermgnt.theatermgnt.repository;

import com.theatermgnt.theatermgnt.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.theatermgnt.theatermgnt.entity.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByAccountId(String accountId);

}
