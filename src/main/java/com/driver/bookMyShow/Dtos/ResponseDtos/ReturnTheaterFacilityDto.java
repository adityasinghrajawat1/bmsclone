package com.driver.bookMyShow.Dtos.ResponseDtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnTheaterFacilityDto {
    public String facilityId;
    public String name;
    public String logo;
    public Boolean isActive;
}
