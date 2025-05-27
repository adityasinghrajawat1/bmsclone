package com.driver.bookMyShow.Dtos.ResponseDtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnShowSeatDto {
    private String seatType;
    private int price;
}
