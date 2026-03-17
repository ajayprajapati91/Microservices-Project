package com.example.AdminMicroservices.helper;

import com.example.AdminMicroservices.entity.Admin;
import com.example.AdminMicroservices.entity.BloodStock;
import com.example.AdminMicroservices.proxy.Adminproxy;
import com.example.AdminMicroservices.proxy.BloodStockProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class MapperHelper {
    @Autowired
    private ObjectMapper objectMapper;

    public Admin proxyToEntityDonation(Adminproxy adminproxy){
        return objectMapper.convertValue(adminproxy, Admin.class);
    }
    public Adminproxy entityToProxyDonation(Admin admin){
        return objectMapper.convertValue(admin, Adminproxy.class);
    }

    public BloodStock proxyToEntityBloodStock(BloodStockProxy bloodStockProxy){
        return objectMapper.convertValue(bloodStockProxy,BloodStock.class);
    }
    public BloodStockProxy entityToProxyBloodStock(BloodStock bloodStock){
        return objectMapper.convertValue(bloodStock, BloodStockProxy.class);
    }
}
