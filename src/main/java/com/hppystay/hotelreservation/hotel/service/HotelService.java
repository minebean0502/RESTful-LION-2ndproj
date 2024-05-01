package com.hppystay.hotelreservation.hotel.service;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.common.exception.GlobalErrorCode;
import com.hppystay.hotelreservation.common.exception.GlobalException;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.dto.HotelDto;
import com.hppystay.hotelreservation.hotel.dto.RoomDto;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.entity.Room;
import com.hppystay.hotelreservation.hotel.entity.HotelLike;
import com.hppystay.hotelreservation.hotel.repository.HotelLikeRepository;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
import com.hppystay.hotelreservation.hotel.repository.ReservationRepository;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepo;
    private final RoomRepository roomRepo;
    private final ReservationRepository reservationRepo;
    private final HotelLikeRepository hotelLikeRepo;
    private final AuthenticationFacade facade;

    @Transactional
    public HotelDto createHotel(HotelDto hotelDto) {
        // 현재 멤버 불러오기
        Member member = facade.getCurrentMember();

        // 멤버가 가진 호텔 확인
        if (member.getHotel() != null)
            throw new GlobalException(GlobalErrorCode.HOTEL_ALREADY_CREATED);

        // 호텔 생성
        Hotel hotel = Hotel.builder()
                .title(hotelDto.getTitle())
                .address(hotelDto.getAddress())
                .area(hotelDto.getArea())
                .description(hotelDto.getDescription())
                .firstImage(hotelDto.getFirstImage())
                .avg_score(0.0)
                .review_count(0L)
                .like_count(0L)
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
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.HOTEL_NOT_FOUND));

        return HotelDto.fromEntity(hotel);
    }

    public HotelDto readOneHotelReservationPossible(Long id, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isAfter(checkOut))
            throw new GlobalException(GlobalErrorCode.CHECKIN_AFTER_CHECKOUT);

        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.HOTEL_NOT_FOUND));
        List<Long> unavailableRoomIds = reservationRepo.findUnavailableRoomIds(checkIn, checkOut);

        HotelDto dto = HotelDto.fromEntity(hotel);
        dto.setRooms(hotel.getRooms().stream()
                .filter(room -> !unavailableRoomIds.contains(room.getId()))
                .map(RoomDto::fromEntity)
                .toList());

        return dto;
    }

    // 예약 가능한 호텔과 방 조회
    public List<HotelDto> readHotelsReservationPossible(String keyword, LocalDate checkIn, LocalDate checkOut) {
        List<Hotel> hotels = hotelRepo.searchByKeyword(keyword);
        List<Long> unavailableRoomIds = reservationRepo.findUnavailableRoomIds(checkIn, checkOut);

        return hotels.stream()
                .map(HotelDto::fromEntity)
                .map(hotel -> {
                    hotel.setRooms(hotel.getRooms().stream()
                            .filter(room -> !unavailableRoomIds.contains(room.getId())) // 예약된 방 제외
                            .toList());
                    return hotel;
                })
                .filter(hotel -> !hotel.getRooms().isEmpty()) // 모든 방이 예약된 호텔 제외
                .toList();
    }

    public List<HotelDto> searchHotelsAvailable(String keyword, LocalDate checkIn, LocalDate checkOut, String sort) {
        if (checkIn.isAfter(checkOut))
            throw new GlobalException(GlobalErrorCode.CHECKIN_AFTER_CHECKOUT);

        return hotelRepo.searchByKeywordAndDateRangeAndSort(keyword, checkIn, checkOut, sort)
                .stream().map(HotelDto::fromEntity).toList();
    }

    @Transactional
    public HotelDto updateHotel(Long id, HotelDto hotelDto) {
        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.HOTEL_NOT_FOUND));

        Member member = facade.getCurrentMember();

        // 매니저가 아닌 경우
        if (!hotel.getManager().getId().equals(member.getId()))
            throw new GlobalException(GlobalErrorCode.NOT_AUTHORIZED_MANAGER);

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

        // 방 업데이트
        Map<Long, RoomDto> roomDtoMap = hotelDto.getRooms().stream()
                .collect(Collectors.toMap(RoomDto::getId, dto -> dto));
        List<Room> updatedRooms = new ArrayList<>();

        for (Room room : hotel.getRooms()) {
            RoomDto roomDto = roomDtoMap.get(room.getId());
            if (roomDto != null) {
                if (!room.getHotel().getId().equals(id)) {
                    throw new GlobalException(GlobalErrorCode.HOTEL_ROOM_MISMATCH);
                }
                room.setName(roomDto.getName());
                room.setPrice(roomDto.getPrice());
                room.setContent(roomDto.getContent());
                updatedRooms.add(room);
                roomDtoMap.remove(room.getId());
            } else {
                roomRepo.delete(room); // 변경된 객실이 없는 경우 삭제
            }
        }

        // 변경된 객실이 추가된 경우 처리
        for (RoomDto roomDto : roomDtoMap.values()) {
            Room newRoom = Room.builder()
                    .name(roomDto.getName())
                    .price(roomDto.getPrice())
                    .content(roomDto.getContent())
                    .hotel(hotel)
                    .build();
            updatedRooms.add(roomRepo.save(newRoom));
        }

        hotel.setRooms(updatedRooms);

        return HotelDto.fromEntity(hotelRepo.save(hotel));
    }

    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.HOTEL_NOT_FOUND));

        hotelRepo.delete(hotel);
    }

    public void toggleLike(Long hotelId) {
        Member member = facade.getCurrentMember();

        Hotel hotel = hotelRepo.findById(hotelId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.HOTEL_NOT_FOUND));

        if (!hotelLikeRepo.existsByMemberAndHotel(member, hotel)) {
            // 호텔의 like_count 증가
            hotel.setLike_count(hotel.getLike_count() + 1);
            hotelLikeRepo.save(new HotelLike(member, hotel));

        } else {
            // 좋아요가 있는 상태에서 한번 더 좋아요 하면 좋아요 취소
            hotel.setLike_count(hotel.getLike_count() - 1);
            hotelLikeRepo.deleteByMemberAndHotel(member, hotel);
        }
    }
}
