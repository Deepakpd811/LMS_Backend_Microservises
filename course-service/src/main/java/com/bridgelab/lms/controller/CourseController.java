package com.bridgelab.lms.controller;



import com.bridgelab.lms.dto.CourseDto;
import com.bridgelab.lms.dto.CourseRequestDto;
import com.bridgelab.lms.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(
            @Valid @RequestBody CourseRequestDto createCourseDto) {
        return ResponseEntity.ok(courseService.createCourse(createCourseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/instructor/{id}/courses")
    public ResponseEntity<List<CourseDto>> getCoursesByInstructorId(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getCourseByInstructorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequestDto updateCourseDto) {
        return ResponseEntity.ok(courseService.updateCourse(id, updateCourseDto));
    }
}