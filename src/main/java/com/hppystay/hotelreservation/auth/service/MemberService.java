package com.hppystay.hotelreservation.auth.service;

import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.jwt.JwtRequestDto;
import com.hppystay.hotelreservation.auth.jwt.JwtResponseDto;
import com.hppystay.hotelreservation.auth.jwt.JwtTokenFilter;
import com.hppystay.hotelreservation.auth.jwt.JwtTokenUtils;
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
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;


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

    // 해당 이메일로 가입한 아이디 확인
    public boolean userExists(String email) {
        return memberRepository.existsByEmail(email);

    }

    //로그인 (jwt 발급)
    public JwtResponseDto issueToken(JwtRequestDto dto) {
        //아이디 확인
        if (!userExists(dto.getEmail()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        UserDetails userDetails = loadUserByUsername(dto.getEmail());

        //비밀번호 같은지 확인
        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        String jwt = jwtTokenUtils.generateToken((Member) userDetails);
        JwtResponseDto response =  new JwtResponseDto();
        response.setToken(jwt);

        return response;
    }
}
