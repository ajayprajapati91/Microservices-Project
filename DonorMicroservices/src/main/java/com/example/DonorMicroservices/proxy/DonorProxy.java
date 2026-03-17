package com.example.DonorMicroservices.proxy;

import com.example.DonorMicroservices.enums.BloodGroup;
import com.example.DonorMicroservices.enums.Status;
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
public class DonorProxy {

    private Long donorId ;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    private Integer  age;
    private String name;
    private String gender;
    private String city;
    private double weight;
    private double height;
}
