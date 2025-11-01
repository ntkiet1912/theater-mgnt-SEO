package com.theatermgnt.theatermgnt.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatResponse {
     String id;
     String rowChair;
     Integer seatNumber;
     String roomName;
     String seatTypeName;
}
