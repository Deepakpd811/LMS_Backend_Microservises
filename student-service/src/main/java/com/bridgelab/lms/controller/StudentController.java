package com.bridgelab.lms.controller;

import com.bridgelab.lms.dto.ProfileDto;
import com.bridgelab.lms.dto.student.StudentCreateDTO;
import com.bridgelab.lms.dto.student.StudentDTO;
import com.bridgelab.lms.dto.student.StudentUpdateDTO;
import com.bridgelab.lms.service.EnrollmentService;
import com.bridgelab.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @PostMapping
    public ResponseEntity<StudentCreateDTO> createStudent(@RequestBody StudentCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(dto));
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getProfile(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudent() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentUpdateDTO dto
    ) {
        studentService.updateStudent(id, dto);
        return ResponseEntity.ok("Student updated successfully");
    }

}
