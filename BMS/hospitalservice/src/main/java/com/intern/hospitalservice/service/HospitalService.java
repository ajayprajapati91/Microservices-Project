package com.intern.hospitalservice.service;

import com.intern.hospitalservice.domain.BloodRequest;
import com.intern.hospitalservice.proxy.BloodRequestHistoryProxy;
import com.intern.hospitalservice.proxy.BloodRequestProxy;
import com.intern.hospitalservice.proxy.HospitalProxy;

import java.util.List;

public interface HospitalService {
    String createHospital(HospitalProxy hospitalProxy , String token);

    HospitalProxy getProfile(Long id , String token);

    String hospitalBloodRequest(BloodRequestProxy bloodRequestProxy , String token);

    List<BloodRequestHistoryProxy> requestHistory(Long id , String token);

    BloodRequest getBloodRequest(Long id);

    String saveBloodRequest(BloodRequest request);
}
