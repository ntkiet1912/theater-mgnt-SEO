package com.theatermgnt.theatermgnt.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String rowChair;
    Integer seatNumber;

    // Quan hệ nhiều-1 với Room
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId", nullable = false)
    Room room;

    // Quan hệ nhiều-1 với SeatType
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatTypeId", nullable = false)
    SeatType seatType;
}
