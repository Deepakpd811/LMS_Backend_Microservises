package com.bridgelab.lms.service;


import com.bridgelab.lms.dto.CourseDto;
import com.bridgelab.lms.dto.CourseRequestDto;
import com.bridgelab.lms.entity.Course;
import com.bridgelab.lms.exception.ResourceNotFoundException;
import com.bridgelab.lms.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {


    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public CourseService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<CourseDto> getAllCourses() {
        List<Course> courseList = courseRepository.findAll();
        List<CourseDto> dtoList = courseList.stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
        return  dtoList;

    }

    public List<CourseDto> getCourseByInstructorId(Long instid){
        List<Course> courseList = courseRepository.findByInstructorId(instid);
        List<CourseDto> dtoList = courseList.stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
        return  dtoList;
    }

    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("courser not found "+id));
        return modelMapper.map(course, CourseDto.class);
    }

    public CourseDto createCourse(CourseRequestDto createCourseDto) {
        Course course = modelMapper.map(createCourseDto, Course.class); // dto to entity
        Course savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseDto.class);
    }

    public CourseDto updateCourse(Long id, CourseRequestDto updateCourseDto) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("courser not found "+id));
        updateCourseDto.setId(id);

        modelMapper.map(updateCourseDto, existingCourse);
        Course updatedCourse = courseRepository.save(existingCourse);
        return modelMapper.map(updatedCourse, CourseDto.class);
    }


}

