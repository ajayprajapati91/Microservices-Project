package com.intern.adminservice.proxy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodStockProxy {
    private Long id;
    @NotBlank
    @Pattern(regexp = "^(A|B|AB|O)[+-]$",
            message = "Invalid blood group")
    private String bloodGroup;
    private Double unitsAvailable;
    private LocalDateTime lastUpdated;
}
