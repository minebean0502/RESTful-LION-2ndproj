package com.hppystay.hotelreservation.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateHotelDto {

    @NotBlank(message = "숙소 이름을 입력해주세요.")
    @Length(max = 20)
    private String name;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "지역을 입력해주세요.")
    private String region;

    @NotBlank(message = "숙소를 설명해주세요.")
    private String description;

    @NotBlank(message = "이미지를 올려주세요.")
    private String images;

//    private String
//
//    @Builder.Default
//    private List<CreateRoomRequest> createRoomRequests = new ArrayList<>();
}
