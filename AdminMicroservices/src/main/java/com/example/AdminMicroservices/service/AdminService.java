package com.example.AdminMicroservices.service;

import com.example.AdminMicroservices.enums.BloodGroup;
import com.example.AdminMicroservices.proxy.BloodStockProxy;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AdminService {
    Boolean validate(HttpServletRequest request);


//    String approveBloodRequest(Long donationId);
//
//    byte[] downloadExcel();

//    String approveDonor(Long donorId);
//    Boolean isEligible(Donor donor);

     String checkDonorEligibility(Long id,HttpServletRequest req);
//    List<UserResponseDto> getAllUsers();
    String approveBloodRequest(Long id);
//    List<BloodRequestResponseDto> getBloodRequest();

//    String addBloodStock(BloodStockProxy bloodStockProxy);

    String updateBloodStock(BloodGroup bloodGroup, BloodStockProxy bloodStockProxy,HttpServletRequest req);

    BloodStockProxy getStockByBloodGroup(BloodGroup bloodGroup,HttpServletRequest req);

    List<BloodStockProxy> getAllBloodStock(HttpServletRequest req);

    boolean hasSufficientStock(BloodGroup bloodGroup, Integer requiredUnits);

    void reduceStock(BloodGroup bloodGroup, Integer unitsToDeduct);


}
