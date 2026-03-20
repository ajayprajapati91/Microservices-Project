package com.intern.donorservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate donationDate;
    private Double quantity;
    private String remarks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "donor_id")
    private DonorDetails donorDetails;
}
