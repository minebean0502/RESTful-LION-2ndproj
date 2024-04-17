package com.hppystay.hotelreservation.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberDto {
    @Pattern(regexp = "^[a-zA-Z가-힣]{3,}$", message = "닉네임은 세글자 이상의 영어 또는 한국어로 입력해주세요.")
    private String nickname;
    @Email(message = "유효한 이메일을 입력하세요.")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호는 영어와 숫자의 조합으로 8글자 이상으로 입력해주세요.")
    private String password;
    @Pattern(regexp = "ROLE_USER|ROLE_MANAGER", message = "유효한 역할을 선택해주세요.")
    private String role;
}
