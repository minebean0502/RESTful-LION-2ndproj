package com.hppystay.hotelreservation.hotel.service;

import com.hppystay.hotelreservation.api.KNTO.utils.AreaCode;
import com.hppystay.hotelreservation.hotel.dto.HotelDto;
import com.hppystay.hotelreservation.hotel.dto.RoomDto;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.entity.Room;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepo;
    private final RoomRepository roomRepo;

    @Transactional
    public HotelDto createHotel(HotelDto hotelDto) {
        // 호텔 생성
        Hotel hotel = Hotel.builder()
                .name(hotelDto.getName())
                .address(hotelDto.getAddress())
                .region(hotelDto.getRegion())
                .description(hotelDto.getDescription())
                .images(hotelDto.getImages())
                .avg_score(hotelDto.getAvg_score())
                .mapX(hotelDto.getMapX())
                .mapY(hotelDto.getMapY())
                .phone(hotelDto.getPhone())
                .rooms(new ArrayList<>())
                .build();
        hotel = hotelRepo.save(hotel);

        // 연결된 방 생성
        List<RoomDto> roomDtoList = hotelDto.getRooms();
        List<Room> roomList = new ArrayList<>();
        for (RoomDto roomDto : roomDtoList) {
            Room room = roomRepo.save(Room.builder()
                    .name(roomDto.getName())
                    .price(roomDto.getPrice())
                    .content(roomDto.getContent())
                    .hotel(hotel)
                    .build());

            roomList.add(room);
        }

        hotel.setRooms(roomList);
        log.info("호텔명: " + hotel.getName() + "/" + "방 개수: " + hotel.getRooms().size());
        return HotelDto.fromEntity(hotelRepo.save(hotel));
    }

    public HotelDto readOneHotel(Long id) {
        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return HotelDto.fromEntity(hotel);
    }

    public HotelDto updateHotel(Long id, HotelDto hotelDto) {
        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 호텔 업데이트
        hotel.setName(hotelDto.getName());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setRegion(hotelDto.getRegion());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setImages(hotelDto.getImages());
        hotel.setAvg_score(hotelDto.getAvg_score()); //TODO: 사용자가 업데이트할때 Entity의 avg_score 업데이트 여부
        hotel.setMapX(hotelDto.getMapX());
        hotel.setMapY(hotelDto.getMapY());
        hotel.setPhone(hotelDto.getPhone());
        hotel = hotelRepo.save(hotel);

        // 방 업데이트
        List<RoomDto> roomDtoList = hotelDto.getRooms();
        List<Room> roomList = new ArrayList<>();
        for (RoomDto roomDto : roomDtoList) {
            Room room = roomRepo.findById(roomDto.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            room.setName(roomDto.getName());
            room.setPrice(roomDto.getPrice());
            room.setContent(roomDto.getContent());
            room.setHotel(hotel);
            room = roomRepo.save(room);

            roomList.add(room);
        }

        hotel.setRooms(roomList);
        return HotelDto.fromEntity(hotelRepo.save(hotel));
    }

    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        hotelRepo.delete(hotel);
    }

    public boolean checkRegion(String regionName)
    {
        int areaCode = AreaCode.getAreaCode(regionName);

        return areaCode !=0;
    }
}
