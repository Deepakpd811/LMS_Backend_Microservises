package com.bridgelab.lms.dto;


import lombok.Builder;
import lombok.Data;

// AuthResponse.java
@Data
@Builder
public class AuthResponse {
    private String token;
    private UserDto user;
}