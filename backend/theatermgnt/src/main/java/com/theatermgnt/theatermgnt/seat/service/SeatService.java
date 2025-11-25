package com.theatermgnt.theatermgnt.seat.service;

import com.theatermgnt.theatermgnt.common.exception.AppException;
import com.theatermgnt.theatermgnt.common.exception.ErrorCode;
import com.theatermgnt.theatermgnt.room.entity.Room;
import com.theatermgnt.theatermgnt.room.repository.RoomRepository;
import com.theatermgnt.theatermgnt.seat.dto.request.SeatCreationRequest;
import com.theatermgnt.theatermgnt.seat.dto.request.SeatUpdateRequest;
import com.theatermgnt.theatermgnt.seat.dto.response.SeatResponse;
import com.theatermgnt.theatermgnt.seat.entity.Seat;
import com.theatermgnt.theatermgnt.seat.mapper.SeatMapper;
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
public class SeatService {
    SeatRepository seatRepository;
    RoomRepository roomRepository;
    SeatTypeRepository seatTypeRepository;
    SeatMapper seatMapper;

    // This method used to create seats for a specific room, not use in controller
    public List<Seat> createSeatsForRoom(Room room, List<SeatCreationRequest> seatRequests) {
        if(seatRequests == null || seatRequests.isEmpty()) {
            return new ArrayList<>();
        }
        // Get all seat types used in the layout
        Set<String> seatTypeIds = seatRequests.stream()
                .map(SeatCreationRequest::getSeatTypeId)
                .collect(Collectors.toSet());

        // Query seat types from the database
        List<SeatType> seatTypes = seatTypeRepository.findAllById(seatTypeIds);

        // Convert to map for easy access
        Map<String, SeatType> seatTypeMap = seatTypes.stream()
                .collect(Collectors.toMap(SeatType::getId, Function.identity()));

        // Map requests to Seat entities
        List<Seat> seatsToSave = new ArrayList<>();
        for(SeatCreationRequest seatRequest : seatRequests) {
            SeatType seatType = seatTypeMap.get(seatRequest.getSeatTypeId());

            if(seatType == null) {
                throw new AppException(ErrorCode.SEATTYPE_NOT_EXISTED);
            }
            Seat seat = Seat.builder()
                    .rowChair(seatRequest.getRowChair())
                    .seatNumber(seatRequest.getSeatNumber())
                    .seatType(seatType)
                    .room(room)
                    .build();
            seatsToSave.add(seat);
        }
        return seatRepository.saveAll(seatsToSave);
    }

    public List<SeatResponse> getSeatsByRoom(String roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new AppException(ErrorCode.ROOM_NOT_EXISTED);
        }
        return seatRepository.findByRoomId(roomId).stream()
                .map(seatMapper::toSeatResponse)
                .toList();
    }

    public List<SeatResponse> getSeats() {
        return seatRepository.findAll().stream()
                .map(seatMapper::toSeatResponse)
                .toList();
    }

    public SeatResponse getSeat(String seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));
        return seatMapper.toSeatResponse(seat);
    }

    @Transactional
    public SeatResponse updateSeat(String seatId, SeatUpdateRequest request) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));

        if (request.getSeatTypeId() != null) {
            SeatType seatType = seatTypeRepository.findById(request.getSeatTypeId())
                    .orElseThrow(() -> new AppException(ErrorCode.SEATTYPE_NOT_EXISTED));
            seat.setSeatType(seatType);
        }

        if ((request.getRowChair() != null && !request.getRowChair().equals(seat.getRowChair())) ||
                (request.getSeatNumber() != null && !request.getSeatNumber().equals(seat.getSeatNumber()))) {

            String newRowChair = request.getRowChair() != null ? request.getRowChair() : seat.getRowChair();
            Integer newSeatNumber = request.getSeatNumber() != null ? request.getSeatNumber() : seat.getSeatNumber();

            if (seatRepository.existsByRowChairAndSeatNumberAndRoomId(
                    newRowChair, newSeatNumber, seat.getRoom().getId())) {
                throw new AppException(ErrorCode.SEAT_EXISTED);
            }
        }

        seatMapper.updateSeat(seat, request);
        return seatMapper.toSeatResponse(seatRepository.save(seat));
    }

    @Transactional
    public void deleteSeat(String seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));

        Room room = seat.getRoom();

        seatRepository.deleteById(seatId);

        room.setTotalSeats(room.getTotalSeats() - 1);
        roomRepository.save(room);
    }
}