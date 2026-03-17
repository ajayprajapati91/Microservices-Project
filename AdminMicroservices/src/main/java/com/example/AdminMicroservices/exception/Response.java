package com.example.AdminMicroservices.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
    private String message;
    private Integer status;
    private LocalDateTime dateTime;
    private String path;
}
