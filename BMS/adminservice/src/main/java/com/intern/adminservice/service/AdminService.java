package com.intern.adminservice.service;

import com.intern.adminservice.domain.BloodStock;
import com.intern.adminservice.proxy.BloodStockProxy;
import com.intern.adminservice.proxy.RoleProxy;
import com.intern.adminservice.proxy.UserProxy;
import com.intern.adminservice.proxy.Users;

import java.util.List;

public interface AdminService {
    List<Users> getAllUser(String token);
    String approve(Long id , String token);
    String addBloodStock(BloodStockProxy bloodStockProxy );
    String approveBloodRequest(Long id , String token);
    byte[] getExcel(String token);
    BloodStock findbyBloodGroup(String bloodGroup);
//    byte[] getUserReport();
}
