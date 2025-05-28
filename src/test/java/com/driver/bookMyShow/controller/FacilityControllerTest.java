package com.driver.bookMyShow.controller;

import com.driver.bookMyShow.Dtos.RequestDtos.FacilityEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnFacilityDto;
import com.driver.bookMyShow.Services.FacilityService;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacilityController.class)
public class FacilityControllerTest {

    //@MockBean = if Bean is inside spring container or spring context
    //@Mock = if Bean is not inside spring container or spring context
    @MockBean
    private FacilityService facilityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ReturnFacilityDto returnDto;

    @BeforeEach
    void setUp()
    {
         returnDto = ReturnFacilityDto.builder()
                .facilityId("1")
                .name("Parking")
                .logo("park.jpeg")
                .isActive(true)
                .build();
    }

    @Test
    void save()throws Exception
    {
        FacilityEntryDto inputDto = FacilityEntryDto.builder().name("Parking").logo("park.jpeg").build();
        when(facilityService.addFacilityService(any(FacilityEntryDto.class))).thenReturn(returnDto);

        mockMvc.perform(post("/facility")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.multiple").value(Messages.FALSE))
                .andExpect(jsonPath("$.data.facilityId").value("1"))
                .andExpect(jsonPath("$.data.name").value("Parking"))
                .andExpect(jsonPath("$.data.logo").value("park.jpeg"))
                .andExpect(jsonPath("$.data.isActive").value(true));
    }

    @Test
    void getAll()throws Exception
    {
        when(facilityService.getAllFacilityService()).thenReturn(List.of(returnDto));
        mockMvc.perform(get("/facility"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.multiple").value(Messages.TRUE))
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.data[0].name").value("Parking"))
                .andExpect(jsonPath("$.data[0].facilityId").value("1"))
                .andExpect(jsonPath("$.data",hasSize(1)));
    }

    @Test
    void delete()throws Exception
    {
        String facilityId ="1";
        when(facilityService.deleteFacilityService(facilityId))
                .thenReturn("Facility deleted successfully!!");

        mockMvc.perform(MockMvcRequestBuilders.delete("/facility/{facilityId}",facilityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.multiple").value(Messages.FALSE))
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.data").value("Facility deleted successfully!!"));
    }

    @Test
    void update() throws Exception
    {
        FacilityEntryDto inputDto = FacilityEntryDto.builder()
                .name("Updated Facility")
                .logo("updated_logo")
                .build();

        ReturnFacilityDto updatedDto = ReturnFacilityDto.builder()
                .facilityId("2")
                .name("Updated Facility")
                .logo("updated_logo")
                .isActive(true)
                .build();

        when(facilityService.updateFacilityService(anyString(), any(FacilityEntryDto.class)))
                .thenReturn(updatedDto);

        mockMvc.perform(put("/facility/{facilityId}", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Facility"));
    }

    @Test
    void getById() throws Exception
    {
        when(facilityService.getFacilityById(anyString()))
                .thenReturn(returnDto);

        mockMvc.perform(get("/facility/{facilityId}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.facilityId").value("1"));
    }

    @Test
    void facilityActiveInactive() throws Exception
    {
        ReturnFacilityDto toggledDto = ReturnFacilityDto.builder()
                .facilityId("abc123")
                .name("3D Glasses")
                .logo("logo_url")
                .isActive(false)
                .build();

        when(facilityService.facilityOnAndOffService(anyString()))
                .thenReturn(toggledDto);

        mockMvc.perform(patch("/facility/{facilityId}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.isActive").value(false));
    }

}
