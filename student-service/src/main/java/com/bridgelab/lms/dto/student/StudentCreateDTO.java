package com.bridgelab.lms.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreateDTO {
    private Long userId;
    private String enrollmentNumber;
}
