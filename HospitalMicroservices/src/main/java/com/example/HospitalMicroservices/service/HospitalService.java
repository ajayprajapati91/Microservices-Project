package com.example.HospitalMicroservices.service;

import com.example.HospitalMicroservices.proxy.HospitalProxy;
import jakarta.servlet.http.HttpServletRequest;

public interface HospitalService {
    HospitalProxy registerHospital(HospitalProxy hospitalProxy);

//    String requestBlood(BloodRequestRequestDto dto, HttpServletRequest req);
//    HospitalResponseDto getHospital(Long id, HttpServletRequest req);
//    String updateHospital(Long id,HospitalResponseDto dto);

//    HospitalProxy getCurrentHospitalProfile(Long hospitalId);
}
