package com.driver.bookMyShow.ServiceImpl;

import com.driver.bookMyShow.Models.Movie;
import com.driver.bookMyShow.Repositories.MovieRepository;
import com.driver.bookMyShow.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public boolean existsById(String id) {
        return movieRepository.existsById(id);
    }

    @Override
    public Optional<Movie> findById(String id) {
        return movieRepository.findById(id);
    }
}
