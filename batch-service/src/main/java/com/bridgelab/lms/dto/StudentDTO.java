package com.bridgelab.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {
    private Long userId;
    private String enrollmentNumber;
//    private String enrollmentName;  // fetched from User service
}
