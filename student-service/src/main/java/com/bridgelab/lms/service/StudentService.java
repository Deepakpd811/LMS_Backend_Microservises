package com.bridgelab.lms.service;

import com.bridgelab.lms.dto.CourseDto;
import com.bridgelab.lms.dto.ProfileDto;
import com.bridgelab.lms.dto.UserDto;
import com.bridgelab.lms.dto.student.StudentCreateDTO;
import com.bridgelab.lms.dto.student.StudentDTO;
import com.bridgelab.lms.dto.student.StudentUpdateDTO;
import com.bridgelab.lms.entity.Enrollment;
import com.bridgelab.lms.entity.Student;
import com.bridgelab.lms.exception.InvalidException;
import com.bridgelab.lms.feignclient.CourseClient;
import com.bridgelab.lms.feignclient.UserClient;
import com.bridgelab.lms.repository.EnrollmentRepository;
import com.bridgelab.lms.repository.StudentRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserClient userServiceClient;
    @Autowired
    private  final CourseClient courseClient;

    @Autowired
    private ModelMapper modelMapper;

    // Create minimal Student after User creation
    public StudentCreateDTO createStudent(StudentCreateDTO dto) {
        if(studentRepository.existsById(dto.getUserId())) {
            throw new InvalidException("Student already exists with id: " + dto.getUserId());
        }
        Student student = Student.builder()
                .id(dto.getUserId())
                .enrollmentNumber(dto.getEnrollmentNumber())
                .build();
        studentRepository.save(student);
        return  dto;
    }

    // Update other student details
    public void updateStudent(Long userId, StudentUpdateDTO dto) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new InvalidException("Student not found with id: " + userId));

        student.setEnrollmentDate(LocalDate.now());
        student.setCurrentDegree(dto.getCurrentDegree());
        student.setYear(dto.getYear());
        student.setBranch(dto.getBranch());
        student.setUniversity(dto.getUniversity());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());

        studentRepository.save(student);

    }

    public ProfileDto getProfile(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new InvalidException("Student not found with id: " + studentId));

        // Step 1: Get student details from User Service
        UserDto userDto = validateUserExists(studentId);

        // Step 2: Get all enrollments for the student
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        // Step 3: For each enrollment, fetch course details using Feign Client
        List<CourseDto> courseInfoList = enrollments.stream().map(enrollment -> {
            CourseDto courseDto = courseClient.getCourseById(enrollment.getCourseId()).getBody();
            return new CourseDto(
                    courseDto.getId(),
                    courseDto.getTitle(),
                    courseDto.getDescription(),
                    courseDto.getInstructorId()
            );
        }).collect(Collectors.toList());

        // Step 4: Construct the final profile response

        ProfileDto profileDto = ProfileDto.builder()
                .courses(courseInfoList)
                .credential(userDto)
                .currentDegree(student.getCurrentDegree())
                .enrollmentDate(student.getEnrollmentDate())
                .address(student.getAddress())
                .phone(student.getPhone())
                .year(student.getYear())
                .branch(student.getBranch())
                .enrollmentNumber(student.getEnrollmentNumber())
                .id(student.getId())
                .build();

        return  profileDto;

    }

    private UserDto validateUserExists(Long userId) {
        try {
            UserDto userDto = userServiceClient.getUserById(userId).getBody();
            return userDto;
        } catch (FeignException.NotFound | FeignException.BadRequest e) {
            throw new InvalidException("Student/User ID " + userId + " does not exist in User Service.");
        }
    }

    public StudentDTO getStudentById(Long userId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new InvalidException("Student not found with id: " + userId));

        return modelMapper.map(student, StudentDTO.class);
    }

    public List<StudentDTO> getAllStudent() {
        List<Student> students = studentRepository.findAll();

        System.out.println(students);

        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .toList();
    }




}
