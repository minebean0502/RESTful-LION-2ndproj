package com.hppystay.hotelreservation.auth.config;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.entity.MemberRole;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UserConfig {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final HotelRepository hotelRepository;

    @PostConstruct
    public void createTestUsers() {
        createAdmin();
        createUser();
        createManager();
    }


    public void createAdmin() {
        memberRepository.save(Member.builder()
                .nickname("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .role(MemberRole.ROLE_ADMIN)
                .build());
    }

    // 채운 수정 04-25~
    //테스트용 계정 추가
    // 유저
    public void createUser() {
        for (int i = 1; i <= 10; i++) {
            memberRepository.save(Member.builder()
                    .nickname(String.format("user %d", i))
                    .email(String.format("user%d@gmail.com", i))
                    .password(passwordEncoder.encode("1234"))
                    .role(MemberRole.ROLE_USER)
                    .build());
        }
    }

    // 매니저
    public void createManager() {
        for (int i = 11; i <= 20; i++) {
            memberRepository.save(Member.builder()
                    .nickname(String.format("manager %d", i))
                    .email(String.format("manager%d@gmail.com", i))
                    .password(passwordEncoder.encode("1234"))
                    .role(MemberRole.ROLE_MANAGER)
                    .build());
        }
    }
}