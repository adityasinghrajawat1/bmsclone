package com.driver.bookMyShow.Dtos.ResponseDtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnCapacityDto {
    private int diamond;
    private int gold;
    private int silver;
}
