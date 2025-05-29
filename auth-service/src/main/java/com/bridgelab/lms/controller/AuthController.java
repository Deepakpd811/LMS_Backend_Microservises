package com.bridgelab.lms.controller;

import com.bridgelab.lms.dto.*;
import com.bridgelab.lms.entity.Role;
import com.bridgelab.lms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private  AuthService authService;

//    @Autowired
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(
            @Valid @RequestBody RegistrationRequest request) {
        UserDto registeredUser = authService.register(request);
        return ResponseEntity.ok(
                ApiResponse.success(
                        registeredUser,
                        "OTP has been sent to your email. Please verify to complete registration."
                )
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyOtp(
            @Valid @RequestBody OtpVerificationRequest request) {
        AuthResponse authResponse = authService.verifyOtp(request);
        return ResponseEntity.ok(
                ApiResponse.success(
                        authResponse,
                        "OTP verified successfully. You are now logged in."
                )
        );
    }

//    @PostMapping("/resend-otp")
//    public ResponseEntity<ApiResponse<Void>> resendOtp(
//            @Valid @RequestBody ResendOtpRequest request) {
//        authService.resendOtp(request);
//        return ResponseEntity.ok(
//                ApiResponse.success(
//                        null,
//                        "New OTP has been sent to your email."
//                )
//        );
//    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(
                ApiResponse.success(
                        authResponse,
                        "Login successful"
                )
        );
    }

//    @GetMapping("/validate")
//    public ResponseEntity<ApiResponse> validateToken(@RequestHeader("Authorization") String authHeader){
//
//        if(authHeader==null || !authHeader.startsWith("Bearer ")){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        return authService.validateToken(authHeader.substring(7))
//                ?ResponseEntity.ok(ApiResponse.success(HttpStatus.OK , "validate successfull"))
//                :ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//
//    }
//

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse> validateToken(@RequestHeader("Authorization") String authHeader,
    @RequestBody List<String> roles){

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(authHeader.substring(7) , roles)
                ?ResponseEntity.ok(ApiResponse.success(HttpStatus.OK , "validate successfull"))
                :ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }



}