package com.driver.bookMyShow.service;

import com.driver.bookMyShow.Dtos.RequestDtos.FacilityEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnFacilityDto;
import com.driver.bookMyShow.Exceptions.AlreadyPresentException;
import com.driver.bookMyShow.Exceptions.NotFoundException;
import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Repositories.FacilityRepository;
import com.driver.bookMyShow.Services.TheaterFacilityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacilityServiceImplTest
{
    @InjectMocks
    private FacilityServiceImpl facilityService;

    @Mock
    private FacilityRepository facilityRepository;

    @Mock
    private TheaterFacilityService theaterFacilityService;

    private FacilityEntryDto dto;
    private Facility facility;

    @BeforeEach
    public void setup()
    {
       dto = FacilityEntryDto.builder()
               .name("Parking")
               .logo("park.png")
               .build();

       facility = Facility.builder()
               .id("1")
               .name("Parking")
               .logo("park.png")
               .isActive(true)
               .deleted(false)
               .build();
    }

    @Test
    @DisplayName("Should save the facility object to database")
    void saveFacility_Success()
    {
        when(facilityRepository.existsByName("Parking")).thenReturn(false);
        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);

        ReturnFacilityDto result = facilityService.addFacilityService(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Parking",result.getName());
        verify(facilityRepository).save(any(Facility.class));
    }

    @Test
    @DisplayName("Should throw exception if facility already present")
    void saveFacility_AlreadyExists()
    {
        when(facilityRepository.existsByName("Parking")).thenReturn(true);
        Assertions.assertThrows(AlreadyPresentException.class,() -> facilityService.addFacilityService(dto));
    }

    @Test
    @DisplayName("Should return list of facilities with size 1")
    void getAllFacilities_Success()
    {
        when(facilityRepository.findAll()).thenReturn(List.of(facility));

        List<ReturnFacilityDto> result = facilityService.getAllFacilityService();
        Assertions.assertEquals(1,result.size());
        Assertions.assertEquals("Parking",result.get(0).getName());
    }

    @Test
    @DisplayName("Should throw exception if facility list is empty")
    void getAllFacilities_NotFound()
    {
        when(facilityRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(NotFoundException.class,() -> facilityService.getAllFacilityService());
    }

    @Test
    @DisplayName("Should delete facility with Id")
    void delete_Success()
    {
        when(facilityRepository.findById(anyString())).thenReturn(Optional.of(facility));
        doNothing().when(facilityRepository).deleteById(anyString());
        when(theaterFacilityService.findByFacility(facility)).thenReturn(List.of());

        facilityService.deleteFacilityService("1");

        verify(facilityRepository,times(1)).deleteById("1");
    }

    @Test
    @DisplayName("Should throw exception if facility not found")
    void delete_NotFound()
    {
        when(facilityRepository.findById("1")).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,() -> facilityService.deleteFacilityService("1"));
    }

    @Test
    @DisplayName("Should update facility with id and data")
    void update_Success()
    {
        FacilityEntryDto newDto = FacilityEntryDto.builder()
                .name("Wheel Chair")
                .logo("wc.svg")
                .build();

        when(facilityRepository.findById("1")).thenReturn(Optional.of(facility));
        when(facilityRepository.findByName("Wheel Chair")).thenReturn(Optional.empty());
        when(facilityRepository.save(any(Facility.class))).thenReturn(facility);

       ReturnFacilityDto updated =  facilityService.updateFacilityService("1",newDto);
       Assertions.assertEquals("Wheel Chair",updated.getName());
       verify(facilityRepository).save(facility);
    }

    @Test
    @DisplayName("Should throw exception if facility already present")
    void update_AlreadyPresent()
    {
        Facility existingFacility = Facility.builder().id("2").name("Parking").build();

        when(facilityRepository.findById("1")).thenReturn(Optional.of(facility));
        when(facilityRepository.findByName("Parking")).thenReturn(Optional.of(existingFacility));

        Assertions.assertThrows(AlreadyPresentException.class, () -> facilityService.updateFacilityService("1",dto));
    }

    @Test
    @DisplayName("Should return facility with id")
    void getById_Success()
    {
        when(facilityRepository.findById("1")).thenReturn(Optional.of(facility));
        ReturnFacilityDto result = facilityService.getFacilityById("1");
        Assertions.assertEquals("Parking",result.getName());
    }

    @Test
    @DisplayName("Should throw exception if facility not found")
    void getById_NotFound()
    {
        when(facilityRepository.findById("1")).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> facilityService.getFacilityById("1"));
    }

    @Test
    @DisplayName("Should set facility to active or inactive")
    void facilityActiveInactive_Success()
    {
        when(facilityRepository.findById("1")).thenReturn(Optional.of(facility));
        when(theaterFacilityService.findByFacility(facility)).thenReturn(Collections.emptyList());

        ReturnFacilityDto result = facilityService.facilityOnAndOffService("1");

        Assertions.assertEquals(false,result.getIsActive());
    }

    @Test
    @DisplayName("Should throw exception if facility not found")
    void facilityActiveInactive_NotFound()
    {
        when(facilityRepository.findById("1")).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,() -> facilityService.facilityOnAndOffService("1"));
    }

    @Test
    @DisplayName("Should return true if facility is present")
    void existsById()
    {
        when(facilityRepository.existsById("1")).thenReturn(true);
        Boolean exists = facilityService.existsById("1");
        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("Should return true if facility exists and is active")
    void existsByIdAndIsActiveTrue()
    {
        when(facilityRepository.existsByIdAndIsActiveTrue("1")).thenReturn(true);
        Boolean exists = facilityService.existsByIdAndIsActiveTrue("1");
        Assertions.assertTrue(exists);
    }


    @Test
    @DisplayName("Should return all the facilities by ids with active = true")
    void findAllByIdAndIsActiveTrue()
    {
            when(facilityRepository.findAllByIdInAndIsActiveTrue(List.of("1"))).thenReturn(List.of(facility));
            List<Facility> facilities = facilityService.findAllByIdInAndIsActiveTrue(List.of("1"));
            Assertions.assertEquals(1,facilities.size());
            Assertions.assertEquals("Parking",facilities.get(0).getName());
    }

}
