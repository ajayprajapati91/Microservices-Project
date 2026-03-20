package com.intern.donorservice.proxy;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DonationHistoryProxy {
    private Long id;
    private String bloodGroup;
    private Double quantity;
    private LocalDate donationDate;
    private String remarks;
}
