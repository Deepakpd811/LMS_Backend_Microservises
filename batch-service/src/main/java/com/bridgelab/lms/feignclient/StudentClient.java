package com.bridgelab.lms.feignclient;

import com.bridgelab.lms.dto.StudentDTO;
import com.bridgelab.lms.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "student-service", path = "/api/students") // service name registered with Eureka or your config
public interface StudentClient {


    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudent();

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(Long studentId);




}
