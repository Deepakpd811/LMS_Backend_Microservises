package com.bridgelab.lms.dto.student;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentUpdateDTO {
    private LocalDate enrollmentDate;
    private String currentDegree;
    private String year;
    private String branch;
    private String university;
    private String phone;
    private String address;
}

