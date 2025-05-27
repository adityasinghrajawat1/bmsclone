package com.driver.bookMyShow.Controllers;

import com.driver.bookMyShow.Dtos.RequestDtos.ShowSeatEntryDTO;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnShowSeatDto;
import com.driver.bookMyShow.Response.ApiResponse;
import com.driver.bookMyShow.Services.ShowSeatService;
import com.driver.bookMyShow.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showSeat/v1")
public class ShowSeatController {
    @Autowired
    private ShowSeatService showSeatService;

    @PostMapping("/{showId}")
    public ResponseEntity<ApiResponse<String>> addSeatDetails(@RequestBody ShowSeatEntryDTO showSeatEntryDTO, @PathVariable String showId) {
        String msg = showSeatService.addSeatDetailsService(showSeatEntryDTO, showId);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.CREATED.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReturnShowSeatDto>>> getAllSeatDetails() {
        List<ReturnShowSeatDto> returnShowSeatDtoList = showSeatService.getAllSeatDetailsService();

        ApiResponse<List<ReturnShowSeatDto>> response = ApiResponse.<List<ReturnShowSeatDto>>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.TRUE)
                .data(returnShowSeatDtoList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updatePrice/{showId}")
    public ResponseEntity<ApiResponse<String>> updatePrice(@RequestBody ShowSeatEntryDTO showSeatEntryDTO, @PathVariable String showId) {
        String msg = showSeatService.updateSeatPriceService(showSeatEntryDTO, showId);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
