package com.bridgelab.lms.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProfileDto {

    private Long id;

    private String username;

    private String email;

    List<CourseDto> courses;

}
