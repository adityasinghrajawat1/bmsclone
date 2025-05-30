package com.driver.bookMyShow.controller;

import com.driver.bookMyShow.Controllers.ShowController;
import com.driver.bookMyShow.Dtos.RequestDtos.ShowRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ShowResponseDto;
import com.driver.bookMyShow.Services.ShowService;
import com.driver.bookMyShow.constant.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShowController.class)
public class ShowControllerTest
{
    @MockBean
    private ShowService showService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ShowRequestDto requestDto;
    private ShowResponseDto responseDto;

    @BeforeEach
    void setUp()
    {
        requestDto = new ShowRequestDto();
        requestDto.setShow_start_time(LocalDateTime.of(2025,6,1,10,0,0));
        requestDto.setShow_end_time(LocalDateTime.of(2025,6,1,12,0,0));

        responseDto = ShowResponseDto.builder()
                .show_start_time(LocalDateTime.of(2025,6,1,10,0,0))
                .show_end_time(LocalDateTime.of(2025,6,1,12,0,0))
                .isActive(true)
                .build();
    }

    @Test
    void saveShow() throws Exception
    {
        when(showService.saveShow(any(ShowRequestDto.class),any(String.class),any(String.class))).thenReturn(responseDto);

        mockMvc.perform(post("/show/movie/movieId/screen/screenId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.data.isActive").value(true));

    }

    @Test
    void getShows() throws Exception
    {
        when(showService.getShows("movieId")).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/show/movieId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.data[0].isActive").
                        value(true));
    }

    @Test
    void showActiveInActive() throws Exception
    {
        when(showService.showActiveInactive("showId")).thenReturn(responseDto);

        mockMvc.perform(patch("/show/showId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.multiple").value(Messages.FALSE));
    }

    @Test
    void deleteShow() throws Exception
    {
        String message = Messages.SHOW + Messages.ONE_TAB + Messages.DELETED + Messages.ONE_TAB + Messages.SUCCESSFULLY + Messages.DOT;
        when(showService.deleteShow("showId")).thenReturn(message);

        mockMvc.perform(delete("/show/showId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.data").value(message))
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void  updateShow() throws Exception
    {
        when(showService.updateShow(any(String.class),any(ShowRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/show/showId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.multiple").value(Messages.FALSE));
    }
}
