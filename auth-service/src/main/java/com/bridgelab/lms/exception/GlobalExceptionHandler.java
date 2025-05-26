package com.bridgelab.lms.exception;

import com.bridgelab.lms.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler   {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponse response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message("Validation valid")
                .error("Bad Request")
                .path(request.getRequestURI())
                .data(errors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler
    public ResponseEntity<ApiResponse> CandidateNotFoundException(CandidateNotFoundException ex, HttpServletRequest request) {

        ApiResponse err = ApiResponse.builder()
                .error("Bad Request")
                .message("Candidate with id " + ex.getId() + " does not exist")
                .status(HttpStatus.NOT_FOUND.toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {

        ApiResponse err = ApiResponse.builder()
                .error("Bad Request")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }




    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleCustomBadRequest(EmailAlreadyExistException ex, HttpServletRequest request) {
        ApiResponse response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .error("Bad Request")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailSendFailedException.class)
    public ResponseEntity<ApiResponse> emailFailedException(EmailSendFailedException ex, HttpServletRequest request) {
        ApiResponse response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .error("Bad Request")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

 @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ApiResponse> invalidException(InvalidException ex, HttpServletRequest request) {
        ApiResponse response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .error("Bad Request")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> UserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        ApiResponse response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .error("Bad Request")
                .path(request.getRequestURI())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}
