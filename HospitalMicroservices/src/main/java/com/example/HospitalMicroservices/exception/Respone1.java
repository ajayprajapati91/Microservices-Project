package com.example.HospitalMicroservices.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Respone1 {
    private String message;
    private Integer status;
    private LocalDateTime dateTime;
    private String path;
}
