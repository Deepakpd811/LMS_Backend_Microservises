package com.bridgelab.lms.feignclient;


import com.bridgelab.lms.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "course-service", path = "/api/courses") // service name registered with Eureka or your config
public interface CourseClient {


    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id);

    @GetMapping("/instructor/{id}/courses")
    public ResponseEntity<List<CourseDto>> getCoursesByInstructorId(@PathVariable Long id);



}
