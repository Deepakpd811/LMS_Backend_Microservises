package com.bridgelab.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OtpVerificationRequest {
    @Email
    @NotBlank
    private String email;

    @Size(min = 6, max = 6)
    private String otp;
}
