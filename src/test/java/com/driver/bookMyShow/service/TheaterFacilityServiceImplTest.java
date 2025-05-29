package com.driver.bookMyShow.service;

import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterFacilityDto;
import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Models.TheaterFacility;
import com.driver.bookMyShow.Repositories.TheaterFacilityRepository;
import com.driver.bookMyShow.ServiceImpl.TheaterFacilityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TheaterFacilityServiceImplTest {

    @Mock
    private TheaterFacilityRepository theaterFacilityRepository;

    @InjectMocks
    private TheaterFacilityServiceImpl theaterFacilityService;

    private TheaterFacility theaterFacility;
    private Theater theater;
    private Facility facility;
    private ReturnTheaterFacilityDto expectedDto;

    @BeforeEach
    void setUp() {
        theater = new Theater();
        theater.setId("theater1");
        theater.setName("PVR Cinemas");
        theater.setAddress("Mall of India, Noida");

        facility = new Facility();
        facility.setId("facility1");
        facility.setName("Dolby Atmos");
        facility.setLogo("dolby_logo.png");
        facility.setIsActive(true);

        theaterFacility = new TheaterFacility();
        theaterFacility.setId("tf1");
        theaterFacility.setTheater(theater);
        theaterFacility.setFacility(facility);

        expectedDto = ReturnTheaterFacilityDto.builder()
                .facilityId("facility1")
                .name("Dolby Atmos")
                .logo("dolby_logo.png")
                .isActive(true)
                .build();
    }

    @Test
    void addTheaterFacility() {
        when(theaterFacilityRepository.save(any(TheaterFacility.class))).thenReturn(theaterFacility);

        ReturnTheaterFacilityDto result = theaterFacilityService.addTheaterFacility(theaterFacility);

        assertNotNull(result);
        assertEquals(facility.getId(), result.getFacilityId());
        assertEquals(facility.getName(), result.getName());
        assertEquals(facility.getLogo(), result.getLogo());
        assertEquals(facility.getIsActive(), result.getIsActive());
        verify(theaterFacilityRepository, times(1)).save(theaterFacility);
    }

    @Test
    void deleteByTheater() {
        theaterFacilityService.deleteByTheater(theater);
        verify(theaterFacilityRepository, times(1)).deleteByTheater(theater);
    }

    @Test
    void deleteById() {
        String id = "tf1";
        doNothing().when(theaterFacilityRepository).deleteById(id);

        theaterFacilityService.deleteById(id);
        verify(theaterFacilityRepository, times(1)).deleteById(id);
    }

    @Test
    void findByFacility() {
        List<TheaterFacility> expectedList = Arrays.asList(theaterFacility);
        when(theaterFacilityRepository.findByFacility(facility)).thenReturn(expectedList);

        List<TheaterFacility> result = theaterFacilityService.findByFacility(facility);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(theaterFacility, result.get(0));
        verify(theaterFacilityRepository, times(1)).findByFacility(facility);
    }

    @Test
    void saveAll() {
        List<TheaterFacility> tfList = Arrays.asList(theaterFacility);
        when(theaterFacilityRepository.saveAll(tfList)).thenReturn(tfList);

        theaterFacilityService.saveAll(tfList);

        verify(theaterFacilityRepository, times(1)).saveAll(tfList);
    }
}