package com.theatermgnt.theatermgnt.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatTypeUpdateRequest {
    @NotBlank
    String typeName;
    @NotNull
    Double basePriceModifier;
}
