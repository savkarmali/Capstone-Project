package com.capstone.survey.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponseDto<T> {

    private final LocalDateTime timestamp;

    private final boolean success;

    private final String message;

    private final T data;

    public static <T> ApiResponseDto<T> success(String message, T data) {
        return ApiResponseDto.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponseDto<T> failure(String message, T data) {
        return ApiResponseDto.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(false)
                .message(message)
                .data(data)
                .build();
    }
}