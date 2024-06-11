package com.hppystay.hotelreservation.auth.controller;

import com.hppystay.hotelreservation.auth.dto.*;
import com.hppystay.hotelreservation.auth.jwt.JwtRequestDto;
import com.hppystay.hotelreservation.auth.jwt.JwtResponseDto;
import com.hppystay.hotelreservation.auth.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "User", description = "User 관련 API입니다.")
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
    public ResponseEntity<String> signIn(
            @RequestBody
            LoginDto dto,
            HttpServletResponse response
    ) {
        memberService.signIn(dto, response);
        return ResponseEntity.ok("{}");
    }

//    @PostMapping("/sign-in")
//    public JwtResponseDto signIn(
//            @RequestBody
//            JwtRequestDto dto
//    ) {
//        return memberService.issueToken(dto);
//    }

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
            @Valid
            @RequestBody
            PasswordChangeRequestDto requestDto
    ) {
        return memberService.resetPassword(requestDto);
    }

    // 비밀번호 변경
    @PutMapping("/password/change")
    public ResponseEntity<String> changePassword(
            @Valid
            @RequestBody
            PasswordDto dto
    ) {
        return memberService.changePassword(dto);
    }

    @PostMapping("/profile-upload")
    public ResponseEntity<String> uploadProfileImage(
            @RequestPart("image")
            MultipartFile image
    ) {
        memberService.uploadProfileImage(image);
        return ResponseEntity.ok("{}");
    }

    @GetMapping("/my-profile")
    public MemberProfileDto getMyProfile() {
        return memberService.getMyProfile();
    }

    @PostMapping("/manager-requests")
    public ResponseEntity<String> requestManagerRole(
            @RequestParam("business-number")
            String businessNumber
    ) {
        memberService.requestManagerRole(businessNumber);
        return ResponseEntity.ok("{}");
    }

    @GetMapping("/manager-requests/list")
    public List<ManagerRequestDto> readAllManagerRequests() {
        return memberService.readAllManagerRequests();
    }

    @PostMapping("/manager-requests/{requestId}/approve")
    public ResponseEntity<String> approveManagerRole(
            @PathVariable("requestId")
            Long requestId
    ) {
        memberService.approveManagerRole(requestId);
        return ResponseEntity.ok("{}");
    }

    @PostMapping("/manager-requests/{requestId}/reject")
    public ResponseEntity<String> rejectManagerRole(
            @PathVariable("requestId")
            Long requestId
    ) {
        memberService.rejectManagerRole(requestId);
        return ResponseEntity.ok("{}");
    }

    @GetMapping("/login-info")
    public ResponseEntity<UserInfoDto> getLoginInfo() {
        return memberService.getLoginInfo();
    }
}