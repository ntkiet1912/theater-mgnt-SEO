package com.theatermgnt.theatermgnt.room.service;

import com.theatermgnt.theatermgnt.cinema.entity.Cinema;
import com.theatermgnt.theatermgnt.cinema.repository.CinemaRepository;
import com.theatermgnt.theatermgnt.common.exception.AppException;
import com.theatermgnt.theatermgnt.common.exception.ErrorCode;
import com.theatermgnt.theatermgnt.room.dto.request.RoomCreationRequest;
import com.theatermgnt.theatermgnt.room.dto.request.RoomUpdateRequest;
import com.theatermgnt.theatermgnt.room.dto.response.RoomResponse;
import com.theatermgnt.theatermgnt.room.dto.response.RoomSeatResponse;
import com.theatermgnt.theatermgnt.room.entity.Room;
import com.theatermgnt.theatermgnt.room.mapper.RoomMapper;
import com.theatermgnt.theatermgnt.room.repository.RoomRepository;
import com.theatermgnt.theatermgnt.seat.dto.request.SeatLayoutRequest;
import com.theatermgnt.theatermgnt.seat.entity.Seat;
import com.theatermgnt.theatermgnt.seat.repository.SeatRepository;
import com.theatermgnt.theatermgnt.seatType.entity.SeatType;
import com.theatermgnt.theatermgnt.seatType.repository.SeatTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService {
    RoomRepository roomRepository;
    CinemaRepository cinemasRepository;
    RoomMapper roomMapper;
    SeatTypeRepository seatTypeRepository;
    SeatRepository seatRepository;

    public RoomResponse createRoom(RoomCreationRequest request) {
        Cinema cinema = cinemasRepository.findById(request.getCinemaId())
                .orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_EXISTED));

        if (roomRepository.existsByNameAndCinemaId(request.getName(), request.getCinemaId()))
            throw new AppException(ErrorCode.ROOM_EXISTED);

        Room room = roomMapper.toRoom(request);
        room.setCinema(cinema);
        room.setTotalSeats(0);

        return roomMapper.toRoomResponse(roomRepository.save(room));
    }
    @Transactional
    public RoomSeatResponse createRoomWithSeatLayout(RoomCreationRequest request) {
        Cinema cinema = cinemasRepository.findById(request.getCinemaId())
                .orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_EXISTED));

        if (roomRepository.existsByNameAndCinemaId(request.getName(), request.getCinemaId()))
            throw new AppException(ErrorCode.ROOM_EXISTED);

        // Create room and save room
        Room room = roomMapper.toRoom(request);
        room.setCinema(cinema);

        if(request.getSeatLayout() != null) {
            room.setTotalSeats(request.getSeatLayout().size());
        }

        Room savedRoom = roomRepository.save(room);

        // Create seat layout
        if(request.getSeatLayout() != null && !request.getSeatLayout().isEmpty()) {
            // Get all seat types used in the layout
            Set<String> seatTypeIds = request.getSeatLayout().stream()
                    .map(SeatLayoutRequest::getSeatTypeId)
                    .collect(Collectors.toSet());

            // Query seat types from the database
            List<SeatType> seatTypes = seatTypeRepository.findAllById(seatTypeIds);

            // Convert to map for easy access
            Map<String, SeatType> seatTypeMap = seatTypes.stream()
                    .collect(Collectors.toMap(SeatType::getId, Function.identity()));

            List<Seat> seatsToSave = new ArrayList<>();

            for(SeatLayoutRequest seatLayout : request.getSeatLayout()) {
                SeatType seatType = seatTypeMap.get(seatLayout.getSeatTypeId());

                if(seatType == null) {
                    throw new AppException(ErrorCode.SEATTYPE_NOT_EXISTED);
                }
                Seat seat = Seat.builder()
                        .rowChair(seatLayout.getRowChair())
                        .seatNumber(seatLayout.getSeatNumber())
                        .seatType(seatType)
                        .room(savedRoom)
                        .build();

                seatsToSave.add(seat);
            }
            List<Seat> saveSeats = seatRepository.saveAll(seatsToSave);
        }

        return roomMapper.toRoomSeatResponse(room);
    }

    public List<RoomResponse> getRoomsByCinema(String cinemaId) {
        return roomRepository.findByCinemaId(cinemaId).stream()
                .map(roomMapper::toRoomResponse)
                .toList();
    }

    public List<RoomResponse> getRooms() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toRoomResponse)
                .toList();
    }

    public RoomResponse getRoom(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));
        return roomMapper.toRoomResponse(room);
    }

    public RoomResponse updateRoom(String roomId, RoomUpdateRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

        roomMapper.updateRoom(room, request);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    public void deleteRoom(String roomId) {
        if (!roomRepository.existsById(roomId))
            throw new AppException(ErrorCode.ROOM_NOT_EXISTED);
        roomRepository.deleteById(roomId);
    }
}
