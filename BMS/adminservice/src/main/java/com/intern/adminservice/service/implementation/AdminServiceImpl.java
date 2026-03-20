package com.intern.adminservice.service.implementation;

import com.intern.adminservice.domain.BloodStock;
import com.intern.adminservice.proxy.*;
import com.intern.adminservice.repositry.BloodStockRepo;
import com.intern.adminservice.service.AdminService;
import com.intern.adminservice.utility.MapperHelper;
import com.intern.adminservice.utility.StorageHelper;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

//    @Autowired
//    private UserRepo userRepo;

    @Autowired
    private MapperHelper mapperHelper;

    @Autowired
    private BloodStockRepo bloodStockRepo;

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private BloodRequestRepo bloodRequestRepo;

//    @Autowired
//    private DonationRepo donationRepo;

    @Override
    public List<Users> getAllUser(String token) {
//        List<Users> all = userRepo.findAll();
//        return all.stream().map(users -> mapperHelper.map(users , UserProxy.class)).toList();
//        return List.of();
//
//            List<Users> forObject = restTemplate.getForObject("http://localhost:8090/gateway/v1/auth/getAll", new ParameterizedTypeReference<List<Users>>(){});
//            System.out.println(forObject);

        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .role("ADMIN")
                .token(token)
                .build();

        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }

        ResponseEntity<List<Users>> response =
                restTemplate.exchange(
                        "http://localhost:8090/gateway/v1/auth/getAll",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Users>>() {}
                );

        List<Users> users = response.getBody();
        System.out.println(users);
        return users;
//        return list.stream().map(u -> mapperHelper.map(u , UserProxy.class)).toList();
    }

    @Override
    public String approve(Long id , String token) {

        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .token(token)
                .role("ADMIN")
                .build();

        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }
        System.out.println("Approved");
        ResponseEntity<Donation> response= restTemplate.exchange(
                "http://localhost:8090/gateway/v1/donor/donation/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Donation>() {}
        );

        Donation donation = response.getBody();
        String bloodGroup = donation.getDonorDetails().getBloodGroup();

        Optional<BloodStock> optBloodStock = bloodStockRepo.findByBloodGroup(bloodGroup);
        BloodStock bloodStock = null;
        if (!optBloodStock.isPresent()){
            bloodStock = BloodStock.builder()
                    .bloodGroup(bloodGroup)
                    .lastUpdated(LocalDateTime.now())
                    .unitsAvailable(0.0)
                    .build();

            bloodStockRepo.save(bloodStock);
        }

        if(!donation.getRemarks().equalsIgnoreCase("APPROVE")){
            donation.setRemarks("APPROVE");
            bloodStock.setUnitsAvailable(bloodStock.getUnitsAvailable() + donation.getQuantity());

            bloodStockRepo.save(bloodStock);
//            donationRepo.save(donation);
            restTemplate.postForObject("http://localhost:8090/gateway/v1/donor/update",donation,String.class);
            return "Approved Successfully";
        }else {
            throw  new RuntimeException("Request Already approve with id "+id);
        }
    }

    @Override
    public String approveBloodRequest(Long id ,String token) {
//        BloodRequest bloodRequest = bloodRequestRepo.findById(id).orElseThrow(() -> new RuntimeException("There is no Blood request available with perticular id"));
//        String bloodGroup = bloodRequest.getBloodGroup();
//        BloodStock bloodStock = bloodStockRepo.findByBloodGroup(bloodGroup).orElseThrow(() -> new BloodGroupNotAvailableException("There is no blood available with " + bloodGroup + " blood group"));
//        Double requestedQuantity = bloodRequest.getQuantity();
//        Double unitsAvailable = bloodStock.getUnitsAvailable();

        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .token(token)
                .role("ADMIN")
                .build();

        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }

//        ResponseEntity<BloodRequest> request = restTemplate.exchange(
//                "http://localhost:8090/gateway/v1/hospital/blood-request/"+id,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<BloodRequest>() {}
//        );

        BloodRequest bloodRequest = restTemplate.getForObject("http://localhost:8090/gateway/v1/hospital/blood-request/"+id, BloodRequest.class);

        String bloodGroup = bloodRequest.getBloodGroup();
        BloodStock bloodStock = bloodStockRepo.findByBloodGroup(bloodGroup).orElseThrow(() -> new RuntimeException("No BloodStock found with blood group " + bloodGroup));
        Double requestedQuantity = bloodRequest.getQuantity();
        Double unitsAvailable = bloodStock.getUnitsAvailable();


        if(bloodRequest.getStatus().equalsIgnoreCase("APPROVE")){
            throw new RuntimeException("Blood request Already Apporved");
        }
        if(unitsAvailable > requestedQuantity ){
            bloodStock.setUnitsAvailable(unitsAvailable - requestedQuantity);
            bloodRequest.setStatus("APPROVE");
            HttpEntity httpEntity = new HttpEntity(bloodRequest);
//            bloodRequestRepo.save(bloodRequest);
            restTemplate.exchange(
                    "http://localhost:8090/gateway/v1/hospital/save-blood-request",
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<String>() {}
            );
            bloodStock.setLastUpdated(LocalDateTime.now());
            bloodStockRepo.save(bloodStock);
            return "Approved Successfully";
        }

        bloodRequest.setStatus("REJECTED");
        HttpEntity httpEntity = new HttpEntity(bloodRequest);
        restTemplate.exchange(
                "http://localhost:8090/gateway/v1/hospital/save-blood-request",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<String>() {}
        );
        throw new RuntimeException("There is no Sufficient blood quantity available for "+bloodGroup+" bloodgroup");
    }

    @Override
    public String addBloodStock(BloodStockProxy bloodStockProxy) {
        BloodStock bloodStock = mapperHelper.map(bloodStockProxy, BloodStock.class);
        bloodStock.setLastUpdated(LocalDateTime.now());
        bloodStock.setUnitsAvailable(0.0);
        return bloodStockRepo.save(bloodStock).toString();
    }

    @Override
    public byte[] getExcel(String token) {
        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .role("ADMIN")
                .token(token)
                .build();
        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }
        List<BloodStock> list = bloodStockRepo.findAll();
        return StorageHelper.downloadExcellFromListOfBloodStock(list);
    }

    @Override
    public BloodStock findbyBloodGroup(String bloodGroup) {
        return bloodStockRepo.findByBloodGroup(bloodGroup).orElseThrow(() -> new RuntimeException("Blood group not found"));
    }

    //    @Override
//    public byte[] getUserReport() {
//        List<Users> list = userRepo.findAll();
//        return StorageHelper.downloadExcellFromListOfUsers(list);
//    }
}
