package com.driver.bookMyShow.Dtos.ResponseDtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnTheaterDto {
    private String theaterId;
    private String name;
    private String address;
    private List<ReturnTheaterFacilityDto> facilities;
}
