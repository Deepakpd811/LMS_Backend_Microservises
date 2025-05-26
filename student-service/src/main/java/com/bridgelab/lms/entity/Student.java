package com.bridgelab.lms.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    private Long id; // Same as User ID

    @Column(nullable = false, unique = true)
    private String enrollmentNumber;

    private LocalDate enrollmentDate;

    private String currentDegree;

    private String year;

    private String branch;

    private String university;

    private String phone;

    private String address;

}

