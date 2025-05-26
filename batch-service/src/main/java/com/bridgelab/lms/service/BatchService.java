package com.bridgelab.lms.service;


import com.bridgelab.lms.dto.*;
import com.bridgelab.lms.entity.Batch;
import com.bridgelab.lms.exception.InvalidException;
import com.bridgelab.lms.repository.BatchRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository batchRepository;

    @Autowired
    private ValidationService validationService;

    public BatchDTO createBatch(BatchDTO dto) {

        validationService.validateCourseExists(dto.getCourseId());
        validationService.validateInstructorExists(dto.getInstructorId());
//        validationService.validateStudentExists(dto.)

        Batch batch = Batch.builder()
                .courseId(dto.getCourseId())
                .instructorId(dto.getInstructorId())
                .studentIds(dto.getStudentIds())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .schedule(dto.getSchedule())
                .build();

        batch = batchRepository.save(batch);
        dto.setId(batch.getId());
        return dto;
    }

    public List<BatchDTO> getAllBatches() {
        return batchRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BatchDTO getBatchById(Long id) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        return mapToDTO(batch);
    }

    public FullBatchDTO getFullBatchDetails(Long batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new InvalidException("Batch not found"));

        // Fetch Instructor
        InstructorDTO instructor = validationService.validateInstructorExists(batch.getInstructorId());

        // Fetch Course
        CourseDto course = validationService.validateCourseExists(batch.getCourseId());

        // Fetch all students
        List<StudentDTO> allStudents = validationService.getAllStudents();

        // Filter students who belong to this batch
        List<StudentDTO> studentsInBatch = allStudents.stream()
                .filter(student -> batch.getStudentIds().contains(student.getUserId()))
                .toList();

        return FullBatchDTO.builder()
                .id(batch.getId())
                .name(batch.getName())
                .instructor(instructor)
                .course(course)
                .schedule(batch.getSchedule())
                .studentList(studentsInBatch)
                .build();
    }




    private BatchDTO mapToDTO(Batch batch) {
        return BatchDTO.builder()
                .id(batch.getId())
                .courseId(batch.getCourseId())
                .instructorId(batch.getInstructorId())
                .studentIds(batch.getStudentIds())
                .startDate(batch.getStartDate())
                .endDate(batch.getEndDate())
                .schedule(batch.getSchedule())
                .build();
    }
}