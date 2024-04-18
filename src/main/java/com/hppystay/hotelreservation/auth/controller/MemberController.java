package com.hppystay.hotelreservation.auth.controller;

import com.hppystay.hotelreservation.auth.dto.CreateMemberDto;
import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.dto.PasswordDto;
import com.hppystay.hotelreservation.auth.jwt.JwtRequestDto;
import com.hppystay.hotelreservation.auth.jwt.JwtResponseDto;
import com.hppystay.hotelreservation.auth.service.MemberService;
import jakarta.validation.Valid;
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
            @Valid
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

    // 비밀번호 찾기(아이디 입력시 인증코드 발송)
    @PostMapping("/password/find")
    public ResponseEntity<String> findPassword(
            @RequestParam("email")
            String email
    ) {
        memberService.sendVerifyCode(email);
        return ResponseEntity.ok("{}");
    }

    // 비밀번호 인증 코드 입력 후 비밀번호 변경
    @PostMapping("/password/code")
    public ResponseEntity<String> PasswordCode(
            @RequestParam("email")
            String email,
            @RequestParam("code")
            String code,
            @RequestParam("newPassword")
            String newPassword
    ) {
        return memberService.passwordCode(email,code,newPassword);
    }

    // 비밀번호 변경
    @PutMapping("/password/change")
    public ResponseEntity<String> changePassword(
            @RequestBody
            PasswordDto dto
    ) {
       return memberService.changePassword(dto);
    }
}

