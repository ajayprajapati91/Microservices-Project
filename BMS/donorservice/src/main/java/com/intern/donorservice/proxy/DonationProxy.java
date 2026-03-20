package com.intern.donorservice.proxy;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationProxy {
    private Long id;
    private LocalDate donationDate;
    @Max(value = 400 , message = "You can't donate more than 400ml blood")
    @Min(value = 100 , message = "You can't demand less than 100ml blood")
    private Double quantity;
    private String remarks;

    private DonorDetailsProxy donorDetails;


}
