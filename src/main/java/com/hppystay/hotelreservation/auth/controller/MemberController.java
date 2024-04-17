package com.hppystay.hotelreservation.auth.controller;

import com.hppystay.hotelreservation.auth.dto.CreateMemberDto;
import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.jwt.JwtRequestDto;
import com.hppystay.hotelreservation.auth.jwt.JwtResponseDto;
import com.hppystay.hotelreservation.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return memberService.issueToken(dto);
    }

    @PostMapping("/email/verify")
    public ResponseEntity<String> sendVerifyCode(
            @RequestParam("email")
            String email
    ) {
        memberService.sendVerifyCode(email);
        return ResponseEntity.ok("{}");
    }

    //비밀번호 찾기 페이지
    @GetMapping("/find/password")
    public String findPassword(
            @RequestParam("email")
            String email
    ){
    }


}
