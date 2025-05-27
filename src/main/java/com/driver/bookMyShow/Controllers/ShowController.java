package com.driver.bookMyShow.Controllers;

import com.driver.bookMyShow.Dtos.RequestDtos.ShowRequestDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ShowResponseDto;
import com.driver.bookMyShow.Response.ApiResponse;
import com.driver.bookMyShow.Services.ShowService;
import com.driver.bookMyShow.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @PostMapping("/movie/{movieId}/screen/{screenId}")
    public ResponseEntity<ApiResponse<ShowResponseDto>> saveShow(@RequestBody ShowRequestDto showRequestDto, @PathVariable String movieId, @PathVariable String screenId) {
        ApiResponse<ShowResponseDto> response = ApiResponse.<ShowResponseDto>builder()
                .status(HttpStatus.CREATED.value())
                .message(Messages.SUCCESS)
                .data(showService.saveShow(showRequestDto, movieId, screenId))
                .multiple(Messages.FALSE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ApiResponse<List<ShowResponseDto>>> getShows(@PathVariable String movieId) {
        ApiResponse<List<ShowResponseDto>> response = ApiResponse.<List<ShowResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .data(showService.getShows(movieId))
                .multiple(Messages.TRUE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{showId}")
    public ResponseEntity<ApiResponse<ShowResponseDto>> showActiveInactive(@PathVariable String showId) {
        ApiResponse<ShowResponseDto> response = ApiResponse.<ShowResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .data(showService.showActiveInactive(showId))
                .multiple(Messages.FALSE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{showId}")
    public ResponseEntity<ApiResponse<String>> deleteShow(@PathVariable String showId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .data(showService.deleteShow(showId))
                .multiple(Messages.FALSE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{showId}")
    public ResponseEntity<ApiResponse<ShowResponseDto>> updateShow(@PathVariable String showId, @RequestBody ShowRequestDto showRequestDto) {
        ApiResponse<ShowResponseDto> response = ApiResponse.<ShowResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .data(showService.updateShow(showId, showRequestDto))
                .multiple(Messages.FALSE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
