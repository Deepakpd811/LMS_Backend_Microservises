package com.bridgelab.lms.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ProfileDto {


    private Long id; // Same as User ID

    private UserDto credential;

    private String enrollmentNumber;

    private LocalDate enrollmentDate;

    private String currentDegree;

    private String year;

    private String branch;

    private String university;

    private String phone;

    private String address;


    List<CourseDto> courses;

}
