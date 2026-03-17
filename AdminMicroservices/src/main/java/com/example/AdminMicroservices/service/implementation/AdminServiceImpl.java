package com.example.AdminMicroservices.service.implementation;

import com.example.AdminMicroservices.customizedException.BloodStockNotFoundException;
import com.example.AdminMicroservices.entity.BloodStock;
import com.example.AdminMicroservices.enums.BloodGroup;
import com.example.AdminMicroservices.enums.Status;
import com.example.AdminMicroservices.helper.MapperHelper;
import com.example.AdminMicroservices.proxy.BloodStockProxy;
import com.example.AdminMicroservices.repository.BloodStockRepo;
import com.example.AdminMicroservices.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private MapperHelper mapperHelper;

    @Autowired
    private BloodStockRepo bloodStockRepo;

    @Autowired
    private RestTemplate restTemplate;


//    @Override
//    public String checkDonorEligibility(Long id) {
//        Optional<Donor> byId = donorRepo.findById(id);
//        if(byId.isEmpty()) throw new NoUserFoundException("No Donor found ", HttpStatus.NOT_FOUND.value());
//        Donor donor = byId.get();
//        double bmi = donor.getWeight() / (donor.getHeight() * donor.getHeight());
//        if(bmi>18)
//            donor.setStatus("Approved");
//        else {
//            donor.setStatus("Rejected");
//        }
//        donorRepo.save(donor);
//        return "Donor eligibility checked";
//    }

//    @Override
//    public List<UserResponseDto> getAllUsers() {
//        List<Users> users = userRepo.findAll();
//        if(users.isEmpty()) throw new NoUserFoundException("No donor found ", HttpStatus.NOT_FOUND.value());
//        List<UserResponseDto> list = users.stream().map(d -> mapper.entityToDtoUser(d)).toList();
//        return list;
//    }

    @Override
    public String approveBloodRequest(Long id) {
//        Optional<BloodRequest> byId = bloodReqRepo.findById(id);
//        if(byId.isEmpty())throw new NoUserFoundException("No Hospital found ", HttpStatus.NOT_FOUND.value());
//        BloodRequest bloodRequest = byId.get();
//        if(bloodRequest.getStatus().equals("Pending"))
//        {
//            Optional<BloodStock> byBloodGroup = bloodStockRepository.findByBloodGroup(bloodRequest.getBloodGroup());
//            if(byBloodGroup.isEmpty())
//            {
//                bloodRequest.setStatus("Rejected");
//                bloodReqRepo.save(bloodRequest);
//                throw new NoUserFoundException("No blood Group found", HttpStatus.NOT_FOUND.value());
//            }
//            BloodStock bloodStock = byBloodGroup.get();
//            if(bloodRequest.getQuantity()<=bloodStock.getUnits())
//            {
//                bloodRequest.setStatus("Approved");
//                bloodStock.setUnits(bloodStock.getUnits()-bloodRequest.getQuantity());
//                bloodStockRepository.save(bloodStock);
//            }
//            else
//                bloodRequest.setStatus("Rejected");
//        }
//        bloodReqRepo.save(bloodRequest);

        return "Blood Request status checked";

    }

//    @Override
//    public List<BloodRequestResponseDto> getBloodRequest() {
//        List<BloodRequest> all = bloodReqRepo.findByPending("Pending");
//        return all.stream().map(b->mapper.entityToDtoBloodRequest(b)).toList();
//
//    }

//    @Override
//    public String addBloodStock(BloodStockProxy bloodStockRequestProxy) {
//
//        BloodStock stock = bloodStockRepository.findByBloodGroup(dto.getBloodGroup()).orElse(new BloodStock());
//        stock.setBloodGroup(dto.getBloodGroup());
//        stock.setUnits(stock.getUnits() + dto.getUnits());
//        stock.setLastUpdated(LocalDateTime.now());
//        bloodStockRepository.save(stock);
//        return "Blood stock updated successfully";
//    }

    @Override
    public String updateBloodStock(BloodGroup bloodGroup, BloodStockProxy bloodStockProxy) {

        BloodStock existingStock = bloodStockRepo.findByBloodGroup(bloodGroup)
                .orElseThrow(() -> new RuntimeException("Blood stock not found for blood group " + bloodGroup));

        Integer newUnits = existingStock.getUnitsAvailable() + bloodStockProxy.getUnitsAvailable();

        existingStock.setUnitsAvailable(newUnits);
        existingStock.setLastUpdated(LocalDateTime.now());

        bloodStockRepo.save(existingStock);

        return "Blood stock updated successfully for blood group " + bloodGroup;
    }

    @Override
    public BloodStockProxy getStockByBloodGroup(BloodGroup bloodGroup) {
        Optional<BloodStock> byBloodGroup = bloodStockRepo.findByBloodGroup(bloodGroup);

        if(byBloodGroup.isEmpty()){
            throw new BloodStockNotFoundException("Blood stock not found for blood group " + bloodGroup,HttpStatus.NOT_FOUND.value());
        }
        return mapperHelper.entityToProxyBloodStock(byBloodGroup.get());
    }

    @Override
    public List<BloodStockProxy> getAllBloodStock() {
        List<BloodStock> bloodStockList = bloodStockRepo.findAll();
        return bloodStockList.stream().map(b->mapperHelper.entityToProxyBloodStock(b)).toList();
    }

    @Override
    public boolean hasSufficientStock(BloodGroup bloodGroup, Integer requiredUnits) {
        Optional<BloodStock> byBloodGroup = bloodStockRepo.findByBloodGroup(bloodGroup);
        if (byBloodGroup.isEmpty()) {
            return false;
        }
        return byBloodGroup.get().getUnitsAvailable() >= requiredUnits;
    }

    @Override
    public void reduceStock(BloodGroup bloodGroup, Integer unitsToDeduct) {
        Optional<BloodStock> byBloodGroup = bloodStockRepo.findByBloodGroup(bloodGroup);

        if (byBloodGroup.isEmpty()) {
            throw new BloodStockNotFoundException("Blood stock not found for blood group " + bloodGroup,HttpStatus.NOT_FOUND.value());
        }
        BloodStock bloodStock = byBloodGroup.get();
        Integer remaining = bloodStock.getUnitsAvailable() - unitsToDeduct;
        if (remaining < 0) {
            remaining = 0;
        }
        bloodStock.setUnitsAvailable(remaining);
        bloodStock.setLastUpdated(LocalDateTime.now());
        bloodStockRepo.save(bloodStock);
    }

    }


//    @Override
//    public String approveBloodRequest(Long donationId) {
//        Optional<Donation> byId = donationRepo.findById(donationId);
//
//        if (byId.isEmpty()){
//            throw new UserNotFoundException("User not found", HttpStatus.NO_CONTENT.value());
//        }
//        Donation donation = byId.get();
//
//        if (donation.getStatus().equals(StatusEnum.PENDING)){
//
//            if(isEligible(Donor donor))
//            Optional<BloodStock> byBloodGroup = bloodStockRepo.findByBloodGroup(donation.getBloodGroup());
//            if (byBloodGroup.isEmpty()){
//                throw new DonorNotFoundException("Blood Group  not found", HttpStatus.NO_CONTENT.value());
//            }
//            BloodStock bloodStock = byBloodGroup.get();
//            if (donation.getQuantity() <= bloodStock.getUnitsAvailable()){
//                donation.setStatus(StatusEnum.ACCEPTED);
//                bloodStock.setUnitsAvailable(bloodStock.getUnitsAvailable()-donation.getQuantity());
//                bloodStockRepo.save(bloodStock);
//
//            }
//            else{
//                donation.setStatus(StatusEnum.REJECTED);
//            }
//            donationRepo.save(donation);
////        }
//        return "Blood request status has been checked successfully";
//    }
//
//    @Override
//    public byte[] downloadExcel() {
//
//        return StorageHelper.getExcelSheetFromListOfEmployee(donationRepo.findAll());
//    }

//    @Override
//    public String approveDonor(Long donorId) {
//        Optional<Donor> byId = donorRepo.findById(donorId);
//        if (byId.isEmpty()){
//            throw new UserNotFoundException("User not found", HttpStatus.NO_CONTENT.value());
//        }
//        Donor donor = byId.get();
//
//        if (!isEligible(donor)){
//            throw new DonorNotFoundException("Donor not found",HttpStatus.NO_CONTENT.value());
//        }
//
//        if (donor.getStatus() == Status.PENDING){
//            donor.setStatus(Status.ACCEPTED);
//
//        }
//        donorRepo.save(donor);
//
//        return "Donor approved and Stock updated.";
//    }

//    @Override
//    public Boolean isEligible(Donor donor) {
//
//        if (donor.getLastDonationDate() == null) {
//            return donor.getAge() >= 18 && donor.getAge() <= 65;
//        }
//
//        return donor.getAge() >= 18 &&
//                donor.getAge() <= 65;
//    }


