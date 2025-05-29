package com.bridgelab.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long instructorId;
    @JsonIgnore
    private Set<Long> studentIds;
    private LocalDate startDate;
    private LocalDate endDate;

}