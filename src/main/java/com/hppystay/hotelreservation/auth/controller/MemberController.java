package com.hppystay.hotelreservation.auth.controller;

import com.hppystay.hotelreservation.auth.dto.CreateMemberDto;
import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.dto.PasswordChangeRequestDto;
import com.hppystay.hotelreservation.auth.dto.PasswordDto;
import com.hppystay.hotelreservation.auth.jwt.JwtRequestDto;
import com.hppystay.hotelreservation.auth.jwt.JwtResponseDto;
import com.hppystay.hotelreservation.auth.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 회원가입용 이메일 인증 코드 전송
    @PostMapping("/sign-up/send-code")
    public ResponseEntity<String> signUpSendCode(
            @RequestParam("email")
            String email
    ) {
        return memberService.signUpSendCode(email);
    }

    // 이메일 코드 인증
    @PostMapping("/email/verify")
    public ResponseEntity<String> verifyEmail(
            @RequestParam("email")
            String email,
            @RequestParam("code")
            String code
    ) {
        memberService.verifyEmail(email, code);
        return ResponseEntity.ok("{}");
    }

    // 패스워드 재발급용 이메일 인증 코드 전송
    @PostMapping("/password/send-code")
    public ResponseEntity<String> passwordSendCode(
            @RequestParam("email")
            String email
    ) {
        return memberService.passwordSendCode(email);
    }

    // 비밀번호 재발급
    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(
            @RequestBody
            PasswordChangeRequestDto requestDto
    ) {
        return memberService.resetPassword(requestDto);
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

