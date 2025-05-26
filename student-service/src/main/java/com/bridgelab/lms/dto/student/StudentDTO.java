package com.bridgelab.lms.dto.student;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {
    private Long id;
    private String enrollmentNumber;
//    private String enrollmentName;  // fetched from User service
    private LocalDate enrollmentDate;

    private String currentDegree;

    private String year;

    private String branch;

    private String university;

    private String phone;

    private String address;
}
