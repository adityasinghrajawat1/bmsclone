package com.driver.bookMyShow.Controllers;

import com.driver.bookMyShow.Dtos.ResponseDtos.ReturnScreenDto;
import com.driver.bookMyShow.Models.Screen;
import com.driver.bookMyShow.Response.ApiResponse;
import com.driver.bookMyShow.Services.ScreenService;
import com.driver.bookMyShow.constant.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/screen")
public class ScreenController {
    @Autowired
    private ScreenService screenService;

    @GetMapping("/{id}/type/{screenType}")
    public ResponseEntity<ApiResponse<ReturnScreenDto>> findScreenByIdAndType(@PathVariable String id, @PathVariable String screenType) {
        ApiResponse<ReturnScreenDto> response = ApiResponse.<ReturnScreenDto>builder()
                .multiple(Messages.FALSE)
                .status(HttpStatus.FOUND.value())
                .data(screenService.findByIdAndScreenType(id, screenType))
                .message(Messages.SUCCESS)
                .build();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
