package com.theatermgnt.theatermgnt.repository;

import com.theatermgnt.theatermgnt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
