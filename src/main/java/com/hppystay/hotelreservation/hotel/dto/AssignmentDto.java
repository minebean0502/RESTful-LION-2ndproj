package com.hppystay.hotelreservation.hotel.dto;

import com.hppystay.hotelreservation.hotel.entity.Assignment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssignmentDto {
    private Long id;
    private Long reservationId;
    private Long fromUserId;
    private Long toUserId;
    private String status;

    public static AssignmentDto fromEntity(Assignment assignment) {
        return AssignmentDto.builder()
                .id(assignment.getId())
                .reservationId(assignment.getReservation().getId())
                .fromUserId(assignment.getFromUser().getId())
                .toUserId(assignment.getToUser().getId())
                .status(assignment.getStatus().name())
                .build();
    }
}
