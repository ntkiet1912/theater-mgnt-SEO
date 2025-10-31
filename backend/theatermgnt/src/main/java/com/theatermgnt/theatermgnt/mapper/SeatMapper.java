package com.theatermgnt.theatermgnt.mapper;


import com.theatermgnt.theatermgnt.dto.request.RoomCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.RoomUpdateRequest;
import com.theatermgnt.theatermgnt.dto.request.SeatCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.SeatUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.RoomResponse;
import com.theatermgnt.theatermgnt.dto.response.SeatResponse;
import com.theatermgnt.theatermgnt.entity.Room;
import com.theatermgnt.theatermgnt.entity.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    Seat toSeat(SeatCreationRequest request);

    @Mapping(source = "seatType.typeName", target = "seatTypeName")
    @Mapping(source = "room.name", target = "roomName")
    SeatResponse toSeatResponse(Seat seat);

    void updateSeat(@MappingTarget Seat seat, SeatUpdateRequest request);
}
