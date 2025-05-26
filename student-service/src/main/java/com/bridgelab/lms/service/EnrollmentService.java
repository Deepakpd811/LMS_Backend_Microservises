package com.bridgelab.lms.service;


import com.bridgelab.lms.dto.CourseDto;
import com.bridgelab.lms.dto.EnrollmentDto;
import com.bridgelab.lms.dto.ProfileDto;
import com.bridgelab.lms.dto.UserDto;
import com.bridgelab.lms.entity.Enrollment;
import com.bridgelab.lms.exception.InvalidException;
import com.bridgelab.lms.exception.ResourceNotFoundException;
import com.bridgelab.lms.feignclient.CourseClient;
import com.bridgelab.lms.feignclient.UserClient;
import com.bridgelab.lms.repository.EnrollmentRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {


    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private ValidationService validationService;

    public EnrollmentDto toEnrollmentDto(Enrollment enrollment){
        EnrollmentDto enrollmentDto = new EnrollmentDto();
        enrollmentDto.setCourseId(enrollment.getCourseId());
        enrollmentDto.setStudentId(enrollment.getStudentId());
        return enrollmentDto;
    }

    public Enrollment toEnrollmentEntity(EnrollmentDto dto){
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(dto.getStudentId());
        enrollment.setCourseId(dto.getCourseId());
        return  enrollment;
    }


    public List<EnrollmentDto> getAllEnrollment(){
        List<EnrollmentDto> enrollment = enrollmentRepository.findAll()
                .stream()
                .map(ele-> toEnrollmentDto(ele) )
                .toList();

        return enrollment;

    }

    public EnrollmentDto getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("courser not found "+id));
        return toEnrollmentDto(enrollment);
    }

    public EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto) {
        Enrollment enrollment = toEnrollmentEntity(enrollmentDto) ; // dto to entity

        validationService.validateUserExists(enrollment.getCourseId());
        validationService.validateCourseExists(enrollment.getCourseId());


        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return toEnrollmentDto(savedEnrollment);
    }










}

