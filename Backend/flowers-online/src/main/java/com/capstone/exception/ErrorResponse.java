package com.capstone.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private List<String> errors;
}
