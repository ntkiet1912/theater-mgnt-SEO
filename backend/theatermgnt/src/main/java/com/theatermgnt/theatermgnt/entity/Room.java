package com.theatermgnt.theatermgnt.entity;

import com.theatermgnt.theatermgnt.enums.RoomType;
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
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaId", nullable = false)
    Cinema cinema;
    String name;

    @Enumerated(EnumType.STRING)
    RoomType roomType;
    Integer totalSeats;
}
