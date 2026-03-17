package com.example.HospitalMicroservices.helper;

import com.example.HospitalMicroservices.entity.Hospital;
import com.example.HospitalMicroservices.proxy.HospitalProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class MapperHelper {

    @Autowired
    private ObjectMapper objectMapper;

    public Hospital proxyToEntityHospital(HospitalProxy hospitalProxy){
        return objectMapper.convertValue(hospitalProxy, Hospital.class);
    }
    public HospitalProxy entityToProxyHospital(Hospital hospital){
        return objectMapper.convertValue(hospital, HospitalProxy.class);
    }
}
