package com.hppystay.hotelreservation.payment.toss.temp.config;

import com.hppystay.hotelreservation.payment.toss.temp.entity.TempMemberEntity;
import com.hppystay.hotelreservation.payment.toss.temp.entity.TempReservationEntity;
import com.hppystay.hotelreservation.payment.toss.temp.repository.TempMemberRepository;
import com.hppystay.hotelreservation.payment.toss.temp.repository.TempReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TempConfig {
    private final PasswordEncoder passwordEncoder;
    private final TempReservationRepository reservationRepository;
    private final TempMemberRepository memberRepository;

    // config로 temp member값들 넣어주기
    @Bean
    public CommandLineRunner createMember() {
        return args -> {
            if (memberRepository.count() == 0) {
                memberRepository.saveAll(List.of(
                        TempMemberEntity.builder()
                                .nickname("chaewoon")
                                .email("no4323@naver.com")
                                .password(passwordEncoder.encode("1234"))
                                .role("USER")
                                .build(),
                        TempMemberEntity.builder()
                                .nickname("Notchaewoon")
                                .email("no4323@gmail.com")
                                .password(passwordEncoder.encode("1234"))
                                .role("USER")
                                .build()
                ));
            }
        };
    }

    // config로 temp reservation 값들 넣어주기
    @Bean
    public CommandLineRunner createReservation() {
        return args -> {
            if (reservationRepository.count() == 0) {
                reservationRepository.saveAll(List.of(
                        TempReservationEntity.builder()
                                .memberEmail("user1@gmail.com")
                                .roomId("500번방")
                                .numberOfPeople("5")
                                .price("50000")
                                .status("IN_PROGRESS")
                                // 나중에 지울곳
                                .imageUrl("/static/tossImage/hotel1.png")
                                .member(1L)
                                .build(),
                        TempReservationEntity.builder()
                                .memberEmail("user2@gmail.com")
                                .roomId("1번방")
                                .numberOfPeople("1")
                                .price("10000")
                                .status("IN_PROGRESS")
                                // 나중에 지울곳
                                .imageUrl("/static/tossImage/hotel2.png")
                                .member(2L)
                                .build(),
                        TempReservationEntity.builder()
                                .memberEmail("user3@gmail.com")
                                .roomId("2번방")
                                .numberOfPeople("10")
                                .price("12345")
                                .status("IN_PROGRESS")
                                // 나중에 지울곳
                                .imageUrl("/static/tossImage/hotel3.png")
                                .member(1L)
                                .build(),
                        TempReservationEntity.builder()
                                .memberEmail("user4@gmail.com")
                                .roomId("10번방")
                                .numberOfPeople("1")
                                .price("20000")
                                .status("IN_PROGRESS")
                                // 나중에 지울곳
                                .imageUrl("/static/tossImage/hotel4.png")
                                .member(2L)
                                .build()
                ));
            }
        };
    }
}
