package com.intern.donorservice.service;

import com.intern.donorservice.domain.Donation;
import com.intern.donorservice.proxy.DonationHistoryProxy;
import com.intern.donorservice.proxy.DonationProxy;
import com.intern.donorservice.proxy.DonorDetailsProxy;
import jakarta.transaction.Transactional;

import java.util.List;

public interface DonorDetailsService {

    DonorDetailsProxy getDonorDetails(Long id , String token);

    String updateDonorDetails(DonorDetailsProxy donorDetailsProxy , String token);

    @Transactional
    String donateBlood(DonationProxy donationProxy , String token);

    List<DonationHistoryProxy> donerHistory(Long id , String token);

    Donation getById(Long id);

    String updateDonation(Donation donation);

}
