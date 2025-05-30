package com.driver.bookMyShow.service;

import com.driver.bookMyShow.Dtos.RequestDtos.ShowRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ShowResponseDto;
import com.driver.bookMyShow.Exceptions.AlreadyPresentException;
import com.driver.bookMyShow.Exceptions.NotFoundException;
import com.driver.bookMyShow.Models.Movie;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.Show;
import com.driver.bookMyShow.Repositories.ShowRepository;
import com.driver.bookMyShow.ServiceImpl.ShowServiceImpl;
import com.driver.bookMyShow.Services.MovieService;
import com.driver.bookMyShow.Services.ScreenService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShowServiceImplTest
{
    @InjectMocks
    private ShowServiceImpl showService;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private ScreenService screenService;

    @Mock
    private MovieService movieService;

    private ShowRequestDto requestDto;
    private Show show;
    private Screen screen;
    private Movie movie;

    @BeforeEach
    void setUp()
    {
        screen = Screen.builder()
                .id("screenId")
                .resolution("3D")
                .isActive(true)
                .deleted(false)
                .build();

        movie = Movie.builder()
                .id("movieId")
                .movieName("My Movie")
                .duration(175)
                .isActive(true)
                .deleted(false)
                .build();

        requestDto = new ShowRequestDto();
        requestDto.setShow_start_time(LocalDateTime.of(2025,6,1,10,0));
        requestDto.setShow_end_time(LocalDateTime.of(2025,6,1,12,0));

        show = Show.builder()
                .id("showId")
                .screen(screen)
                .movie(movie)
                .isActive(true)
                .deleted(false)
                .startTime(requestDto.getShow_start_time())
                .endTime(requestDto.getShow_end_time())
                .status("Running")
                .build();
    }

    @Test
    void saveShow_success()
    {
        when(screenService.findById("screenId")).thenReturn(Optional.of(screen));
        when(showRepository.existsByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(any(),any(),any())).thenReturn(false);
        when(showRepository.save(any())).thenReturn(show);

        ShowResponseDto result = showService.saveShow(requestDto,"movieId","screenId");

        Assertions.assertThat(result).isNotNull();
        verify(showRepository).save(any());
    }

    @Test
    void saveShow_ScreenNotFound()
    {
        when(screenService.findById("screenId")).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> showService.saveShow(requestDto,"movieId","screenId"));
    }

    @Test
    void saveShow_AlreadyPresent()
    {
        when(screenService.findById("screenId")).thenReturn(Optional.of(screen));
        when(showRepository.existsByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(any(),any(),any())).thenReturn(true);

        org.junit.jupiter.api.Assertions.assertThrows(AlreadyPresentException.class,
                () -> showService.saveShow(requestDto,"movieId","screenId"));
    }

    @Test
    void getShows_success()
    {
        show.setMovie(movie);
        movie.setShows(List.of(show));

        when(movieService.findById("movieId")).thenReturn(Optional.of(movie));

        List<ShowResponseDto> result = showService.getShows("movieId");
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result).hasSize(1);
    }

    @Test
    void getShows_MovieNotFound()
    {
        show.setMovie(movie);
        movie.setShows(List.of(show));

        when(movieService.findById("movieId")).thenReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> showService.getShows("movieId"));
    }

    @Test
    void showActiveInactive_success()
    {
        when(showRepository.findById("showId")).thenReturn(Optional.of(show));
        when(showRepository.save(show)).thenReturn(show);

        ShowResponseDto result = showService.showActiveInactive("showId");
        Assertions.assertThat(result.getIsActive()).isFalse();
    }

    @Test
    void showActiveInactive_NotFound()
    {
        when(showRepository.findById("showId")).thenReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> showService.showActiveInactive("showId"));
    }

    @Test
    void deleteShow_success()
    {
        when(showRepository.findById("showId")).thenReturn(Optional.of(show));
        showService.deleteShow("showId");
        verify(showRepository).deleteById("showId");
    }

    @Test
    void deleteShow_NotFound()
    {
        when(showRepository.findById("showId")).thenReturn(Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> showService.deleteShow("showId"));
    }

    @Test
    void updateShow_success()
    {
        when(showRepository.findById("showId")).thenReturn(Optional.of(show));
        when(showRepository.findByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(any(),any(),any())).thenReturn(List.of());

        ShowResponseDto result = showService.updateShow("showId",requestDto);
        Assertions.assertThat(result).isNotNull();
    }
    @Test
    void updateShow_NotFound()
    {
        when(showRepository.findById("showId")).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class,
                () -> showService.updateShow("showId",requestDto));
    }

    @Test
    void updateShow_AlreadyPresent()
    {
        Show conflicting = new Show();
        conflicting.setId("conflict");
        conflicting.setScreen(screen);

        when(showRepository.findById("showId")).thenReturn(Optional.of(show));
        when(showRepository.findByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(any(),any(),any()))
                .thenReturn(List.of(conflicting));

        org.junit.jupiter.api.Assertions.assertThrows(AlreadyPresentException.class,
                () -> showService.updateShow("showId",requestDto));
    }
}
