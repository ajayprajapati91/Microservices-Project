package com.example.DonorMicroservices.service.implementation;

import com.example.DonorMicroservices.customizedException.DonorNotFoundException;
import com.example.DonorMicroservices.customizedException.UserNotFoundException;
import com.example.DonorMicroservices.entity.Donor;
import com.example.DonorMicroservices.enums.Status;
import com.example.DonorMicroservices.helper.MapperHelper;
import com.example.DonorMicroservices.proxy.DonateProxy;
import com.example.DonorMicroservices.proxy.DonorProxy;
import com.example.DonorMicroservices.repository.DonorRepo;
import com.example.DonorMicroservices.service.DonorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DonorServiceImpl implements DonorService {

    @Autowired
    private MapperHelper mapperHelper;

    @Autowired
    private DonorRepo donorRepo ;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Boolean validate(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String s = authorization.split("Bearer ")[1];
        String URL="http://localhost:8082/auth/verifyToken";
        return Boolean.TRUE.equals(restTemplate.postForObject(URL, s, Boolean.class));
    }

    @Override
    public String saveDonor(DonorProxy donorProxy,HttpServletRequest req) {
        if (validate(req)){
            Donor donor = mapperHelper.proxyToEntityDonor(donorProxy);
            donor.setStatus(Status.PENDING);
            donorRepo.save(donor);
            return "Donor saved successfully";
        }
        throw new DonorNotFoundException("Unauthorize access",HttpStatus.NOT_FOUND.value());
    }

    @Override
    public String donateBlood(DonateProxy donateProxy, HttpServletRequest req) {
        if (validate(req)){
            Optional<Users> byUsername = userRepo.findByUsername(username);
            Users users = byUsername.get();
            Long id = users.getId();

            Optional<Donor> byId = donorRepo.findByUserId(id);
            if (byId.isEmpty()) {
                throw new NoUserFoundException("Donor not Found",HttpStatus.NOT_FOUND.value());
            }
            System.out.println("BYID"+byId.get().getName());
            System.out.println("token name : "+username);
            if(!username.equals(byId.get().getUser().getUsername()))
            {
                throw new NoUserFoundException("Donor does not match with logged in donor",HttpStatus.NOT_FOUND.value());
            }
            Donation donation1 = mapper.dtoToEntityDonation(donation);
            Donor donor =byId.get();
            if(donor.getStatus().equals("Approved"))
            {

                donor.setLastDonationDate(LocalDate.now());
                donorRepo.save(donor);

                donation1.setDonationDate(LocalDate.now());
                donation1.setBloodGroup(donor.getBloodGroup());
                donation1.setDonor(donor);
                donationRepository.save(donation1);

                BloodStock stock = bloodStockRepository.findByBloodGroup(donor.getBloodGroup()).orElse(new BloodStock());
                stock.setLastUpdated(LocalDateTime.now());
                stock.setBloodGroup(donor.getBloodGroup());
                stock.setUnits(stock.getUnits() + donation.getQuantity());

                bloodStockRepository.save(stock);
                return "Thank you "+donor.getName()+" for donating blood";
            }
            else
            {
                return donor.getName()+" Not eligible to donate blood ";
            }
        }
        throw new DonorNotFoundException("Unauthorize access",HttpStatus.NOT_FOUND.value());
    }

    @Override
    public DonorProxy getDonor(Long donorId,HttpServletRequest req) {
        if(validate(req))
        {
            Optional<Donor> byId = donorRepo.findById(donorId);
            if (byId.isEmpty()){
                throw new DonorNotFoundException("Donor not found with this id :"+donorId,HttpStatus.NOT_FOUND.value());
            }
            Donor donor = byId.get();
            return mapperHelper.entityToProxyDonor(donor);
        }
        throw new DonorNotFoundException("Unauthorize access",HttpStatus.NOT_FOUND.value());

    }

    @Override
    public DonorProxy updateCurrentDonorProfile(Long donorId, DonorProxy donorProxy,HttpServletRequest req) {
        if (validate(req)) {
            Optional<Donor> byId = donorRepo.findById(donorId);
            if (byId.isEmpty()) {
                throw new DonorNotFoundException("Donor not found with this id :" + donorId, HttpStatus.NOT_FOUND.value());
            }
            Donor donor = byId.get();
            donor.setAge(donorProxy.getAge());
            donor.setCity(donorProxy.getCity());
            donor.setGender(donorProxy.getGender());
            donor.setBloodGroup(donorProxy.getBloodGroup());
            donor.setHeight(donorProxy.getHeight());
            donor.setName(donorProxy.getName());
            donor.setWeight(donorProxy.getWeight());
            return mapperHelper.entityToProxyDonor(donorRepo.save(donor));
        }
        throw new DonorNotFoundException("Unauthorize access", HttpStatus.NOT_FOUND.value());
    }

//
//        Users users = donor.getUsers();
//        users.setName(donorRequestProxy.getUsers().getName());
//        users.setEmail(donorRequestProxy.getUsers().getEmail());
//        users.setRole(donorRequestProxy.getUsers().getRole());
//        users.setPhoneNo(donorRequestProxy.getUsers().getPhoneNo());
//        users.setStatus(donorRequestProxy.getUsers().getStatus());
//        users.setUsername(donorRequestProxy.getUsers().getUsername());
//        users.setPassword(donorRequestProxy.getUsers().getPassword());

//        donor.setUsers(users);


//
//    @Override
//    public DonorResponseDto donorDetails(Long id, HttpServletRequest req) {
//        String authToken = req.getHeader("Authorization");
//        String token = authToken.substring(7);
//        String username = jwtUtil.extractUsername(token);
//
//        Optional<Donor> byId = donorRepo.findById(id);
//        if (byId.isEmpty()) {
//            throw new NoUserFoundException("Donor not Found",HttpStatus.NOT_FOUND.value());
//        }
//        if(!username.equals(byId.get().getUser().getUsername()))
//        {
//            throw new NoUserFoundException("Donor does not match with logged in donor",HttpStatus.NOT_FOUND.value());
//        }
//        return mapper.entityToDtoDonor(byId.get());
//    }
//
//    @Override
//    public List<DonationResponseDto> donorHistory(Long id, HttpServletRequest req) {
//        String authToken = req.getHeader("Authorization");
//        String token = authToken.substring(7);
//        String username = jwtUtil.extractUsername(token);
//        List<Donation> byDonorId = donationRepository.findByDonor_DonorId(id);
//        Optional<Donor> byId = donorRepo.findById(id);
//        if(!username.equals(byId.get().getUser().getUsername()))
//        {
//            throw new NoUserFoundException("Donor does not match with logged in donor",HttpStatus.NOT_FOUND.value());
//        }
//        if(byDonorId.isEmpty()) throw new NoUserFoundException("No donor found ", HttpStatus.NOT_FOUND.value());
//        List<DonationResponseDto> list = byDonorId.stream().map(d -> mapper.entityToDtoDonation(d)).toList();
//        return list;
//    }
//
//    }

//    @Override
//    public List<DonationResponseProxy1> getDonorHistory(Long donorId) {
//        List<Donation> byDonorId = donationRepo.findByDonor_DonorId(donorId);
//        if(byDonorId.isEmpty()){
//            throw new DonorNotFoundException("Donor not found with this id :"+donorId,HttpStatus.NOT_FOUND.value());
//        }
//
//        List<DonationResponseProxy1> donorList = byDonorId.stream().map(donation -> mapperHelper.entityToProxyDonation(donation)).toList();
//        return donorList;
//    }


}
