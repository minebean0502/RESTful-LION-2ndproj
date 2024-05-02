package com.hppystay.hotelreservation.hotel.dto;

import com.hppystay.hotelreservation.hotel.entity.Assignment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AssignmentDto {
    private Long id;
    private Long fromMemberId;
    private Long toMemberId;
    // 기존 A
    private Long reservationId;
    // 이후 B
    private Long toReservationId;
    private String price;
    private String tossOrderId;
    private String itemName;
//    private LocalDateTime assignedAt;

    public static AssignmentDto fromEntity(Assignment assignment) {
        return AssignmentDto.builder()
                .id(assignment.getId())
                .fromMemberId(assignment.getFromMember() != null ? assignment.getFromMember().getId() : null)
                .toMemberId(assignment.getToMember() != null ? assignment.getToMember().getId() : null)
                .reservationId(assignment.getFromReservation() != null ? assignment.getFromReservation().getId() : null)
                .toReservationId(assignment.getToReservation().getId())
                .price(assignment.getPrice())
                .tossOrderId(assignment.getTossOrderId())
                .itemName(assignment.getItemName())
//                .assignedAt(assignment.getAssignedAt())
                .build();
    }
}
