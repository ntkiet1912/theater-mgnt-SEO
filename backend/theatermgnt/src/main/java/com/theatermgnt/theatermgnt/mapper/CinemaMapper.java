package com.theatermgnt.theatermgnt.mapper;


import com.theatermgnt.theatermgnt.dto.request.CinemaCreationRequest;
import com.theatermgnt.theatermgnt.dto.request.CinemaUpdateRequest;
import com.theatermgnt.theatermgnt.dto.response.CinemaResponse;
import com.theatermgnt.theatermgnt.entity.Cinema;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    Cinema toCinemas(CinemaCreationRequest request);
    CinemaResponse toCinemaResponse(Cinema cinema);
    void updateCinema(@MappingTarget Cinema cinema, CinemaUpdateRequest request);
}
