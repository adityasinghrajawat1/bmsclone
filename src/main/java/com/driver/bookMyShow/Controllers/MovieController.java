package com.driver.bookMyShow.Controllers;

import com.driver.bookMyShow.Dtos.RequestDtos.MovieRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.MovieResponseDto;
import com.driver.bookMyShow.Response.ApiResponse;
import com.driver.bookMyShow.Services.MovieService;
import com.driver.bookMyShow.constant.Messages;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController
{
    @Autowired
    private MovieService movieService;

    @GetMapping("/import")
    public ResponseEntity<ApiResponse<String>> fetchAndSave()
    {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.TRUE)
                .data(movieService.fetchAndSave())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<ApiResponse<MovieResponseDto>> findMovieById(@PathVariable String id)
    {
        ApiResponse<MovieResponseDto> response = ApiResponse.<MovieResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(movieService.findMovieById(id))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieResponseDto>>> findAllMovies()
    {
        ApiResponse<List<MovieResponseDto>> response = ApiResponse.<List<MovieResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.TRUE)
                .data(movieService.findAllMovies())
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponseDto>> movieActiveInactive(@PathVariable String id)
    {
        ApiResponse<MovieResponseDto> response = ApiResponse.<MovieResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(movieService.movieActiveInactive(id))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteById(@PathVariable String id)
    {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(movieService.deleteById(id))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponseDto>> update(@PathVariable String id, @RequestBody MovieRequestDto movieRequestDto)
    {
        ApiResponse<MovieResponseDto> response = ApiResponse.<MovieResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(movieService.update(id,movieRequestDto))
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
