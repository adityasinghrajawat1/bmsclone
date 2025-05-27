package com.driver.bookMyShow.Services;

import com.driver.bookMyShow.Models.Movie;

import java.util.Optional;

public interface MovieService {

    boolean existsById(String id);

    Optional<Movie> findById(String id);
}
