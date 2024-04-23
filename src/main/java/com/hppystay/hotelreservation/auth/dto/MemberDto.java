package com.hppystay.hotelreservation.auth.dto;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.entity.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String profileImage;
    private MemberRole role;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .password(member.getPassword())
                .profileImage(member.getProfileImage())
                .role(member.getRole())
                .build();
    }
}
