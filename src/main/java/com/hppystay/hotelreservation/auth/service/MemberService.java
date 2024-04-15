package com.hppystay.hotelreservation.auth.service;

import com.hppystay.hotelreservation.auth.dto.CreateMemberDto;
import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.entity.MemberRole;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto signUp(CreateMemberDto createMemberDto) {
        // 이메일 중복 체크
        if (memberRepository.existsByEmail(createMemberDto.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Member member = Member.builder()
                .nickname(createMemberDto.getNickname())
                .email(createMemberDto.getEmail())
                .password(passwordEncoder.encode(createMemberDto.getPassword()))
                .role(MemberRole.valueOf(createMemberDto.getRole()))
                .build();
        return MemberDto.fromEntity(memberRepository.save(member));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(username);

        if (optionalMember.isEmpty())
            throw new UsernameNotFoundException("email not found");
        Member member = optionalMember.get();

        return CustomUserDetails.builder()
                .member(member)
                .build();
    }
}
