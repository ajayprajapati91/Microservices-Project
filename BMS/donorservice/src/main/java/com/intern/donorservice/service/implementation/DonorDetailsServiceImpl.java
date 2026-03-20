package com.intern.donorservice.service.implementation;

import com.intern.donorservice.domain.Donation;
import com.intern.donorservice.domain.DonorDetails;
import com.intern.donorservice.domain.Users;
import com.intern.donorservice.proxy.DonationHistoryProxy;
import com.intern.donorservice.proxy.DonationProxy;
import com.intern.donorservice.proxy.DonorDetailsProxy;
import com.intern.donorservice.proxy.TokenRoleProxy;
import com.intern.donorservice.repository.DonationRepo;
import com.intern.donorservice.repository.DonorDetailsRepo;
import com.intern.donorservice.service.DonorDetailsService;
import com.intern.donorservice.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DonorDetailsServiceImpl implements DonorDetailsService {
    @Autowired
    private DonorDetailsRepo donorDetailsRepo;

    @Autowired
    private DonationRepo donationRepo;

    @Autowired
    private MapperHelper mapperHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DonorDetailsProxy getDonorDetails(Long id , String token) {

        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .role("DONOR")
                .token(token)
                .build();

        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }

        Optional<DonorDetails> donor = donorDetailsRepo.findById(id);
        return mapperHelper.map(donor, DonorDetailsProxy.class);
    }

    @Override
    public String updateDonorDetails(DonorDetailsProxy donorDetailsProxy , String token) {
        System.out.println("Token----->"+token);

        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .role("DONOR")
                .token(token)
                .build();

        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }

        Users users = restTemplate.getForObject("http://localhost:8090/gateway/v1/auth/user/"+donorDetailsProxy.getUserId() , Users.class);
        if(!users.getRole().equalsIgnoreCase("DONOR")){
            throw new RuntimeException("User not found with DONOR role");
        }

        Optional<DonorDetails> optionalDonorDetails = donorDetailsRepo.findByUserId(donorDetailsProxy.getUserId());
        DonorDetails donor = mapperHelper.map(donorDetailsProxy, DonorDetails.class);
        DonorDetails save = new DonorDetails();

        if(optionalDonorDetails.isPresent()){
            DonorDetails donorDetails = optionalDonorDetails.get();
            donor.setId(donorDetails.getId());
            save = donorDetailsRepo.save(donor);
        }else{
            save = donorDetailsRepo.save(mapperHelper.map(donorDetailsProxy , DonorDetails.class));
        }
        return "updated successfully";
    }

    @Override
    public String donateBlood(DonationProxy donationProxy , String token) {
        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .role("DONOR")
                .token(token)
                .build();
        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }
        Long id = donationProxy.getDonorDetails().getId();
        DonorDetails donorDetails = donorDetailsRepo.findById(id).orElseThrow(() -> new RuntimeException("Donor is not found"));
        Donation donation = mapperHelper.map(donationProxy, Donation.class);
        donation.setDonationDate(LocalDate.now());
        donation.setRemarks("PENDING");
        donation.setDonorDetails(donorDetails);
        return donationRepo.save(donation).toString();
    }

    @Override
    public List<DonationHistoryProxy>  donerHistory(Long id , String token) {
        TokenRoleProxy tokenRoleProxy = TokenRoleProxy.builder()
                .role("DONOR")
                .token(token)
                .build();
        if(!restTemplate.postForObject("http://localhost:8090/gateway/v1/auth/validate", tokenRoleProxy, Boolean.class)){
            throw new RuntimeException("UnAuthorized");
        }
        DonorDetails donorDetails = donorDetailsRepo.findById(id).orElseThrow(() -> new RuntimeException("Donor not found"));
        List<Donation> donations = donationRepo.findByDonorDetails(donorDetails).orElseThrow(() -> new RuntimeException("No DonerDetails found with id " + id));

        List<DonationHistoryProxy> list = new ArrayList<>();
        for(Donation donation:donations){
            DonationHistoryProxy donationHistoryProxy = DonationHistoryProxy.builder()
                    .id(donation.getId())
                    .donationDate(donation.getDonationDate())
                    .quantity(donation.getQuantity())
                    .remarks(donation.getRemarks())
                    .build();
            list.add(donationHistoryProxy);
        }
        return list;
    }

    @Override
    public Donation getById(Long id) {
        return donationRepo.findById(id).orElseThrow(() -> new RuntimeException("Donation is not available"));
    }

    @Override
    public String updateDonation(Donation donation) {
        donationRepo.save(donation);
        return "updated successfully";
    }
}
