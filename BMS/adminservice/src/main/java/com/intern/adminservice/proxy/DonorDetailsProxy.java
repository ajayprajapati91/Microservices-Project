package com.intern.adminservice.proxy;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DonorDetailsProxy {
    private Long id;
    private String bloodGroup;
    private Integer age;
    private String gender;
    private String city;
    private LocalDate lastDonationDate;
    private Boolean available;
    private Long userId;
}
