package com.theatermgnt.theatermgnt.dto.response;

import com.theatermgnt.theatermgnt.enums.DayType;
import com.theatermgnt.theatermgnt.enums.TimeSlot;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceConfigResponse {
     String id;
     DayType dayType;
     TimeSlot timeSlot;
     BigDecimal price;

     String seatTypeName;
}
