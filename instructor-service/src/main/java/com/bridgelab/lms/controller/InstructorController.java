package com.bridgelab.lms.controller;


import com.bridgelab.lms.dto.BatchDTO;
import com.bridgelab.lms.dto.CourseDto;
import com.bridgelab.lms.dto.InstructorDTO;
import com.bridgelab.lms.dto.InstructorRequest;
import com.bridgelab.lms.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping
    public ResponseEntity<InstructorDTO> createInstructor(@RequestBody InstructorRequest request) {
        InstructorDTO created = instructorService.createInstructor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //profile
    @GetMapping("/{instructorId}")
    public ResponseEntity<InstructorDTO> getInstructor(@PathVariable Long instructorId) {
        InstructorDTO dto = instructorService.getInstructorById(instructorId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<List<InstructorDTO>> getAllInstructor() {
        List<InstructorDTO> dto = instructorService.getAllInstructor();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{instructorId}/courses")
    public ResponseEntity<List<CourseDto>> getCourses(@PathVariable Long instructorId) {
        return ResponseEntity.ok(instructorService.getCoursesByInstructor(instructorId));
    }

    @GetMapping("/{instructorId}/batches")
    public ResponseEntity<List<BatchDTO>> getBatches(@PathVariable Long instructorId) {
        return ResponseEntity.ok(instructorService.getBatchesByInstructor(instructorId));
    }

//    @GetMapping("/{instructorId}/stats")
//    public ResponseEntity<MentorshipStatsDTO> getMentorshipStats(@PathVariable Long instructorId) {
//        return ResponseEntity.ok(instructorService.getMentorshipStats(instructorId));
//    }
}
