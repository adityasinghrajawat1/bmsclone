package com.driver.bookMyShow.Transformers;

import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterFacilityDto;
import com.driver.bookMyShow.Models.TheaterFacility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheaterFacilityTransformer {
    public static ReturnTheaterFacilityDto TheaterFacilityDtoToReturnTheaterFacilityDto(TheaterFacility theaterFacility) {
        ReturnTheaterFacilityDto returnTheaterFacilityDto = ReturnTheaterFacilityDto.builder()
                .facilityId(theaterFacility.getFacility().getId())
                .name(theaterFacility.getFacility().getName())
                .logo(theaterFacility.getFacility().getLogo())
                .isActive(theaterFacility.getIsActive())
                .build();
        return returnTheaterFacilityDto;
    }
}
