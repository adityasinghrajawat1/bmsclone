package com.driver.bookMyShow.Exceptions;

import com.driver.bookMyShow.Response.ApiResponse;
import com.driver.bookMyShow.constant.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyPresentException.class)
    public ResponseEntity<ApiResponse<Object>> handleAlreadyPresentException(AlreadyPresentException ape) {
        ApiResponse<Object> errorResponse = ApiResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .data(Messages.FAILED)
                .message(ape.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException nfe) {
        ApiResponse<Object> errorResponse = ApiResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .data(Messages.FAILED)
                .message(nfe.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestFailedException.class)
    public ResponseEntity<ApiResponse<Object>> handleRequestFailedException(RequestFailedException rfe) {
        ApiResponse<Object> errorResponse = ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(Messages.FAILED)
                .message(rfe.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidScreenSizeException(InvalidException iss) {
        ApiResponse<Object> errorResponse = ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(Messages.FAILED)
                .message(iss.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleExceptions(Exception e) {
        ApiResponse<Object> errorResponse = ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(Messages.FAILED)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
