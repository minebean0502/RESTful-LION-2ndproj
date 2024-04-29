package com.hppystay.hotelreservation.auth.dto;

import com.hppystay.hotelreservation.auth.entity.ManagerRequest;
import com.hppystay.hotelreservation.auth.entity.ManagerRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerRequestDto {
    private Long id;
    private Long memberId;
    private String nickname;
    private String email;
    private String businessNumber;
    private ManagerRequestStatus status;

    public static ManagerRequestDto fromEntity(ManagerRequest request) {
        return ManagerRequestDto.builder()
                .id(request.getId())
                .memberId(request.getMember().getId())
                .nickname(request.getMember().getNickname())
                .email(request.getMember().getEmail())
                .businessNumber(request.getBusinessNumber())
                .status(request.getStatus())
                .build();
    }
}
