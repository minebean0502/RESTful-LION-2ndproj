package com.hppystay.hotelreservation.hotel.config;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.entity.Room;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class HotelAndRoomConfig {
    private final HotelRepository hotelRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;


    @Bean
    public CommandLineRunner createHotel() {
        return args -> {
            if (hotelRepository.count() == 0 ) {
                // manager11부터 manager15까지의 멤버를 가져옵니다.
                List<Member> managers = memberRepository.findByNicknameIn(
                        IntStream.rangeClosed(11, 15)
                                .mapToObj(i -> String.format("manager %d", i))
                                .collect(Collectors.toList())
                );
                List<Hotel> hotels = List.of(
                        Hotel.builder()
                                .title("강남스테이힐(Gangnam Stay Hill)")
                                .address("서울특별시 강남구 테헤란로13길 65")
                                .area("서울")
                                .description("강남 스테이힐 입니다")
                                .firstImage("http://tong.visitkorea.or.kr/cms/resource/43/2819443_image2_1.jpg")
                                .avg_score(0.0)
                                .review_count(0L)
                                .mapX("127.0301236613")
                                .mapY("37.5034291706")
                                .tel("010-2746-7882")
                                .like_count(0L)
                                .rooms(new ArrayList<>())
                                .build(),
                        Hotel.builder()
                                .title("게더링 앳 홍대1(게더링앳홍대)")
                                .address("서울특별시 마포구 동교로47길 3")
                                .area("서울")
                                .description("홍대 게더링 입니다")
                                .firstImage("http://tong.visitkorea.or.kr/cms/resource/93/2571393_image2_1.PNG")
                                .avg_score(0.0)
                                .review_count(0L)
                                .mapX("126.9248537833")
                                .mapY("37.5632120044")
                                .tel("010-5155-9555")
                                .like_count(0L)
                                .rooms(new ArrayList<>())
                                .build(),
                        Hotel.builder()
                                .title("게스트하우스 류가(Guest House Ryuga)")
                                .address("서울특별시 마포구 독막로7길 19")
                                .area("서울")
                                .description("류가 호텔")
                                .firstImage("https://tong.visitkorea.or.kr/cms/resource/07/2573607_image2_1.JPG")
                                .avg_score(0.0)
                                .review_count(0L)
                                .mapX("126.9187769621")
                                .mapY("37.5489302228")
                                .tel("010-5417-1955")
                                .like_count(0L)
                                .rooms(new ArrayList<>())
                                .build(),
                        Hotel.builder()
                                .title("고운[한국관광 품질인증/Korea Quality]")
                                .address("서울특별시 종로구 북촌로12길 35-1")
                                .area("서울")
                                .description("고운")
                                .firstImage("http://tong.visitkorea.or.kr/cms/resource/08/2948908_image2_1.JPG")
                                .avg_score(0.0)
                                .review_count(0L)
                                .mapX("126.9858393299")
                                .mapY("37.5830052419")
                                .tel("0504-0904-2464")
                                .like_count(0L)
                                .rooms(new ArrayList<>())
                                .build(),
                        Hotel.builder()
                                .title("그랜드 인터컨티넨탈 서울 파르나스")
                                .address("서울특별시 강남구 테헤란로 521")
                                .area("서울")
                                .description("컨티넨탈 호텔입니다")
                                .firstImage("http://tong.visitkorea.or.kr/cms/resource/45/2705645_image2_1.jpg")
                                .avg_score(0.0)
                                .review_count(0L)
                                .mapX("127.0608845757")
                                .mapY("37.5095984514")
                                .tel("02-555-5656")
                                .like_count(0L)
                                .rooms(new ArrayList<>())
                                .build());

                for (int i = 0; i < managers.size(); i++) {
                    hotels.get(i).setManager(managers.get(i));
                }
                hotelRepository.saveAll(hotels);

//                // 호텔에 매니저 할당 로직
//                for (int i = 1; i <= 5; i++) {
//                    final int id = i;
//                    memberRepository.findById((long) id).ifPresent(manager -> {
//                        hotelRepository.findById((long) id).ifPresent(hotel -> {
//                            hotel.setManager(manager);
//                            hotelRepository.save(hotel);
//                        });
//                    });
//                }
//                for (int i = 1; i <= 5 ; i++) {
//                    final int id = i;
//                    hotelRepository.findById((long) id).ifPresent(hotel -> {
//                        memberRepository.findById((long) id).ifPresent(member -> {
//                            member.setHotel(hotel);
//                            memberRepository.save(member);
//                        });
//                    });
//                }

                createRoomsForHotel(1L, 3);
                createRoomsForHotel(2L, 2);
                createRoomsForHotel(3L, 1);
                createRoomsForHotel(4L, 1);
                createRoomsForHotel(5L, 1);


            }
        };
    }
    private void createRoomsForHotel(Long hotelId, int roomCount) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);
        if (hotelOpt.isPresent()) {
            Hotel hotel = hotelOpt.get();
            List<Room> rooms = new ArrayList<>();
            for (int i = 1; i <= roomCount; i++) {
                Room room = Room.builder()
                        .name(hotelId + "번 호텔의 " + i + "번 룸")
                        .price(String.valueOf((i * 10000)))
                        .content(hotelId + "번 호텔의 " + i + "번 룸 설명")
                        .hotel(hotel)
                        .build();
                rooms.add(room);
            }
            roomRepository.saveAll(rooms);
            hotel.setRooms(rooms);
            hotelRepository.save(hotel);
        }
    }
}
