package com.bridgelab.lms.feignclient;

import com.bridgelab.lms.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", path = "/api/users") // service name registered with Eureka or your config
public interface UserClient {


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@RequestParam Long id) ;




}
