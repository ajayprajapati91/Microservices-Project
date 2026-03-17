package com.example.AdminMicroservices.service;

import com.example.AdminMicroservices.enums.BloodGroup;
import com.example.AdminMicroservices.proxy.BloodStockProxy;

import java.util.List;

public interface AdminService {


//    String approveBloodRequest(Long donationId);
//
//    byte[] downloadExcel();

//    String approveDonor(Long donorId);
//    Boolean isEligible(Donor donor);

//     String checkDonorEligibility(Long id);
//    List<UserResponseDto> getAllUsers();
    String approveBloodRequest(Long id);
//    List<BloodRequestResponseDto> getBloodRequest();

//    String addBloodStock(BloodStockProxy bloodStockProxy);

    String updateBloodStock(BloodGroup bloodGroup, BloodStockProxy bloodStockProxy);

    BloodStockProxy getStockByBloodGroup(BloodGroup bloodGroup);

    List<BloodStockProxy> getAllBloodStock();

    boolean hasSufficientStock(BloodGroup bloodGroup, Integer requiredUnits);

    void reduceStock(BloodGroup bloodGroup, Integer unitsToDeduct);


}
