package com.driver.bookMyShow.repository;

import com.driver.bookMyShow.Models.Movie;
import com.driver.bookMyShow.Repositories.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

@DataJpaTest
public class MovieRepositoryTest
{
    @Autowired
    private MovieRepository movieRepository;

    private Movie movie;

    @BeforeEach
    void setUp()
    {
        movie = Movie.builder()
                .id("movieId")
                .title("Inception")
                .originalTitle("Inception")
                .originalLanguage("en")
                .overview("A mind-bending thriller")
                .posterPath("/poster.jpg")
                .backdropPath("/backdrop.jpg")
                .releaseDate("2010-07-16")
                .popularity(9.8)
                .voteAverage(8.8)
                .voteCount(10000)
                .video(false)
                .adult(false)
                .genreIds(Arrays.asList(1, 2, 3))
                .isActive(true)
                .deleted(false)
                .build();
    }

    @Test
    void existsById()
    {
        movieRepository.save(movie);
        boolean exists = movieRepository.existsById(movie.getId());
        Assertions.assertTrue(exists);
    }
}
