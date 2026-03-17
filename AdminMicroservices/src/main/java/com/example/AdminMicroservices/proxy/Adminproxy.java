package com.example.AdminMicroservices.proxy;

import com.example.AdminMicroservices.enums.BloodGroup;
import com.example.AdminMicroservices.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adminproxy {

    private Long donationId;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Integer unitsAvailable;
    private LocalDateTime lastUpdated;
    private Integer age;

}
