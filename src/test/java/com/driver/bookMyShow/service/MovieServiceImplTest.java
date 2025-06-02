package com.driver.bookMyShow.service;

import com.driver.bookMyShow.Dtos.RequestDtos.MovieRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.MovieAPIResponseDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.MovieResponseDto;
import com.driver.bookMyShow.Exceptions.NotFoundException;
import com.driver.bookMyShow.Models.Movie;
import com.driver.bookMyShow.Repositories.MovieRepository;
import com.driver.bookMyShow.ServiceImpl.MovieServiceImpl;
import com.driver.bookMyShow.config.ApiConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest
{
    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApiConfig apiConfig;

    @BeforeEach
    void cleanUp()
    {
        movieRepository.deleteAll();
    }

    @Test
    void existsById()
    {
        when(movieRepository.existsById("1")).thenReturn(true);

        assertTrue(movieRepository.existsById("1"));
    }

    @Test
    void findById()
    {
        Movie movie = new Movie();
        movie.setId("movieId");
        movie.setIsActive(true);

        when(movieRepository.findById("movieId")).thenReturn(Optional.of(movie));

        Optional<Movie> optionalMovie = movieRepository.findById("movieId");
        assertTrue(optionalMovie.isPresent());
        assertEquals("movieId",optionalMovie.get().getId());
    }

    @Test
    void fetchAndSave_Success()
    {
        MovieResponseDto movieResponseDto = new MovieResponseDto();
        movieResponseDto.setId("1");
        movieResponseDto.setActive(true);

        MovieAPIResponseDto apiResponse = new MovieAPIResponseDto();
        apiResponse.setResults(List.of(movieResponseDto));

        when(apiConfig.getBaseurlfordata()).thenReturn("http://java.com");
        when(apiConfig.getApikey()).thenReturn("1234");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET),any(),eq(MovieAPIResponseDto.class)))
                .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

        String result = movieService.fetchAndSave();
        assertEquals("Movies fetched and saved successfully.", result);
    }

    @Test
    void fetchAndSave_Failure()
    {
        when(apiConfig.getBaseurlfordata()).thenReturn("http://java.com");
        when(apiConfig.getApikey()).thenReturn("1234");
        when(restTemplate.exchange(anyString(),eq(HttpMethod.GET),any(),eq(MovieAPIResponseDto.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));

        String result = movieService.fetchAndSave();
        assertEquals("Failed to fetch movies.", result);
    }

    @Test
    void findMovieById_Success()
    {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setIsActive(true);
        when(movieRepository.findById("1")).thenReturn(Optional.of(movie));
        Optional<Movie> result = movieService.findById(movie.getId());

        assertTrue(result.isPresent());
        assertEquals(result.get().getId(),"1");
    }

    @Test
    void findMovieById_NotFound()
    {
        when(movieRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> movieService.findMovieById("1"));
    }

    @Test
    void findAllMovies_Success()
    {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setIsActive(true);

        when(movieRepository.findAll()).thenReturn(List.of(movie));
        List<MovieResponseDto> result = movieService.findAllMovies();

        Assertions.assertEquals(result.size(),1);
        Assertions.assertEquals(result.get(0).getId(),"1");
    }

    @Test
    void findAllMovies_NotFound()
    {
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> movieService.findAllMovies());
    }

    @Test
    void movieActiveInactive_Success()
    {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setIsActive(true);

        MovieResponseDto movieResponseDto = new MovieResponseDto();
        movieResponseDto.setId("1");
        movieResponseDto.setActive(true);

        when(movieRepository.findById("1")).thenReturn(Optional.of(movie));
        MovieResponseDto result = movieService.movieActiveInactive("1");

        Assertions.assertEquals(false,result.isActive());
    }

    @Test
    void movieActiveInactive_NotFound()
    {
        when(movieRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,() -> movieService.movieActiveInactive("1"));
    }

    @Test
    void deleteById_Success()
    {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setIsActive(true);

        when(movieRepository.existsById("1")).thenReturn(true);
        doNothing().when(movieRepository).deleteById(movie.getId());

        movieService.deleteById(movie.getId());
        verify(movieRepository,times(1)).deleteById("1");
    }

    @Test
    void deleteById_NotFound()
    {
        when(movieRepository.existsById("1")).thenReturn(false);
        assertThrows(NotFoundException.class, () -> movieService.deleteById("1"));
    }

    @Test
    void update_Success()
    {
        String movie_id = "1";
        MovieRequestDto requestDto = new MovieRequestDto();
        requestDto.setTitle("updated movie title");
        requestDto.setOverview("updated overview");

        Movie existingMovie = new Movie();
        existingMovie.setId(movie_id);

        when(movieRepository.findById(movie_id)).thenReturn(Optional.of(existingMovie));

        MovieResponseDto updatedMovie = movieService.update(movie_id,requestDto);

        assertEquals("updated movie title",updatedMovie.getTitle());
    }

    @Test
    void update_NotFound()
    {
        String movie_Id = "1";
        MovieRequestDto requestDto = new MovieRequestDto();

        when(movieRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,() -> movieService.update(movie_Id,requestDto));
    }
}
