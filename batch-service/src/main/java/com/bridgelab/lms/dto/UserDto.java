package com.bridgelab.lms.dto;


import com.bridgelab.lms.entity.Role;
import com.bridgelab.lms.entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonIgnore
    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String otp;

    @JsonIgnore
    private LocalDateTime otpGeneratedAt;

    private Status status;

    private Role role;

}
