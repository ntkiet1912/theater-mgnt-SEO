package com.theatermgnt.theatermgnt.seat.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatLayoutResponse {
    String id;
    String rowChair;
    Integer seatNumber;
    String seatType;
}