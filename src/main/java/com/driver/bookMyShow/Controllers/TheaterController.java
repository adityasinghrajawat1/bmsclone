package com.driver.bookMyShow.Controllers;

import com.driver.bookMyShow.Dtos.RequestDtos.ScreenEntryDto;
import com.driver.bookMyShow.Dtos.RequestDtos.TheaterEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnScreenDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterDetailsDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterFacilityDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnTheaterDto;
import com.driver.bookMyShow.Response.ApiResponse;
import com.driver.bookMyShow.Services.TheaterService;
import com.driver.bookMyShow.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theater")
public class TheaterController {
    @Autowired
    private TheaterService theaterService;


    @PostMapping
    public ResponseEntity<ApiResponse<ReturnTheaterDto>> saveTheater(@RequestBody TheaterEntryDto theaterEntryDto) {
        ApiResponse<ReturnTheaterDto> response = ApiResponse.<ReturnTheaterDto>builder()
                .status(HttpStatus.CREATED.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(theaterService.addTheaterService(theaterEntryDto))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{theaterId}/facilities/{facilityId}/toggle")
    public ResponseEntity<ApiResponse<ReturnTheaterFacilityDto>> theaterFacilityActiveInactive(@PathVariable String theaterId, @PathVariable String facilityId) {
        ApiResponse<ReturnTheaterFacilityDto> response = ApiResponse.<ReturnTheaterFacilityDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(theaterService.theaterFacilityOnAndOffService(theaterId, facilityId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReturnTheaterDetailsDto>>> getTheaters() {
        ApiResponse<List<ReturnTheaterDetailsDto>> response = ApiResponse.<List<ReturnTheaterDetailsDto>>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.TRUE)
                .data(theaterService.getAllTheaterDetailsService())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReturnTheaterDto>> getById(@PathVariable String id) {
        ApiResponse<ReturnTheaterDto> response = ApiResponse.<ReturnTheaterDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(theaterService.getTheaterDetailsByIdService(id))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReturnTheaterDto>> update(@PathVariable String id, @RequestBody TheaterEntryDto theaterEntryDto) {
        ApiResponse<ReturnTheaterDto> response = ApiResponse.<ReturnTheaterDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(theaterService.updateTheaterDetailsByIdService(id, theaterEntryDto))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{theaterId}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String theaterId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(theaterService.deleteTheaterService(theaterId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{theaterId}")
    public ResponseEntity<ApiResponse<ReturnScreenDto>> saveTheaterScreen(@PathVariable String theaterId, @RequestBody ScreenEntryDto screenEntryDto) {
        ApiResponse<ReturnScreenDto> response = ApiResponse.<ReturnScreenDto>builder()
                .status(HttpStatus.CREATED.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(theaterService.saveTheaterScreen(screenEntryDto, theaterId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{theaterId}/screens")
    public ResponseEntity<ApiResponse<List<ReturnScreenDto>>> getTheaterScreens(@PathVariable String theaterId) {
        ApiResponse<List<ReturnScreenDto>> response = ApiResponse.<List<ReturnScreenDto>>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.TRUE)
                .data(theaterService.getTheaterScreens(theaterId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{theaterId}/screen/{screenId}")
    public ResponseEntity<ApiResponse<String>> deleteTheaterScreen(@PathVariable String theaterId, @PathVariable String screenId) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(theaterService.deleteTheaterScreen(theaterId, screenId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{theaterId}/screen/{screenId}")
    public ResponseEntity<ApiResponse<ReturnScreenDto>> theaterScreenActiveInactive(@PathVariable String theaterId, @PathVariable String screenId) {
        ApiResponse<ReturnScreenDto> response = ApiResponse.<ReturnScreenDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(theaterService.theaterScreenActiveInactive(theaterId, screenId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
