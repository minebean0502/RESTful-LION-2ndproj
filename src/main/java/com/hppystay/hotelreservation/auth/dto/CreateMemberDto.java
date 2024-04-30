package com.hppystay.hotelreservation.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberDto {
    @Pattern(regexp = "^[a-zA-Z가-힣]{3,12}$", message = "닉네임은 영어 또는 한국어로 3글자 이상, 12글자 이하여야 합니다.")
    private String nickname;
    @Email(message = "유효한 이메일을 입력하세요.")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()-_+=]{8,15}$",
            message = "비밀번호는 영어와 숫자가 포함된 8자리 이상, 15자리 이하의 문자열이어야 합니다.")
    private String password;
}
