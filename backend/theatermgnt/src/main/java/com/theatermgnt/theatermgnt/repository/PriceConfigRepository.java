package com.theatermgnt.theatermgnt.repository;

import com.theatermgnt.theatermgnt.entity.PriceConfig;
import com.theatermgnt.theatermgnt.entity.Room;
import com.theatermgnt.theatermgnt.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceConfigRepository extends JpaRepository<PriceConfig,String> {
    List<PriceConfig> findBySeatTypeId(String seatTypeId);
}
