package com.theatermgnt.theatermgnt.repository;

import com.theatermgnt.theatermgnt.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
