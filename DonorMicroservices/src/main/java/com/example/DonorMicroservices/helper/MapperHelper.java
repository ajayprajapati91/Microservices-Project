package com.example.DonorMicroservices.helper;

import com.example.DonorMicroservices.entity.Donor;
import com.example.DonorMicroservices.proxy.DonorProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class MapperHelper {

    @Autowired
    private ObjectMapper objectMapper;

    public Donor proxyToEntityDonor(DonorProxy donorProxy){
        return objectMapper.convertValue(donorProxy,Donor.class);
    }
    public DonorProxy entityToProxyDonor(Donor donor){
        return objectMapper.convertValue(donor, DonorProxy.class);
    }
}
