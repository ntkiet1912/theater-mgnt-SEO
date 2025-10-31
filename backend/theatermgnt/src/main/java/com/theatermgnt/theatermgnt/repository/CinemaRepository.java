package com.theatermgnt.theatermgnt.repository;

import com.theatermgnt.theatermgnt.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CinemaRepository extends JpaRepository<Cinema,String> {
    boolean existsByName(String name);
}
