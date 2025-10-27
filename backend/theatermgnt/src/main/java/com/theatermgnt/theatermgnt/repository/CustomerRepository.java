package com.theatermgnt.theatermgnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.theatermgnt.theatermgnt.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {}
