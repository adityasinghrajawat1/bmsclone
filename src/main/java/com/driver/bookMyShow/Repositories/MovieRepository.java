package com.driver.bookMyShow.Repositories;

import com.driver.bookMyShow.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {

    boolean existsById(String id);
}
