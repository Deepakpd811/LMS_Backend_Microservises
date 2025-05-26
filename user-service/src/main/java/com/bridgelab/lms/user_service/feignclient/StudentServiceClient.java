package com.bridgelab.lms.user_service.feignclient;

import com.bridgelab.lms.user_service.dto.StudentCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "student-service")
public interface StudentServiceClient {

    @PostMapping("/api/students")
    void createStudent(@RequestBody StudentCreateDTO dto);
}
