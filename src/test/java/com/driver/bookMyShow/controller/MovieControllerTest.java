package com.driver.bookMyShow.controller;

import com.driver.bookMyShow.Controllers.MovieController;
import com.driver.bookMyShow.Dtos.RequestDtos.MovieRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.MovieResponseDto;
import com.driver.bookMyShow.Services.MovieService;
import com.driver.bookMyShow.constant.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerTest
{
    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MovieResponseDto responseDto;

    @BeforeEach
    void setUp()
    {
        responseDto = new MovieResponseDto();
        responseDto.setAdult(false);
        responseDto.setBackdropPath("backdrop.jpg");
        responseDto.setGenreIds(List.of(1,2));
        responseDto.setId("1");
        responseDto.setOriginalLanguage("hi");
        responseDto.setOverview("overview of movie");
        responseDto.setPopularity(100.12);
        responseDto.setPosterPath("posterpath.jpg");
        responseDto.setReleaseDate("2025-04-10");
        responseDto.setTitle("my movie");
        responseDto.setVideo(false);
        responseDto.setVoteAverage(19.78);
        responseDto.setVoteCount(2345);
        responseDto.setActive(true);
    }

    @Test
    void fetchAndSave() throws Exception
    {
        String expectedMessage = "Movies fetched and saved successfully.";
        when(movieService.fetchAndSave()).thenReturn(expectedMessage);

        mockMvc.perform(get("/movie/import"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.multiple").value(Messages.TRUE))
                .andExpect(jsonPath("$.data").value(expectedMessage));
    }

    @Test
    void findByMovieId() throws Exception
    {
        when(movieService.findMovieById("1")).thenReturn(responseDto);

        mockMvc.perform(get("/movie/v1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.multiple").value(Messages.FALSE))
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.title").value("my movie"));
    }

    @Test
    void findAllMovies() throws Exception
    {
        when(movieService.findAllMovies()).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.multiple").value(Messages.TRUE))
                .andExpect(jsonPath("$.data[0].id").value("1"))
                .andExpect(jsonPath("$.data[0].title").value("my movie"));
    }

    @Test
    void movieActiveInactive() throws Exception
    {
       when(movieService.movieActiveInactive("1")).thenReturn(responseDto);

       mockMvc.perform(patch("/movie/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
               .andExpect(jsonPath("$.multiple").value(Messages.FALSE))
               .andExpect(jsonPath("$.data.title").value("my movie"))
               .andExpect(jsonPath("$.data.backdrop_path").value("backdrop.jpg"));
    }

    @Test
    void deleteById() throws Exception
    {
        String msg = "Movie Deleted successfully";
        when(movieService.deleteById("1")).thenReturn(msg);

        mockMvc.perform(delete("/movie/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.multiple").value(Messages.FALSE))
                .andExpect(jsonPath("$.data").value(msg));
    }

    @Test
    void update() throws Exception
    {
        MovieRequestDto requestDto = new MovieRequestDto();
        requestDto.setTitle("Updated Movie");
        requestDto.setOverview("Updated Overview");

        MovieResponseDto updatedMovie = new MovieResponseDto();
        updatedMovie.setId("23");
        updatedMovie.setTitle("Updated Movie");

        when(movieService.update(eq("23"),any(MovieRequestDto.class))).thenReturn(updatedMovie);

        mockMvc.perform(put("/movie/23")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value(Messages.SUCCESS))
                .andExpect(jsonPath("$.data.id").value("23"))
                .andExpect(jsonPath("$.data.title").value("Updated Movie"));
    }
}
