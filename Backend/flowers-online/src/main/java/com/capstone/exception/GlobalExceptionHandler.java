package com.capstone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<String>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.add(error.getDefaultMessage()));
        exception.getBindingResult().getGlobalErrors()
                .forEach(error -> errors.add(error.getDefaultMessage()));

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
        );
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception) {
        List<String> errors = new ArrayList<String>();
        errors.add(exception.getMessage());

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                errors
        );
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
