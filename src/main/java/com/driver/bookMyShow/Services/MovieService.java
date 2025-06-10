package com.driver.bookMyShow.Services;

import com.driver.bookMyShow.Dtos.RequestDtos.MovieRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.MovieResponseDto;
import com.driver.bookMyShow.Models.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    boolean existsById(String id);

    Optional<Movie> findById(String id);

    //////////////////////////////////////////////////////////////////////////////////////////
    String fetchAndSave();

    MovieResponseDto findMovieById(String id);

    List<MovieResponseDto> findAllMovies();

    MovieResponseDto movieActiveInactive(String id);

    String deleteById(String id);

    MovieResponseDto update(String id, MovieRequestDto movieRequestDto);
}
