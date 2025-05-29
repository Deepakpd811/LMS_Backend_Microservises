package com.bridgelab.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
public class InstructorDTO {

    private Long id;
    @JsonIgnore
    private Long userId;
    private String expertise;
    private LocalDate joinedDate;

    private UserDto credential; // populated via Feign client
    @JsonIgnore
    private List<CourseDto> courseDtoList;
    // Constructors, Getters, Setters
}
