package com.bridgelab.lms.feignclient;

import com.bridgelab.lms.dto.OtpVerificationRequest;
import com.bridgelab.lms.dto.RegistrationRequest;
import com.bridgelab.lms.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", path = "/api/users") // service name registered with Eureka or your config
public interface UserClient {

    @PostMapping()
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto);

    @GetMapping("/")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) ;

    @GetMapping("/verify")
    public ResponseEntity<UserDto> verifyUser(@RequestBody OtpVerificationRequest Request) ;


}
