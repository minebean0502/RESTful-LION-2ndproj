package com.hppystay.hotelreservation.hotel.entity;

public enum AssignmentStatus {
    PENDING,        // 양도가 요청되었지만 아직 처리되지 않음
    COMPLETED,      // 양도가 성공적으로 완료됨
    FAILED,         // 양도가 실패함
    CANCELED,       // 양도가 취소됨
    ASSIGNMENT_PENDING;  // 양도 대기 중
}
