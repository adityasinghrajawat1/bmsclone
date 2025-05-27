package com.driver.bookMyShow.Dtos.ResponseDtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnTheaterDetailsDto {
    private String theaterId;
    private String name;
    private String address;
}
