package com.theatermgnt.theatermgnt.entity;

import com.theatermgnt.theatermgnt.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "seatTypes")
public class SeatType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String typeName;
    BigDecimal basePriceModifier;
}
