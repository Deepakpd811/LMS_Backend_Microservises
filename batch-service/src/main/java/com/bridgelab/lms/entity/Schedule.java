package com.bridgelab.lms.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private String days; // e.g. "Mon,Wed,Fri"
    private String time; // e.g. "10:00 AM"
}
