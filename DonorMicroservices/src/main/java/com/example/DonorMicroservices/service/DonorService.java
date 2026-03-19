package com.example.DonorMicroservices.service;

import com.example.DonorMicroservices.proxy.DonateProxy;
import com.example.DonorMicroservices.proxy.DonorProxy;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DonorService {

    String saveDonor(DonorProxy donorProxy,HttpServletRequest req);
    String donateBlood(DonateProxy donateProxy, HttpServletRequest req);
    Boolean validate(HttpServletRequest request);

    DonorProxy getDonor(Long donorId,HttpServletRequest req);

    DonorProxy updateCurrentDonorProfile(Long donorId, DonorProxy donorProxy,HttpServletRequest req);
//    DonorResponseDto donorDetails(Long id,HttpServletRequest req);
//
//    List<DonationResponseDto> donorHistory(Long id,HttpServletRequest req);


}
