package com.bridgelab.lms.dto;

import com.bridgelab.lms.entity.Schedule;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullBatchDTO {
    private Long id;
    private String name;
    private InstructorDTO instructor;
    private CourseDto course;
    private Schedule schedule;
    private List<StudentDTO> studentList;
}
