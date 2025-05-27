package com.driver.bookMyShow.Transformers;

import com.driver.bookMyShow.Dtos.RequestDtos.TheaterEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterDetailsDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterFacilityDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterDto;
import com.driver.bookMyShow.Models.TheaterFacility;
import com.driver.bookMyShow.Models.Theater;

import java.util.List;
import java.util.stream.Collectors;

public class TheaterTransformer {
    public static Theater TheatreDtoToTheater(TheaterEntryDto theaterEntryDto, List<TheaterFacility> theaterFacilityList) {
        Theater theater = Theater.builder()
                .name(theaterEntryDto.getName())
                .address(theaterEntryDto.getAddress())
                .theaterFacilityList(theaterFacilityList)
                .build();
        return theater;
    }

    public static ReturnTheaterDto TheaterToReturnTheaterDto(Theater theater) {
        List<ReturnTheaterFacilityDto> returnTheaterFacilityDtos = theater.getTheaterFacilityList().stream()
                //.filter(facility -> !facility.getDeleted())
                .map(TheaterFacilityTransformer::TheaterFacilityDtoToReturnTheaterFacilityDto)
                .collect(Collectors.toList());

        ReturnTheaterDto returnTheaterDto = ReturnTheaterDto.builder()
                .theaterId(theater.getId())
                .address(theater.getAddress())
                .name(theater.getName())
                .facilities(returnTheaterFacilityDtos)
                .build();
        return returnTheaterDto;
    }

    public static ReturnTheaterDetailsDto TheaterToReturnTheaterDetailsDto(Theater theater) {
        ReturnTheaterDetailsDto rtd = ReturnTheaterDetailsDto.builder()
                .theaterId(theater.getId())
                .name(theater.getName())
                .address(theater.getAddress())
                .build();
        return rtd;
    }
}