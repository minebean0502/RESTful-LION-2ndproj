package com.hppystay.hotelreservation.hotel.service;

import com.hppystay.hotelreservation.hotel.dto.RoomDto;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.entity.Room;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
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
    private final HotelRepository hotelRepo;

    // 기존 호텔에 방만 추가하는 경우
    public RoomDto addRoom(RoomDto roomDto, Long hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return RoomDto.fromEntity(roomRepo.save(Room.builder()
                .name(roomDto.getName())
                .price(roomDto.getPrice())
                .content(roomDto.getContent())
                .hotel(hotel)
                .build()));
    }

    public List<RoomDto> readAllByHotelId(Long hotelId) {
        List<Room> roomList = roomRepo.findAllByHotelId(hotelId);

        return roomList.stream()
                .map(RoomDto::fromEntity)
                .toList();
    }

    // 방 업데이트
    public RoomDto updateRoom(Long id, RoomDto roomDto) {
        Room room = roomRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Hotel hotel = hotelRepo.findById(roomDto.getHotelId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        room.setName(room.getName());
        room.setPrice(roomDto.getPrice());
        room.setContent(roomDto.getContent());
        room.setHotel(hotel);

        return RoomDto.fromEntity(roomRepo.save(room));
    }

    // 방 삭제
    public void deleteRoom(Long id) {
        Room room = roomRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        roomRepo.delete(room);
    }
}
