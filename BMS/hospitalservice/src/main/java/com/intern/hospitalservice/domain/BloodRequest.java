package com.intern.hospitalservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bloodGroup;
    private Double quantity;
    private LocalDate requestDate;
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
