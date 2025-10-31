package com.theatermgnt.theatermgnt.dto.request;


import com.theatermgnt.theatermgnt.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatTypeCreationRequest {
    @NotBlank
    String typeName;
    @NotNull
    Double basePriceModifier;
}
