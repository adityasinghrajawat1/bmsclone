package com.driver.bookMyShow.Controllers;

import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnSeatingArrangementDto;
import com.driver.bookMyShow.Response.ApiResponse;
import com.driver.bookMyShow.Services.SeatingArrangementService;
import com.driver.bookMyShow.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seating/v1")
public class SeatingArrangementController {
    @Autowired
    private SeatingArrangementService seatingArrangementService;

//    @GetMapping("/generateSeatingArrangement/{screenId}")
//    public ResponseEntity<ApiResponse<List<ReturnSeatingArrangementDto>>> generateSeatingArrangement(@PathVariable String screenId)
//    {
//            List<ReturnSeatingArrangementDto> seatingArrangementV1List = seatingArrangementService.generateSeatingFromCapacityService(screenId);
//
//            ApiResponse<List<ReturnSeatingArrangementDto>> response = ApiResponse.<List<ReturnSeatingArrangementDto>>builder()
//                    .status(HttpStatus.OK.value())
//                    .message(Messages.SUCCESS)
//                    .multiple(Messages.TRUE)
//                    .data(seatingArrangementV1List)
//                    .build();
//            return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/{screenId}")
//    public ResponseEntity<ApiResponse<List<ReturnSeatingArrangementDto>>> getAllGeneratedSeats(@PathVariable String screenId)
//    {
//        List<ReturnSeatingArrangementDto> returnSeatingArrangementDtoList = seatingArrangementService.getAllGeneratedSeatsService(screenId);
//
//        ApiResponse<List<ReturnSeatingArrangementDto>> response = ApiResponse.<List<ReturnSeatingArrangementDto>>builder()
//                .status(HttpStatus.OK.value())
//                .message(Messages.SUCCESS)
//                .multiple(Messages.TRUE)
//                .data(returnSeatingArrangementDtoList)
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
