package com.driver.bookMyShow.repository;

import com.driver.bookMyShow.Models.Movie;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Models.Show;
import com.driver.bookMyShow.Repositories.MovieRepository;
import com.driver.bookMyShow.Repositories.ScreenRepository;
import com.driver.bookMyShow.Repositories.ShowRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class ShowRepositoryTest
{
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private MovieRepository movieRepository;

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
            screenRepository.save(screen);

            movie = Movie.builder()
                    .id("movieId")
                    .movieName("My Movie")
                    .duration(175)
                    .isActive(true)
                    .deleted(false)
                    .build();
            movieRepository.save(movie);

        Show show = Show.builder()
                .id("showId")
                .screen(screen)
                .movie(movie)
                .startTime(LocalDateTime.of(2025,5,30,10,0))
                .endTime(LocalDateTime.of(2025,5,30,12,0))
                .isActive(true)
                .deleted(false)
                .status("Running")
                .build();

        showRepository.save(show);
    }

    @Test
    void findByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan()
    {
         List<Show> shows =  showRepository.findByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(
                    screen.getId(),
                    LocalDateTime.of(2025,5,30,11,0),
                    LocalDateTime.of(2025,5,30,9,30)
            );
        Assertions.assertFalse(shows.isEmpty());
        Assertions.assertEquals(screen.getId(), shows.get(0).getScreen().getId());
    }

    @Test
    void existsByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan()
    {
         boolean exists = showRepository.existsByScreenIdAndStartTimeLessThanAndEndTimeGreaterThan(
                 screen.getId(),
                 LocalDateTime.of(2025,5,30,11,0),
                 LocalDateTime.of(2025,5,30,9,30)
         );
        Assertions.assertTrue(exists);
    }
}
