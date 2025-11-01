package com.theatermgnt.theatermgnt.repository;

import com.theatermgnt.theatermgnt.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,String> {
    List<Room> findByCinemaId(String cinemaId);
    boolean existsByNameAndCinemaId(String name, String cinemaId);
}
