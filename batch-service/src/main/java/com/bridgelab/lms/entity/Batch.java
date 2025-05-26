package com.bridgelab.lms.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "batches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Long courseId;
    private Long instructorId;

    private LocalDate startDate;
    private LocalDate endDate;

    @ElementCollection
    @CollectionTable(name = "batch_student_ids", joinColumns = @JoinColumn(name = "batch_id"))
    @Column(name = "student_id")
    private Set<Long> studentIds = new HashSet<>();

    @Embedded
    private Schedule schedule;
}