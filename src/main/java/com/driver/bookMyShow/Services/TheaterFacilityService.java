package com.driver.bookMyShow.Services;

import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterFacilityDto;
import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Models.TheaterFacility;

import java.util.List;

public interface TheaterFacilityService {
    ReturnTheaterFacilityDto addTheaterFacility(TheaterFacility theaterFacility);

    void deleteByTheater(Theater theater);

    void deleteById(String id);

    List<TheaterFacility> findByFacility(Facility facility);

    void saveAll(List<TheaterFacility> tfList);
}
