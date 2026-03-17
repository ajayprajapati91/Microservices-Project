package com.example.DonorMicroservices.service;

import com.example.DonorMicroservices.proxy.DonateProxy;
import com.example.DonorMicroservices.proxy.DonorProxy;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DonorService {

    String saveDonor(DonorProxy donorProxy);
    String donateBlood(DonateProxy donateProxy, HttpServletRequest req);

    DonorProxy getDonor(Long donorId);

    DonorProxy updateCurrentDonorProfile(Long donorId, DonorProxy donorProxy);
//    DonorResponseDto donorDetails(Long id,HttpServletRequest req);
//
//    List<DonationResponseDto> donorHistory(Long id,HttpServletRequest req);


}
