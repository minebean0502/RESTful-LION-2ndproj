package com.hppystay.hotelreservation.hotel.temp;

import com.hppystay.hotelreservation.hotel.entity.Reservation;
import com.hppystay.hotelreservation.hotel.entity.ReservationStatus;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TempDto {
    private Long id;
    private Long memberId; // 멤버 ID 사용
    private String roomName; // 예시로 추가한 필드
    private Integer numberOfPeople;
    private ReservationStatus status;

    // Reservation 엔티티로부터 DTO 생성
    public static TempDto fromEntity(Reservation reservation) {
        TempDto dto = new TempDto();
        dto.setId(reservation.getId());
        dto.setMemberId(reservation.getMember().getId()); // Member 엔티티에서 ID 추출
        dto.setRoomName(reservation.getRoom().getName()); // 예시, 실제 구현에 따라 변경 필요
        //dto.setNumberOfPeople(reservation.getNumberOfPeople());
        dto.setStatus(reservation.getStatus());
        return dto;
    }
}

