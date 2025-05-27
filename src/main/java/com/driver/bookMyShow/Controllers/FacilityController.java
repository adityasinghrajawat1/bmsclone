package com.driver.bookMyShow.Controllers;

import com.driver.bookMyShow.Dtos.RequestDtos.FacilityEntryDto;
import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnFacilityDto;
import com.driver.bookMyShow.Response.ApiResponse;
import com.driver.bookMyShow.Services.FacilityService;
import com.driver.bookMyShow.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facility")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReturnFacilityDto>> addFacility(@RequestBody FacilityEntryDto facilityEntryDto) {
        ReturnFacilityDto returnFacilityDto = facilityService.addFacilityService(facilityEntryDto);
        ApiResponse<ReturnFacilityDto> response = ApiResponse.<ReturnFacilityDto>builder()
                .status(HttpStatus.CREATED.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(returnFacilityDto)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReturnFacilityDto>>> getAllFacility() {
        List<ReturnFacilityDto> returnFacilityDtoList = facilityService.getAllFacilityService();
        ApiResponse<List<ReturnFacilityDto>> response = ApiResponse.<List<ReturnFacilityDto>>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.TRUE)
                .data(returnFacilityDtoList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{facilityId}")
    public ResponseEntity<ApiResponse<String>> deleteFacility(@PathVariable String facilityId) {
        String msg = facilityService.deleteFacilityService(facilityId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(msg)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{facilityId}")
    public ResponseEntity<ApiResponse<ReturnFacilityDto>> updateFacility(@PathVariable String facilityId, @RequestBody FacilityEntryDto facilityEntryDto) {
        ReturnFacilityDto returnFacilityDto = facilityService.updateFacilityService(facilityId, facilityEntryDto);
        ApiResponse<ReturnFacilityDto> response = ApiResponse.<ReturnFacilityDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(returnFacilityDto)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{facilityId}")
    public ResponseEntity<ApiResponse<ReturnFacilityDto>> getFacilityById(@PathVariable String facilityId) {
        ApiResponse<ReturnFacilityDto> response = ApiResponse.<ReturnFacilityDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(facilityService.getFacilityById(facilityId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{facilityId}")
    public ResponseEntity<ApiResponse<ReturnFacilityDto>> facilityOnAndOff(@PathVariable String facilityId) {
        ApiResponse<ReturnFacilityDto> response = ApiResponse.<ReturnFacilityDto>builder()
                .status(HttpStatus.OK.value())
                .message(Messages.SUCCESS)
                .multiple(Messages.FALSE)
                .data(facilityService.facilityOnAndOffService(facilityId))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
