package com.example.DonorMicroservices.customizedException;

import java.time.LocalDateTime;

public class DonorNotFoundException extends RuntimeException {
    private String errorMsg;
    private Integer errorCode;
    private LocalDateTime dateTime;

    public DonorNotFoundException(String errorMsg, Integer errorCode) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
        this.dateTime = LocalDateTime.now();
    }
}
