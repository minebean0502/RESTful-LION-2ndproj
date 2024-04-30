package com.hppystay.hotelreservation.auth.dto;

import com.hppystay.hotelreservation.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileDto {
    private String nickname;
    private String email;
    private String profileImage;

    public static MemberProfileDto fromEntity(Member entity) {
        return MemberProfileDto.builder()
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .profileImage(entity.getProfileImage())
                .build();
    }
}
