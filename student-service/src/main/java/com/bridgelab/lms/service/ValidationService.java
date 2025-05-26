package com.bridgelab.lms.service;

import com.bridgelab.lms.dto.CourseDto;
import com.bridgelab.lms.dto.UserDto;
import com.bridgelab.lms.exception.InvalidException;
import com.bridgelab.lms.feignclient.CourseClient;
import com.bridgelab.lms.feignclient.UserClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserClient userClient;

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

    public UserDto validateUserExists(Long userId) {
        try {
            UserDto userDto = userClient.getUserById(userId).getBody();
            return userDto;
        } catch (FeignException.NotFound | FeignException.BadRequest e) {
            throw new InvalidException("Student/User ID " + userId + " does not exist in User Service.");
        }
    }
}
