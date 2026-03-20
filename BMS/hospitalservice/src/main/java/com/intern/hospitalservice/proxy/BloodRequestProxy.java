package com.intern.hospitalservice.proxy;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodRequestProxy {
    private Long id;
    @NotBlank
    private String bloodGroup;
    @Max(value = 1000 , message = "You can't demand more than 1000ml blood")
    @Min(value = 100 , message = "You can't demand less than 100ml blood")
    private Double quantity;
    private LocalDate requestDate;
    private String status;
    private HospitalProxy hospital;
}
