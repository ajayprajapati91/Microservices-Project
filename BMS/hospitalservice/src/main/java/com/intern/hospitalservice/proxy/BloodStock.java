package com.intern.hospitalservice.proxy;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BloodStock {
    private Long id;
    private String bloodGroup;
    private Double unitsAvailable;
    private LocalDateTime lastUpdated;
}
