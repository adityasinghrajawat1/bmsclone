package com.driver.bookMyShow.Transformers;

import com.driver.bookMyShow.Dtos.ResponseDtos.MovieResponseDto;
import com.driver.bookMyShow.Models.Movie;

public class MovieTransformer {
    public static MovieResponseDto MovieToMovieResponseDto(Movie movie) {
        MovieResponseDto movieResponseDto = MovieResponseDto.builder()
                .id(movie.getId())
                .name(movie.getMovieName())
                .build();
        return movieResponseDto;
    }
}
