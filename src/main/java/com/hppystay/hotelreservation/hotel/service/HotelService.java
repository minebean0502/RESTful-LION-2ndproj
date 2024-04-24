package com.hppystay.hotelreservation.hotel.service;

import com.hppystay.hotelreservation.api.KNTO.utils.AreaCode;
import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.entity.MemberRole;
import com.hppystay.hotelreservation.common.exception.GlobalErrorCode;
import com.hppystay.hotelreservation.common.exception.GlobalException;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
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
    private final AuthenticationFacade facade;

    @Transactional
    public HotelDto createHotel(HotelDto hotelDto) {
        // 현재 멤버 불러오기
        Member member = facade.getCurrentMember();

        // 멤버 권한 확인
        if (!member.getRole().equals(MemberRole.ROLE_MANAGER))
            throw new GlobalException(GlobalErrorCode.NOT_AUTHORIZED_MEMBER);

        // 멤버가 가진 호텔 확인
        if (member.getHotel() != null)
            throw new GlobalException(GlobalErrorCode.ALREADY_MANAGER);

        // 호텔 생성
        Hotel hotel = Hotel.builder()
                .title(hotelDto.getTitle())
                .address(hotelDto.getAddress())
                .area(hotelDto.getArea())
                .description(hotelDto.getDescription())
                .firstImage(hotelDto.getFirstImage())
                .avg_score(0.0)
                .review_count(0L)
                .mapX(hotelDto.getMapX())
                .mapY(hotelDto.getMapY())
                .tel(hotelDto.getTel())
                .rooms(new ArrayList<>())
                .manager(member)
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
        log.info("호텔명: " + hotel.getTitle() + "/" + "방 개수: " + hotel.getRooms().size());
        return HotelDto.fromEntity(hotelRepo.save(hotel));
    }

    public HotelDto readOneHotel(Long id) {
        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //해당 호텔의 평균 별점과 총 리뷰 개수
        List<Object[]> result = hotelRepo.getHotelWithAll(id);

            Object[] data = result.get(0);
            Double avgScore = (Double) data[0];
            Long reviewCount = (Long) data[1];

            hotel.setAvg_score(avgScore);
            hotel.setReview_count(reviewCount);

            return HotelDto.fromEntity(hotelRepo.save(hotel));
    }

    public HotelDto updateHotel(Long id, HotelDto hotelDto) {
        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 호텔 업데이트
        hotel.setTitle(hotelDto.getTitle());
        hotel.setAddress(hotelDto.getAddress());
        hotel.setArea(hotelDto.getArea());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setFirstImage(hotelDto.getFirstImage());
        hotel.setAvg_score(hotelDto.getAvg_score()); //TODO: 사용자가 업데이트할때 Entity의 avg_score 업데이트 여부
        hotel.setMapX(hotelDto.getMapX());
        hotel.setMapY(hotelDto.getMapY());
        hotel.setTel(hotelDto.getTel());
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

    // 기존 호텔에 방만 추가하는 경우
    public HotelDto addRoom(RoomDto roomDto, Long hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Room room = roomRepo.save(Room.builder()
                .name(roomDto.getName())
                .price(roomDto.getPrice())
                .content(roomDto.getContent())
                .hotel(hotel)
                .build());

        return HotelDto.fromEntity(hotelRepo.save(hotel.addRoom(room)));
    }
  
    public boolean checkRegion(String regionName) {
        int areaCode = AreaCode.getAreaCode(regionName);

        return areaCode !=0;
    }
}
