package com.example.AdminMicroservices.proxy;

import com.example.AdminMicroservices.enums.BloodGroup;
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
public class BloodStockProxy {
    private Long bloodStockId;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    private Integer unitsAvailable;
    private LocalDateTime lastUpdated;
}
