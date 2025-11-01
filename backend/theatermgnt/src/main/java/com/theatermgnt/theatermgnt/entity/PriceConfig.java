package com.theatermgnt.theatermgnt.entity;

import com.theatermgnt.theatermgnt.enums.DayType;
import com.theatermgnt.theatermgnt.enums.TimeSlot;
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
@Table(name = "priceConfigs")
public class PriceConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    DayType dayType;

    @Enumerated(EnumType.STRING)
    TimeSlot timeSlot;

    @Column(precision = 10, scale = 2)
    BigDecimal price;

    // Quan hệ nhiều-1 với SeatType
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatTypeId", nullable = false)
    SeatType seatType;
}
