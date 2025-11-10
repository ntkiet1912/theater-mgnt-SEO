package com.theatermgnt.theatermgnt.movie.mapper;

import com.theatermgnt.theatermgnt.movie.dto.response.AgeRatingResponse;
import com.theatermgnt.theatermgnt.movie.entity.AgeRating;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AgeRatingMapper {

    /**
     * Convert AgeRating entity to AgeRatingResponse
     */
    AgeRatingResponse toResponse(AgeRating ageRating);

    /**
     * Convert List<AgeRating> to List<AgeRatingResponse>
     */
    List<AgeRatingResponse> toResponseList(List<AgeRating> ageRatings);
}
