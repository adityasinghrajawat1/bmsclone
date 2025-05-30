package com.driver.bookMyShow.controller;

import com.driver.bookMyShow.Controllers.ScreenController;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnScreenDto;
import com.driver.bookMyShow.Services.ScreenService;
import com.driver.bookMyShow.constant.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScreenController.class)
public class ScreenControllerTest
{
    @MockBean
    private ScreenService screenService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ReturnScreenDto returnScreenDto;

    @BeforeEach
    void setUp()
    {
        returnScreenDto = ReturnScreenDto.builder()
                .id("screenId")
                .resolution("3D")
                .isActive(true)
                .build();
    }

    @Test
    void findByScreenIdAndType() throws Exception
    {
        when(screenService.findByIdAndScreenType("screenId","3D")).thenReturn(returnScreenDto);

        mockMvc.perform(get("/screen/screenId/type/3D"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS));
    }
}
