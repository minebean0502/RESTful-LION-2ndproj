package com.hppystay.hotelreservation.payment.toss.temp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TempMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String role;
    private String businessNumber;


    // Member 정보에는
    // Pk, 정보들과
    // reservation의 정보들 (리스트)와

}
