package com.theatermgnt.theatermgnt.repository;

import com.theatermgnt.theatermgnt.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatTypeRepository extends JpaRepository<SeatType,String> {
    boolean existsByTypeName(String typeName);
}
