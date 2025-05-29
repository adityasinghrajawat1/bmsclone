package com.driver.bookMyShow.controller;

import com.driver.bookMyShow.Controllers.TheaterController;
import com.driver.bookMyShow.Dtos.RequestDtos.ScreenEntryDto;
import com.driver.bookMyShow.Dtos.RequestDtos.TheaterEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnScreenDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterDetailsDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterFacilityDto;
import com.driver.bookMyShow.Services.TheaterService;
import com.driver.bookMyShow.constant.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TheaterController.class)
public class TheaterControllerTest
{
    @MockBean
    private TheaterService theaterService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TheaterEntryDto theaterEntryDto;
    private ReturnTheaterDto returnTheaterDto;

    @BeforeEach
    void setUp()
    {
        theaterEntryDto = new TheaterEntryDto();
        theaterEntryDto.setName("INOX");
        theaterEntryDto.setAddress("Fun Plaza, High Street");
        theaterEntryDto.setFacilityIds(List.of("facilityIds"));

        returnTheaterDto = ReturnTheaterDto.builder()
                .theaterId("theaterId")
                .name("INOX")
                .address("Fun Plaza, High Street")
                .facilities(Collections.emptyList())
                .build();
    }

    @Test
    void saveTheater() throws Exception
    {
        when(theaterService.addTheaterService(any(TheaterEntryDto.class))).thenReturn(returnTheaterDto);

        mockMvc.perform(post("/theater")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theaterEntryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.multiple").value(Messages.FALSE))
                .andExpect(jsonPath("$.data.theaterId").value("theaterId"))
                .andExpect(jsonPath("$.data.name").value("INOX"));
    }

    @Test
    void theaterFacilityActiveInactive() throws Exception
    {
        ReturnTheaterFacilityDto returnTheaterFacilityDto = new ReturnTheaterFacilityDto();
        when(theaterService.theaterFacilityOnAndOffService("1","101"))
                .thenReturn(returnTheaterFacilityDto);
        mockMvc.perform(patch("/theater/1/facilities/101/toggle"))
                .andExpect(status().isOk());
    }

    @Test
    void getTheaters() throws Exception
    {
        ReturnTheaterDetailsDto theaterDetailsDto = new ReturnTheaterDetailsDto();

        when(theaterService.getAllTheaterDetailsService()).thenReturn(List.of(theaterDetailsDto));

        mockMvc.perform(get("/theater"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.multiple").value(Messages.TRUE));
    }

    @Test
    void getById() throws Exception
    {
        when(theaterService.getTheaterDetailsByIdService("theaterId")).thenReturn(returnTheaterDto);

        mockMvc.perform(get("/theater/theaterId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.theaterId").value("theaterId"));
    }

    @Test
    void update() throws Exception
    {
        when(theaterService.updateTheaterDetailsByIdService(anyString(),any())).thenReturn(returnTheaterDto);

        mockMvc.perform(put("/theater/theaterId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theaterEntryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("INOX"));
    }

    @Test
    void delete() throws Exception
    {
        when(theaterService.deleteTheaterService("theaterId")).thenReturn("Theater Deleted.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/theater/theaterId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Theater Deleted."));
    }

    @Test
    void saveTheaterScreen() throws Exception
    {
        ScreenEntryDto screenEntryDto = new ScreenEntryDto();
        ReturnScreenDto returnScreenDto = new ReturnScreenDto();

        when(theaterService.saveTheaterScreen(any(),anyString())).thenReturn(returnScreenDto);

        mockMvc.perform(post("/theater/theaterId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(screenEntryDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getTheaterScreens() throws Exception
    {
        when(theaterService.getTheaterScreens(anyString())).thenReturn(List.of(new ReturnScreenDto()));

        mockMvc.perform(get("/theater/theaterId/screens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.multiple").value(Messages.TRUE));
    }

    @Test
    void deleteTheaterScreen()throws Exception
    {
        when(theaterService.deleteTheaterScreen(anyString(),anyString())).thenReturn("Screen Deleted.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/theater/theaterId/screen/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Screen Deleted."));
    }

    @Test
    void theaterScreenActiveInactive() throws Exception
    {
        when(theaterService.theaterScreenActiveInactive(anyString(),anyString())).thenReturn(new ReturnScreenDto());

        mockMvc.perform(patch("/theater/theaterId/screen/101"))
                .andExpect(status().isOk());
    }
}