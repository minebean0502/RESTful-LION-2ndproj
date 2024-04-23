package com.hppystay.hotelreservation.auth.config;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.entity.MemberRole;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminConfig {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //관리자 생성
    @PostConstruct
    public void createAdmin() {
        if (memberRepository.count() == 0) {
            memberRepository.save(Member.builder()
                    .nickname("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .role(MemberRole.ROLE_ADMIN)
                    .build());
        }
    }
}