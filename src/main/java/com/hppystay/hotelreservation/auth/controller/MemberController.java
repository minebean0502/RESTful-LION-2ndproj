package com.hppystay.hotelreservation.auth.controller;

import com.hppystay.hotelreservation.auth.dto.CreateMemberDto;
import com.hppystay.hotelreservation.auth.dto.MemberDto;
<<<<<<< HEAD
import com.hppystay.hotelreservation.auth.jwt.JwtRequestDto;
import com.hppystay.hotelreservation.auth.jwt.JwtResponseDto;
=======
import com.hppystay.hotelreservation.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
>>>>>>> main
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public MemberDto signUp(
            @RequestBody
            CreateMemberDto createMemberDto
    ) {
        return memberService.signUp(createMemberDto);
    }

    @PostMapping("/sign-in")
    public JwtResponseDto signIn(
            @RequestBody
            JwtRequestDto dto
    ) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
