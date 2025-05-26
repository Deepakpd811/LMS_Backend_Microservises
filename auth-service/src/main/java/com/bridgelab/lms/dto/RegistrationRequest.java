package com.bridgelab.lms.dto;

import com.bridgelab.lms.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// RegistrationRequest.java
@Data
public class RegistrationRequest {


    private String username;
    private String email;
    private String password;
    private Role role;
}
