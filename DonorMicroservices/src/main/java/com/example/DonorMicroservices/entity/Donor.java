package com.example.DonorMicroservices.entity;

import com.example.DonorMicroservices.enums.BloodGroup;
import com.example.DonorMicroservices.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donor_id")
    private Long donorId ;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    private Integer  age;
    private String name;
    private String gender;
    private double weight;
    private double height;
    private String city;
    private LocalDate lastDonationDate;
    @Enumerated(EnumType.STRING)
    private Status status;


}
