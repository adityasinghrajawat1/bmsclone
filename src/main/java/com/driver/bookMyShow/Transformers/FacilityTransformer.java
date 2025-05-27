package com.driver.bookMyShow.Transformers;

import com.driver.bookMyShow.Dtos.RequestDtos.FacilityEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnFacilityDto;
import com.driver.bookMyShow.Models.Facility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityTransformer {
    public static Facility FacilityEntryDtoToFacility(FacilityEntryDto facilityEntryDto) {
        Facility facility = Facility.builder()
                .name(facilityEntryDto.getName())
                .logo(facilityEntryDto.getLogo())
                .build();
        return facility;
    }

    public static ReturnFacilityDto FacilityToReturnFacilityDto(Facility facility) {
        ReturnFacilityDto returnFacilityDto = ReturnFacilityDto.builder()
                .facilityId(facility.getId())
                .name(facility.getName())
                .logo(facility.getLogo())
                .isActive(facility.getIsActive())
                .build();
        return returnFacilityDto;
    }
}
