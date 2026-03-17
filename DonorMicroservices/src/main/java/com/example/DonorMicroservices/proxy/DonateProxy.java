package com.example.DonorMicroservices.proxy;

import com.example.DonorMicroservices.enums.BloodGroup;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonateProxy {
    private LocalDate donationDate;
    private double quantity;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    private String remarks;
    private Long donorId;
}
