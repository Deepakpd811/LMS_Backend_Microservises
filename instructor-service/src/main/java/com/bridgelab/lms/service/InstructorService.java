package com.bridgelab.lms.service;


import com.bridgelab.lms.dto.CourseDto;
import com.bridgelab.lms.dto.InstructorDTO;
import com.bridgelab.lms.dto.InstructorRequest;
import com.bridgelab.lms.dto.UserDto;
import com.bridgelab.lms.entity.Instructor;
import com.bridgelab.lms.exception.InvalidException;
import com.bridgelab.lms.feignclient.CourseClient;
import com.bridgelab.lms.feignclient.UserClient;
import com.bridgelab.lms.repository.InstructorRepository;
import jakarta.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService  {

    @Autowired
    private final InstructorRepository instructorRepository;
    @Autowired
    private final UserClient userClient;
    @Autowired
    private final CourseClient courseClient;

    @Autowired
    private ValidationService validation;
//    private final BatchClient batchClient;


    public InstructorDTO createInstructor(InstructorRequest request) {

        validation.validateUserExists(request.getUserId());

        Instructor instructor = new Instructor();

                instructor.setUserId(request.getUserId());
                instructor.setExpertise (request.getExpertise());
                instructor.setJoinedDate(LocalDate.now());

        Instructor saved = instructorRepository.save(instructor);
        return mapToDTO(saved);
    }


    // profile of instructor
    public InstructorDTO getInstructorById(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InvalidException("Instructor not found"));

        UserDto user = validation.validateUserExists(instructor.getUserId());

        List<CourseDto> courses = validation.validateCourseExists(instructorId);

        InstructorDTO dto = mapToDTO(instructor);
        dto.setCredential(user);
        dto.setCourseDtoList(courses);
        return dto;
    }


    public List<CourseDto> getCoursesByInstructor(Long instructorId) {
        return validation.validateCourseExists(instructorId);
    }


//    public List<BatchDTO> getBatchesByInstructor(Long instructorId) {
//        return batchClient.getBatchesByInstructorId(instructorId);
//    }


//    public MentorshipStatsDTO getMentorshipStats(Long instructorId) {
//        List<BatchDTO> batches = batchClient.getBatchesByInstructorId(instructorId);
//        int totalStudents = batches.stream()
//                .mapToInt(BatchDTO::getStudentCount)
//                .sum();
//
//        return new MentorshipStatsDTO(batches.size(), totalStudents, 4.5); // mocked rating
//    }

    private InstructorDTO mapToDTO(Instructor instructor) {
        return InstructorDTO.builder()
                .id(instructor.getId())
                .userId(instructor.getUserId())
                .expertise(instructor.getExpertise())
                .joinedDate(instructor.getJoinedDate())
                .build();
    }



}
