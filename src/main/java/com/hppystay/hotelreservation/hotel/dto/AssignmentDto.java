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
    private Long reservationId;
    private String price;
    private String tossOrderId;
    private String itemName;
    private LocalDateTime assignedAt;

    public static AssignmentDto fromEntity(Assignment assignment) {
        return AssignmentDto.builder()
                .id(assignment.getId())
                .fromMemberId(assignment.getFromMember() != null ? assignment.getFromMember().getId() : null)
                .toMemberId(assignment.getToMember() != null ? assignment.getToMember().getId() : null)
                .reservationId(assignment.getReservation() != null ? assignment.getReservation().getId() : null)
                .price(assignment.getPrice())
                .tossOrderId(assignment.getTossOrderId())
                .itemName(assignment.getItemName())
                .assignedAt(assignment.getAssignedAt())
                .build();
    }
}
