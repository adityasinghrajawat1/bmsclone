package com.driver.bookMyShow.ServiceImpl;

import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterFacilityDto;
import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Models.TheaterFacility;
import com.driver.bookMyShow.Repositories.TheaterFacilityRepository;
import com.driver.bookMyShow.Services.TheaterFacilityService;
import com.driver.bookMyShow.Transformers.TheaterFacilityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterFacilityServiceImpl implements TheaterFacilityService {

    @Autowired
    private TheaterFacilityRepository theaterFacilityRepository;

    @Override
    public ReturnTheaterFacilityDto addTheaterFacility(TheaterFacility theaterFacility) {
        return TheaterFacilityTransformer.TheaterFacilityDtoToReturnTheaterFacilityDto(theaterFacilityRepository.save(theaterFacility));
    }

    @Override
    public void deleteByTheater(Theater theater) {
        theaterFacilityRepository.deleteByTheater(theater);
    }

    @Override
    public void deleteById(String id) {
        theaterFacilityRepository.deleteById(id);
    }

    @Override
    public List<TheaterFacility> findByFacility(Facility facility) {
        return theaterFacilityRepository.findByFacility(facility);
    }

    @Override
    public void saveAll(List<TheaterFacility> tfList) {
        theaterFacilityRepository.saveAll(tfList);
    }
}
