package com.intern.donorservice.proxy;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonorDetailsProxy {
    private Long id;
    @NotBlank
    @Pattern(regexp = "^(A|B|AB|O)[+-]$",
            message = "Invalid blood group")
    private String bloodGroup;
    @Min(value = 18 , message = "Age should be greater than 18")
    private Integer age;
    @Pattern(regexp = "^(Male|Female)$",
            message = "Invalid gender details")
    private String gender;
    @NotBlank(message = "city cant be blank")
    private String city;
    private LocalDate lastDonationDate;
    private Boolean available;
    private Long userId;
}
