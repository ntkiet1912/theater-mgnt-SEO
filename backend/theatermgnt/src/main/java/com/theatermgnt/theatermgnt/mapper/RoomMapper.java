package com.theatermgnt.theatermgnt.mapper;


import com.theatermgnt.theatermgnt.dto.request.CinemaCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.CinemaUpdateRequest;
import com.theatermgnt.theatermgnt.dto.request.RoomCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.RoomUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.CinemaResponse;
import com.theatermgnt.theatermgnt.dto.response.RoomResponse;
import com.theatermgnt.theatermgnt.entity.Cinema;
import com.theatermgnt.theatermgnt.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room toRoom(RoomCreationRequest request);

    @Mapping(target = "cinemaName", source = "cinema.name")
    RoomResponse toRoomResponse(Room room);
    void updateRoom(@MappingTarget Room room, RoomUpdateRequest request);
}
