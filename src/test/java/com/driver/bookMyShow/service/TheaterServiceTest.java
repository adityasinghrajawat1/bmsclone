package com.driver.bookMyShow.service;

import com.driver.bookMyShow.Dtos.RequestDtos.ScreenEntryDto;
import com.driver.bookMyShow.Dtos.RequestDtos.TheaterEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnScreenDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterDetailsDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterFacilityDto;
import com.driver.bookMyShow.Exceptions.AlreadyPresentException;
import com.driver.bookMyShow.Exceptions.NotFoundException;
import com.driver.bookMyShow.Models.Facility;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.Theater;
import com.driver.bookMyShow.Models.TheaterFacility;
import com.driver.bookMyShow.Repositories.TheaterRepository;
import com.driver.bookMyShow.Services.FacilityService;
import com.driver.bookMyShow.Services.ScreenService;
import com.driver.bookMyShow.Services.TheaterFacilityService;
import com.driver.bookMyShow.Services.TheaterService;
import com.driver.bookMyShow.constant.Messages;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TheaterServiceTest
{
    @InjectMocks
    private TheaterService theaterService;

    @Mock
    private TheaterRepository theaterRepository;

    @Mock
    private FacilityService facilityService;

    @Mock
    private ScreenService screenService;

    @Mock
    private TheaterFacilityService theaterFacilityService;

    private TheaterEntryDto dto;
    private Theater theater;

    @BeforeEach
    void init()
    {
        dto = new TheaterEntryDto();
        dto.setName("PVR");
        dto.setAddress("Fun Plaza, Downtown");

        List<String> facilityIds = new ArrayList<>();
        facilityIds.add("id1");
        dto.setFacilityIds(facilityIds);


        theater = Theater.builder()
                .id("101")
                .name("PVR")
                .address("Fun Plaza, Downtown")
                .isActive(true)
                .deleted(false)
                .theaterFacilityList(Collections.emptyList())
                .screenList(Collections.emptyList())
                .build();
    }

    @Test
    void addTheater_Success()
    {
        when(theaterRepository.existsByAddress(dto.getAddress())).thenReturn(false);
        when(facilityService.existsById("id1")).thenReturn(true);
        when(facilityService.existsByIdAndIsActiveTrue("id1")).thenReturn(true);
        when(facilityService.findAllByIdInAndIsActiveTrue(any())).thenReturn(List.of(new Facility()));

        ReturnTheaterDto result = theaterService.addTheaterService(dto);
        Assertions.assertNotNull(result);
    }

    @Test
    void addTheater_AlreadyPresent()
    {
        when(theaterRepository.existsByAddress(dto.getAddress())).thenReturn(true);
        Assertions.assertThrows(AlreadyPresentException.class, () -> theaterService.addTheaterService(dto));
    }

    @Test
    void theaterFacilityActiveInactive_Success()
    {
        String theaterId = "theaterId";
        String facilityId = "facilityId";

        TheaterFacility theaterFacility = new TheaterFacility();
        theaterFacility.setFacility(new Facility());
        theaterFacility.getFacility().setId(facilityId);
        theaterFacility.setIsActive(true);
        theaterFacility.setDeleted(false);

        List<TheaterFacility> theaterFacilities = new ArrayList<>();
        theaterFacilities.add(theaterFacility);

        theater.setTheaterFacilityList(theaterFacilities);

        ReturnTheaterFacilityDto responseDto = ReturnTheaterFacilityDto.builder()
                .facilityId(facilityId)
                .name("Parking")
                .logo("park.png")
                .isActive(true)
                .build();
        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
        when(theaterFacilityService.addTheaterFacility(any())).thenReturn(responseDto);

        ReturnTheaterFacilityDto result =  theaterService.theaterFacilityOnAndOffService(theaterId,facilityId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(facilityId,result.getFacilityId());
        Assertions.assertTrue(result.getIsActive());
    }

    @Test
    void theaterFacilityActiveInactive_TheaterNotFound()
    {
        String facilityId = "facilityId";
        String theaterId = "theaterId";

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,() -> theaterService.theaterFacilityOnAndOffService(theaterId,facilityId));
    }

    @Test
    void theaterFacilityActiveInactive_FacilityNotFound()
    {
        String theaterId = "theaterId";
        String facility_Id = "facility_Id";

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
        theater.setTheaterFacilityList(new ArrayList<>()); // no facilities added

        Assertions.assertThrows(NotFoundException.class, () -> theaterService.theaterFacilityOnAndOffService(theaterId,facility_Id));
    }

    @Test
    void theaterFacilityActiveInactive_FacilityNotAssociated()
    {
        String theaterId = "theaterId";
        String facilityId = "facilityId";

        TheaterFacility theaterFacility = new TheaterFacility();
        theaterFacility.setFacility(new Facility());
        theaterFacility.getFacility().setId("differentFaiclityId");

        theater.setTheaterFacilityList(List.of(theaterFacility));

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));

        Assertions.assertThrows(NotFoundException.class, () -> theaterService.theaterFacilityOnAndOffService(theaterId,facilityId));
    }

    @Test
    void getAllTheaters_Success()
    {
        when(theaterRepository.findAll()).thenReturn(List.of(theater));

        List<ReturnTheaterDetailsDto> result = theaterService.getAllTheaterDetailsService();

        Assertions.assertEquals(1,result.size());
        Assertions.assertNotNull(result);
        Assertions.assertEquals("101",result.get(0).getTheaterId());
        Assertions.assertEquals("PVR",result.get(0).getName());
    }

    @Test
    void getAllTheaters_NotFound()
    {
        when(theaterRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(NotFoundException.class, () -> theaterService.getAllTheaterDetailsService());
    }

    @Test
    void getTheaterById_Success()
    {
        when(theaterRepository.findById(theater.getId())).thenReturn(Optional.of(theater));
        ReturnTheaterDto result = theaterService.getTheaterDetailsByIdService(theater.getId());

        Assertions.assertEquals(theater.getId(),result.getTheaterId());
        Assertions.assertNotNull(result);
    }

    @Test
    void getTheaterById_NotFound()
    {
        when(theaterRepository.findById(theater.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> theaterService.getTheaterDetailsByIdService(theater.getId()));
    }

    @Test
    void updateTheater_Success()
    {
        String theaterId = "theaterId";
        TheaterEntryDto updateDto = new TheaterEntryDto();
        updateDto.setName("updated theater");
        updateDto.setAddress("Street 23, City Gate");

        List<String> facilityIds = new ArrayList<>();
        facilityIds.add("facilityId");
        updateDto.setFacilityIds(facilityIds);

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
        when(theaterRepository.findByAddress(updateDto.getAddress())).thenReturn(Optional.empty());
        when(facilityService.existsById("facilityId")).thenReturn(true);
        when(facilityService.existsByIdAndIsActiveTrue("facilityId")).thenReturn(true);
        when(facilityService.findAllByIdInAndIsActiveTrue(any())).thenReturn(List.of(new Facility()));

        ReturnTheaterDto result = theaterService.updateTheaterDetailsByIdService(theaterId,updateDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("updated theater", result.getName());
        verify(theaterRepository, times(1)).save(theater);
    }

    @Test
    void updateTheater_TheaterNotFound()
    {
        String theaterId = "theaterId";
        TheaterEntryDto updateDto = new TheaterEntryDto();
        updateDto.setName("updated theater");
        updateDto.setAddress("Street 23, City Gate");

        List<String> facilityIds = new ArrayList<>();
        facilityIds.add("facilityId");
        updateDto.setFacilityIds(facilityIds);

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> theaterService.updateTheaterDetailsByIdService(theaterId,updateDto));
    }

    @Test
    void updateTheater_TheaterAddressAlreadyExists()
    {
        String theaterId = "theaterId";
        TheaterEntryDto updateDto = new TheaterEntryDto();
        updateDto.setName("updated theater");
        updateDto.setAddress("Street 23, City Gate");

        List<String> facilityIds = new ArrayList<>();
        facilityIds.add("facilityId");
        updateDto.setFacilityIds(facilityIds);

        Theater dummy = new Theater();
        dummy.setId("1111111");

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
        when(theaterRepository.findByAddress(updateDto.getAddress())).thenReturn(Optional.of(dummy));
        Assertions.assertThrows(AlreadyPresentException.class, () -> theaterService.updateTheaterDetailsByIdService(theaterId, updateDto));
    }

    @Test
    void updateTheater_FacilityNotFound()
    {
        String theaterId = "theaterId";
        TheaterEntryDto updateDto = new TheaterEntryDto();
        updateDto.setName("updated theater");
        updateDto.setAddress("Street 23, City Gate");

        List<String> facilityIds = new ArrayList<>();
        facilityIds.add("facilityId");
        updateDto.setFacilityIds(facilityIds);

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
        when(theaterRepository.findByAddress(updateDto.getAddress())).thenReturn(Optional.empty());
        when(facilityService.existsById("facilityId")).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,() -> theaterService.updateTheaterDetailsByIdService(theaterId, updateDto));
    }

    @Test
    void updateTheater_FacilityNotActive()
    {
        String theaterId = "theaterId";
        TheaterEntryDto updateDto = new TheaterEntryDto();
        updateDto.setName("updated theater");
        updateDto.setAddress("Street 23, City Gate");

        List<String> facilityIds = new ArrayList<>();
        facilityIds.add("facilityId");
        updateDto.setFacilityIds(facilityIds);

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
        when(theaterRepository.findByAddress(updateDto.getAddress())).thenReturn(Optional.empty());
        when(facilityService.existsById("facilityId")).thenReturn(true);
        when(facilityService.existsByIdAndIsActiveTrue("facilityId")).thenReturn(false);

        Assertions.assertThrows(NotFoundException.class, () -> theaterService.updateTheaterDetailsByIdService(theaterId, updateDto));
    }

    @Test
    void delete_success()
    {
        when(theaterRepository.findById(theater.getId())).thenReturn(Optional.of(theater));

        String result = theaterService.deleteTheaterService(theater.getId());
        Assertions.assertEquals("Theater Deleted.",result);
        verify(theaterRepository,times(1)).deleteById(theater.getId());
    }

    @Test
    void delete_NotFound()
    {
        when(theaterRepository.findById(theater.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> theaterService.deleteTheaterService(theater.getId()));
    }

    @Test
    void saveTheaterScreen_Success()
    {
        String theaterId = "theaterId";
        ScreenEntryDto screenEntryDto = new ScreenEntryDto();
        screenEntryDto.setResolution("2D");

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
        when(screenService.insertTheaterScreen(any())).thenReturn(new Screen());

        ReturnScreenDto result = theaterService.saveTheaterScreen(screenEntryDto,theaterId);
        Assertions.assertNotNull(result);
        verify(screenService, times(1)).insertTheaterScreen(any());
    }

    @Test
    void saveTheaterScreen_TheaterNotFound()
    {
        String theaterId = "theaterId";
        ScreenEntryDto screenEntryDto = new ScreenEntryDto();
        screenEntryDto.setResolution("2D");

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,() -> theaterService.saveTheaterScreen(screenEntryDto,theaterId));
    }

    @Test
    void getTheaterScreen_Success()
    {
        String theaterId = "theater123";
        Theater theater = new Theater();
        theater.setId(theaterId);

        Screen screen = new Screen();
        screen.setId("screen1");
        screen.setResolution("3D");
        screen.setTheater(theater);

        theater.setScreenList(List.of(screen));

        ReturnScreenDto dto = new ReturnScreenDto();
        dto.setId("screen1");


        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));

        List<ReturnScreenDto> result = theaterService.getTheaterScreens(theaterId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("screen1",result.get(0).getId());
    }

    @Test
    void getTheaterScreens_TheaterNotFound()
    {
        String theaterId = "1234";
        when(theaterRepository.findById(theaterId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,() -> theaterService.getTheaterScreens(theaterId));
    }

    @Test
    void getTheaterScreens_NoScreens()
    {
        when(theaterRepository.findById(theater.getId())).thenReturn(Optional.of(theater));

        Assertions.assertThrows(NotFoundException.class, () -> theaterService.getTheaterScreens(theater.getId()));
    }

    @Test
    void deleteTheaterScreens_Success()
    {
        String theaterId = "theaterId";
        String screenId = "screenId";

        Screen screen = new Screen();
        screen.setId(screenId);
        theater.setScreenList(List.of(screen));

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));

        String expectedMessage = Messages.SCREEN + Messages.ONE_TAB + Messages.DELETED + Messages.DOT;
        String result = theaterService.deleteTheaterScreen(theaterId,screenId);

        verify(screenService).existsByIdAndTheater(screenId,theater);
        verify(screenService).deleteById(screenId);
        Assertions.assertEquals(result,expectedMessage);
    }

    @Test
    void deleteTheaterScreens_TheaterNotFound()
    {
        String theaterId = "theaterId";
        String screenId = "screenId";

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> theaterService.deleteTheaterScreen(theaterId,screenId));
    }

    @Test
    void deleteTheaterScreens_ScreensNotFound()
    {
        String theaterId = "theaterId";
        String screenId = "screenId";
        theater.setScreenList(new ArrayList<>());

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
        doThrow(NotFoundException.getExceptionWithDesc(Messages.SCREEN))
                .when(screenService).existsByIdAndTheater(screenId,theater);
        Assertions.assertThrows(NotFoundException.class, () -> theaterService.deleteTheaterScreen(theaterId,screenId));
    }

    @Test
    void theaterScreenActiveInactive_Success()
    {
        String theaterId = "theaterId";
        String screenId = "screenId";

        Screen screen = new Screen();
        screen.setId(screenId);
        screen.setTheater(theater);
        screen.setIsActive(true);
        theater.setScreenList(List.of(screen));

        ReturnScreenDto returnScreenDto = new ReturnScreenDto();
        returnScreenDto.setId(screenId);
        returnScreenDto.setIsActive(false);

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
        when(screenService.screenActiveInactive(screen)).thenReturn(returnScreenDto);

        ReturnScreenDto result = theaterService.theaterScreenActiveInactive(theaterId,screenId);
        Assertions.assertEquals(returnScreenDto,result);
        verify(screenService).screenActiveInactive(screen);
    }

    @Test
    void theaterScreenActiveInactive_TheaterNotFound()
    {
        String theaterId = "theaterId";
        String screenID = "screenId";

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> theaterService.theaterScreenActiveInactive(theaterId,screenID));
    }

    @Test
    void theaterScreenActiveInactive_ScreensNotFound()
    {
        String theaterId = "theaterId";
        String screenID = "screenId";
        theater.setScreenList(new ArrayList<>());

        when(theaterRepository.findById(theaterId)).thenReturn(Optional.of(theater));
        Assertions.assertThrows(NotFoundException.class, () -> theaterService.theaterScreenActiveInactive(theaterId,screenID));
    }
}