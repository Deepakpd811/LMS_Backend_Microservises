package com.bridgelab.lms.service;

import com.bridgelab.lms.dto.*;
import com.bridgelab.lms.entity.Status;
import com.bridgelab.lms.exception.*;
import com.bridgelab.lms.feignclient.UserClient;
import com.bridgelab.lms.util.JwtUtil;
import com.bridgelab.lms.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {


    private final UserClient userClient;
    private final JwtUtil jwtUtil;
    private final OtpUtil otpUtil;
    private final PasswordEncoder passwordEncoder;
    //private final EmailService emailService;

    @Autowired
    public AuthService(UserClient userClient, JwtUtil jwtUtil, OtpUtil otpUtil,
                       PasswordEncoder passwordEncoder) {
        this.userClient = userClient;
        this.jwtUtil = jwtUtil;
        this.otpUtil = otpUtil;
        this.passwordEncoder = passwordEncoder;

    }

    public UserDto register(RegistrationRequest request) {
        // Check if user already exists
        ResponseEntity<UserDto> existingUser = userClient.getUserByEmail(request.getEmail());
        if (existingUser.getBody() != null) {
            throw new EmailAlreadyExistException("Email already registered");
        }

        // Generate and process OTP
        String otp = otpUtil.generateOtp();
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserDto userDto = UserDto.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .otp(otp)
                .username(request.getUsername())
                .role(request.getRole())
                .otpGeneratedAt(LocalDateTime.now())
                .status(Status.PENDING)
                .build();

        // Create user
        ResponseEntity<UserDto> response = userClient.createUser(userDto);
        UserDto createdUser = response.getBody();

        // Send OTP email
       // emailService.sendOtpEmail(request.getEmail(), otp);

        return createdUser;
    }

    @Transactional
    public AuthResponse verifyOtp(OtpVerificationRequest request) {
        // Get user from user service
        ResponseEntity<UserDto> userResponse = userClient.verifyUser(request);;

        UserDto user = userResponse.getBody();

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return AuthResponse.builder()
                .user(user)
                .build();
    }

//    @Transactional
//    public void resendOtp(ResendOtpRequest request) {
//        ResponseEntity<UserDto> userResponse = userClient.getUserByEmail(request.getEmail());
//        UserDto user = userResponse.getBody();
//
//        if (user == null) {
//            throw new UserNotFoundException("User not found");
//        }
//
//        if (user.getStatus() == Status.ACTIVE) {
//            throw new EmailAlreadyExistException("User already verified");
//        }
//
//        // Generate new OTP
//        String newOtp = otpUtil.generateOtp();
//
//        // Update user with new OTP
//        userClient.updateOtp(request.getEmail(), newOtp);
//
//        // Resend email
//        emailService.sendOtpEmail(request.getEmail(), newOtp);
//    }

    public AuthResponse login(LoginRequest request) {
        ResponseEntity<UserDto> userResponse = userClient.getUserByEmail(request.getEmail());
        UserDto user = userResponse.getBody();

        if (user == null) {
            throw new UserNotFoundException("Invalid credentials");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!user.getStatus().equals(Status.ACTIVE)) {
            throw new RuntimeException("Please verify your email first");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());

        return AuthResponse.builder()
                .token(token)
                .user(user)
                .build();
    }
}