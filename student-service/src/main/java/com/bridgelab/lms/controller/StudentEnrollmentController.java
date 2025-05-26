package com.bridgelab.lms.controller;



import com.bridgelab.lms.dto.EnrollmentDto;
import com.bridgelab.lms.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/enroll")
@RequiredArgsConstructor
public class StudentEnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollments() {
        System.out.println("mapping");
        return ResponseEntity.ok(enrollmentService.getAllEnrollment());
    }

    @PostMapping
    public ResponseEntity<EnrollmentDto> createEnrollment(
            @Valid @RequestBody EnrollmentDto enrollmentDto) {
        return ResponseEntity.ok(enrollmentService.createEnrollment(enrollmentDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDto> getEnrollmentBYId(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }




}