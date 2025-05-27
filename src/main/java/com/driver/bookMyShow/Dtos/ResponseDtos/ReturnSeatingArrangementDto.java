package com.driver.bookMyShow.Dtos.ResponseDtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnSeatingArrangementDto {
    private String seatName;
    private String seatType;
}
