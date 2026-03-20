package com.intern.adminservice.proxy;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Donation {
    private Long id;
    private LocalDate donationDate;
    @Max(value = 400 , message = "You can't donate more than 400ml blood")
    @Min(value = 100 , message = "You can't demand less than 100ml blood")
    private Double quantity;
    private String remarks;
    private DonorDetailsProxy donorDetails;
}
