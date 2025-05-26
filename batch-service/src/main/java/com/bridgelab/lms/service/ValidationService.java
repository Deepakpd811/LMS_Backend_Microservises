package com.bridgelab.lms.service;

import com.bridgelab.lms.dto.CourseDto;
import com.bridgelab.lms.dto.InstructorDTO;
import com.bridgelab.lms.dto.StudentDTO;
import com.bridgelab.lms.dto.UserDto;
import com.bridgelab.lms.exception.InvalidException;
import com.bridgelab.lms.feignclient.CourseClient;
import com.bridgelab.lms.feignclient.InstructorClient;
import com.bridgelab.lms.feignclient.StudentClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private StudentClient studentClient;

    @Autowired
    private InstructorClient instructorClient;

    public CourseDto validateCourseExists(Long courseId) {
        try {
            CourseDto  courseDto=  courseClient.getCourseById(courseId).getBody();
            return courseDto;
        } catch (FeignException.NotFound e) {
            throw new InvalidException("Course ID " + courseId + " does not exist in Course Service.");
        }
}

//    public List<CourseDto> validateCourseExists(Long instructorId) {
//        try {
//            List<CourseDto>  courseDto=  courseClient.getCoursesByInstructorId(instructorId).getBody();
//            return courseDto;
//        } catch (FeignException.NotFound e) {
//            throw new InvalidException("Instructor ID " + instructorId + " does not exist in Course Service.");
//        }
//
//    }

    public List<StudentDTO> getAllStudents() {
        try {
            List<StudentDTO> studentDTO = studentClient.getAllStudent().getBody();
            return studentDTO;
        } catch (FeignException.NotFound | FeignException.BadRequest e) {
            throw new InvalidException(" ALL student not found ");
        }
    }

    public InstructorDTO validateInstructorExists(Long instructorId) {
        try {
            InstructorDTO instructorDTO =  instructorClient.getInstructorById(instructorId).getBody();
            return instructorDTO;
        } catch (FeignException.NotFound | FeignException.BadRequest e) {
            throw new InvalidException("Instructor  ID " + instructorId + " does not exist in User Service.");
        }
    }
    public StudentDTO validateStudentExists(Long studentId) {
        try {
            StudentDTO studentDTO =  studentClient.getStudentById(studentId).getBody();
            return studentDTO;
        } catch (FeignException.NotFound | FeignException.BadRequest e) {
            throw new InvalidException("Instructor  ID " + studentId + " does not exist in User Service.");
        }
    }
}
