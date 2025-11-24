package com.theatermgnt.theatermgnt.room.dto.response;


import com.theatermgnt.theatermgnt.common.enums.RoomType;
import com.theatermgnt.theatermgnt.seat.dto.request.SeatLayoutRequest;
import com.theatermgnt.theatermgnt.seat.dto.response.SeatLayoutResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomSeatResponse {
    String id;
    String cinemaId;
    String cinemaName;
    String name;
    String roomType;
    int totalSeats;

    List<SeatLayoutResponse> seats;

}
