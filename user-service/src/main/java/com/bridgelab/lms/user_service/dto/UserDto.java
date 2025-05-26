package com.bridgelab.lms.user_service.dto;
import com.bridgelab.lms.user_service.entity.Role;
import com.bridgelab.lms.user_service.entity.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String password;
    
    private String otp;

    private LocalDateTime otpGeneratedAt;

    private Status status;

    private Role role;

}
