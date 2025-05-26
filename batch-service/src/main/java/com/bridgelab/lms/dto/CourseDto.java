package com.bridgelab.lms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private Long id;

    private String title;

    private String description;

    private String instructorId;

    private String batchId;

    private boolean isActive = true;


    public CourseDto(Long id, String title, String description, String instructorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
    }
}
