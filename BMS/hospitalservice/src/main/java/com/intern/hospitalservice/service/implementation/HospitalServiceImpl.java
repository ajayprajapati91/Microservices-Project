package com.intern.hospitalservice.service.implementation;

import com.intern.hospitalservice.domain.BloodRequest;
import com.intern.hospitalservice.domain.Hospital;
import com.intern.hospitalservice.domain.Users;
import com.intern.hospitalservice.proxy.*;
import com.intern.hospitalservice.repository.BloodRequestRepo;
import com.intern.hospitalservice.repository.HospitalRepo;
import com.intern.hospitalservice.service.HospitalService;
import com.intern.hospitalservice.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepo hospitalRepo;

    @Autowired
    private MapperHelper mapperHelper;

    @Autowired
    private BloodRequestRepo bloodRequestRepo;

//    @Autowired
//    private BloodStockRepo bloodStockRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String createHospital(HospitalProxy hospitalProxy , String token) {
        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .role("HOSPITAL")
                .token(token)
                .build();
        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }

        Hospital map = mapperHelper.map(hospitalProxy, Hospital.class);
        Users users = restTemplate.getForObject("http://localhost:8090/gateway/v1/auth/user/"+hospitalProxy.getUserId() , Users.class);
        if(users.getRole().equalsIgnoreCase("HOSPITAL")){
            users.setPassword(passwordEncoder.encode(users.getPassword()));
        }
        else
            throw new RuntimeException("You not select the required role user");
        hospitalRepo.save(map);
        return "Created Successfully";
    }

    @Override
    public HospitalProxy getProfile(Long id , String token) {
        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .role("HOSPITAL")
                .token(token)
                .build();
        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }
        Hospital hospital= hospitalRepo.findById(id).orElseThrow(() -> new RuntimeException("Hospital not found exception"));
        return mapperHelper.map(hospital , HospitalProxy.class);
    }

    @Override
    public String hospitalBloodRequest(BloodRequestProxy bloodRequestProxy, String token) {
        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .role("HOSPITAL")
                .token(token)
                .build();
        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }
        BloodRequest bloodRequest = mapperHelper.map(bloodRequestProxy, BloodRequest.class);
        Hospital hospital = hospitalRepo.findById(bloodRequest.getHospital().getId()).orElseThrow(() -> new RuntimeException("Hospital Not Found"));
        String bloodGroup = bloodRequest.getBloodGroup();

        BloodStock bloodStock = restTemplate.getForObject("http://localhost:8090/gateway/v1/admin/"+bloodGroup , BloodStock.class);
        Double unitsAvailable = bloodStock.getUnitsAvailable();

        if(unitsAvailable > bloodRequest.getQuantity()){
            bloodRequest.setHospital(hospital);
            bloodRequest.setStatus("PENDING");
            bloodRequest.setRequestDate(LocalDate.now());
            return bloodRequestRepo.save(bloodRequest).toString();
        }

        throw new RuntimeException("There not sufficient blood available");
    }

    @Override
    public List<BloodRequestHistoryProxy> requestHistory(Long id, String token) {
        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .role("HOSPITAL")
                .token(token)
                .build();
        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }
        Hospital hospital = hospitalRepo.findById(id).orElseThrow(() -> new RuntimeException("Hospital Not Found"));
        List<BloodRequest> requests = bloodRequestRepo.findByHospital(hospital);
        List<BloodRequestHistoryProxy> list = new ArrayList<>();

        for(BloodRequest request : requests){
            BloodRequestHistoryProxy bloodRequestHistoryProxy = BloodRequestHistoryProxy.builder()
                    .id(request.getId())
                    .requestDate(request.getRequestDate())
                    .bloodGroup(request.getBloodGroup())
                    .status(request.getStatus())
                    .quantity(request.getQuantity())
                    .build();

            list.add(bloodRequestHistoryProxy);
        }

        return list;
    }

    @Override
    public BloodRequest getBloodRequest(Long id) {
        return bloodRequestRepo.findById(id).orElseThrow(() ->new RuntimeException("No Blood request available with id "+id));
    }

    @Override
    public String saveBloodRequest(BloodRequest request) {
        bloodRequestRepo.save(request);
        return "Save successfully";
    }
}
