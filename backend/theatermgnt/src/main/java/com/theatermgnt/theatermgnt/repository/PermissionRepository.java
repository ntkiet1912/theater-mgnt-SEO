package com.theatermgnt.theatermgnt.repository;

import com.theatermgnt.theatermgnt.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
