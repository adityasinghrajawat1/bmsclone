package com.driver.bookMyShow.Services;

import com.driver.bookMyShow.Dtos.RequestDtos.FacilityEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnFacilityDto;
import com.driver.bookMyShow.Models.Facility;

import java.util.List;

public interface FacilityService {
    ReturnFacilityDto addFacilityService(FacilityEntryDto facilityEntryDto);

    List<ReturnFacilityDto> getAllFacilityService();

    String deleteFacilityService(String facilityId);

    ReturnFacilityDto updateFacilityService(String facilityId, FacilityEntryDto facilityEntryDto);

    ReturnFacilityDto getFacilityById(String facilityId);

    ReturnFacilityDto facilityOnAndOffService(String facilityId);

    Boolean existsById(String id);

    Boolean existsByIdAndIsActiveTrue(String id);

    List<Facility> findAllByIdInAndIsActiveTrue(List<String> ids);
}
