package com.hppystay.hotelreservation.hotel.service;

import com.hppystay.hotelreservation.hotel.dto.RoomDto;
import com.hppystay.hotelreservation.hotel.entity.Room;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepo;

    public List<RoomDto> readAllByHotelId(Long hotelId) {
        List<Room> roomList = roomRepo.findAllByHotelId(hotelId);

        return roomList.stream()
                .map(RoomDto::fromEntity)
                .toList();
    }

    // 방 삭제
    public void deleteRoom(Long id) {
        Room room = roomRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        roomRepo.delete(room);
    }
}
