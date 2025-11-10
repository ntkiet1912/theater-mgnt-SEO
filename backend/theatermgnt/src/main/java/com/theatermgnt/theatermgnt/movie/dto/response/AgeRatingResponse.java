package com.theatermgnt.theatermgnt.movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgeRatingResponse {
    private String id;
    private String code;
    private String description;
}