package com.example.AdminMicroservices.entity;

import com.example.AdminMicroservices.enums.BloodGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bloodStock_id")
    private Long bloodStockId;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    private Integer unitsAvailable;
    private LocalDateTime lastUpdated;
}
