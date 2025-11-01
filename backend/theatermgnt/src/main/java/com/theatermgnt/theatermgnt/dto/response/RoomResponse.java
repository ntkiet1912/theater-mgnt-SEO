package com.theatermgnt.theatermgnt.dto.response;

import com.theatermgnt.theatermgnt.enums.RoomType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomResponse {
     String id;
     String name;
     RoomType roomType;
     Integer totalSeats;
     String cinemaName;
}
