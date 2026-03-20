package com.intern.adminservice.proxy;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BloodRequest {
    private Long id;
    private String bloodGroup;
    private Double quantity;
    private LocalDate requestDate;
    private String status;
    private Hospital hospital;
}
