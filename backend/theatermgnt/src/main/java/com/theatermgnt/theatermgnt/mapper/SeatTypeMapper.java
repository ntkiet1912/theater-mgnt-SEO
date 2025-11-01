package com.theatermgnt.theatermgnt.mapper;


import com.theatermgnt.theatermgnt.dto.request.CinemaCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.CinemaUpdateRequest;
import com.theatermgnt.theatermgnt.dto.request.SeatTypeCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.SeatTypeUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.CinemaResponse;
import com.theatermgnt.theatermgnt.dto.response.SeatTypeResponse;
import com.theatermgnt.theatermgnt.entity.Cinema;
import com.theatermgnt.theatermgnt.entity.SeatType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SeatTypeMapper {
    SeatType toSeatType(SeatTypeCreationRequest request);
    SeatTypeResponse toSeatTypeResponse(SeatType seatType);
    void updateSeatType(@MappingTarget SeatType seatType, SeatTypeUpdateRequest request);
}
