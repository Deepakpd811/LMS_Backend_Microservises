package com.bridgelab.lms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private final LocalDateTime timestamp;
    private final String status;
    private final String message;
    private final String path;
    private final T data;
    private final String error;

    public static <T> ApiResponse<T> success(T data) {
        return success(data, null);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.toString())
                .message(message != null ? message : "Operation successful")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String errorMessage) {
        return error(status, errorMessage, null, null);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String errorMessage, String path) {
        return error(status, errorMessage, path, null);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String errorMessage, String path, T errorDetails) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(status.toString())
                .message(errorMessage)
                .path(path)
                .error(status.getReasonPhrase())
                .data(errorDetails)
                .build();
    }
}