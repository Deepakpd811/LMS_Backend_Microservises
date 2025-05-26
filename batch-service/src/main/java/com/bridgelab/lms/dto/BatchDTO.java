package com.bridgelab.lms.dto;

import com.bridgelab.lms.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchDTO {
    private Long id;
    private Long courseId;
    private Long instructorId;
    private Set<Long> studentIds;
    private LocalDate startDate;
    private LocalDate endDate;
    private Schedule schedule;
}