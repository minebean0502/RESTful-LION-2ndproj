package com.hppystay.hotelreservation.hotel;

import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.entity.Room;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
import com.hppystay.hotelreservation.hotel.repository.ReservationRepository;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestData {

    private final MemberRepository memberRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public TestData(MemberRepository memberRepository,
                    HotelRepository hotelRepository,
                    RoomRepository roomRepository,
                    ReservationRepository reservationRepository)
    {
        this.memberRepository = memberRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;

        testHotels();
        testRooms();
    }

    private void testHotels()
    {
        hotelRepository.saveAll(
                List.of(
                        Hotel.builder()
                        .name("호텔1")
                        .address("대전 유성구")
                        .region("대전")
                        .description("좋은 호텔이다.")
                        .images("sss")
                        .mapX("13.4")
                        .mapX("13.5")
                        .phone("010-011")
                        .build(),
                        Hotel.builder()
                                .name("호텔2")
                                .address("대구 달서구")
                                .region("대구")
                                .description("좋은 호텔이다2.")
                                .images("sss")
                                .mapX("13.5")
                                .mapX("13.6")
                                .phone("010-012")
                                .build(),
                        Hotel.builder()
                                .name("호텔3")
                                .address("서울 강남")
                                .region("강남")
                                .description("좋은 호텔이다.3")
                                .images("sss")
                                .mapX("13.5")
                                .mapX("13.6")
                                .phone("010-013")
                                .build()

        ));
    }

    private void testRooms()
    {
        hotelRepository.findAll()
                .forEach((this::addRooms));
    }

    private void addRooms(Hotel hotel)
    {
        List<String> rooms = List.of(
          "first room",
          "second room",
          "third room"
        );

        rooms.forEach(room -> roomRepository.save(Room.builder()
                .hotel(hotel)
                .name(room + hotel.getName())
                .price(room.length()* 5000)
                .content(hotel.getName() + " " + room)
                .build()));
    }

}
