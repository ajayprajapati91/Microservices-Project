package com.intern.donorservice.controller;

import com.intern.donorservice.domain.Donation;
import com.intern.donorservice.proxy.DonationHistoryProxy;
import com.intern.donorservice.proxy.DonationProxy;
import com.intern.donorservice.proxy.DonorDetailsProxy;
import com.intern.donorservice.repository.DonationRepo;
import com.intern.donorservice.service.DonorDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/donor")
public class DonorDetailsController {
    @Autowired
    private DonorDetailsService donorDetailsService;
    @Autowired
    private DonationRepo donationRepo;

    @GetMapping(value = "/profile/{id}")
    public ResponseEntity<DonorDetailsProxy> showProfile(@PathVariable Long id , HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        return new ResponseEntity<>(donorDetailsService.getDonorDetails(id , authorization), HttpStatus.OK);
    }

    @PutMapping(value = "/profile")
    public ResponseEntity<String> updateDonorDetails(@Valid @RequestBody DonorDetailsProxy donorDetailsProxy , HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        return new ResponseEntity<>(donorDetailsService.updateDonorDetails(donorDetailsProxy , authorization) , HttpStatus.OK);
    }


    @PostMapping(value ="/donate")
    public ResponseEntity<String> donateBlood(@Valid @RequestBody DonationProxy donation , HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        return new ResponseEntity<>(donorDetailsService.donateBlood(donation , authorization) , HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<DonationHistoryProxy>> donerHistory(@PathVariable Long id , HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        return new ResponseEntity<>(donorDetailsService.donerHistory(id , authorization) , HttpStatus.OK);
    }

    @GetMapping(value = "/donation/{id}")
    public ResponseEntity<Donation> getDonation(@PathVariable Long id){
        return new ResponseEntity<>(donorDetailsService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<String> updateDonation(@RequestBody Donation donation){
        return new ResponseEntity<>(donorDetailsService.updateDonation(donation) , HttpStatus.OK);
    }
}
