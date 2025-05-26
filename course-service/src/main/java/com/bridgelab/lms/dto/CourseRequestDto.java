package com.bridgelab.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {

        private Long id;

    private String title;

    private String description;

    private String instructorId;



}
