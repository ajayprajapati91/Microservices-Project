package com.example.DonorMicroservices.controller;

import com.example.DonorMicroservices.proxy.DonorProxy;
import com.example.DonorMicroservices.service.DonorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/donor")
public class DonorController {


    @Autowired
    private DonorService donorService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/validate")
    public Boolean validate(HttpServletRequest req){
        return donorService.validate(req);
    }

    @PostMapping("/save-donor")
    public ResponseEntity<String> saveDonor(@RequestBody DonorProxy donorProxy) {
        return new ResponseEntity<>(donorService.saveDonor(donorProxy), HttpStatus.CREATED);
    }

    @GetMapping("/get-donor/{donorId}")
    public ResponseEntity<DonorProxy> getDonor( @PathVariable Long donorId){
        return new ResponseEntity<>(donorService.getDonor(donorId),HttpStatus.OK);
    }

    @PutMapping("/update-donorProfile/{donorId}")
    public ResponseEntity<DonorProxy> update(@PathVariable Long donorId, @RequestBody DonorProxy donorProxy){
        return new ResponseEntity<>(donorService.updateCurrentDonorProfile(donorId, donorProxy),HttpStatus.OK);
    }

//    @GetMapping("/history/{id}")
//    public ResponseEntity<List<DonationResponseDto>> history(@PathVariable Long id,HttpServletRequest req)
//    {
//        return new ResponseEntity<>(donorService.donorHistory(id,req),HttpStatus.OK);
//    }

//    @PostMapping("/donate-blood")
//    public ResponseEntity<String> donateBlood(@RequestBody DonationRequestProxy donationRequestProxy){
//        return new ResponseEntity<>(donorService.donateBlood(donationRequestProxy),HttpStatus.OK);
//    }
//@GetMapping("/profile/{id}")
//public ResponseEntity<DonorResponseDto> donorProfile(@PathVariable Long id, HttpServletRequest req)
//{
//    return new ResponseEntity<>(donorService.donorDetails(id,req),HttpStatus.OK);
//}
//
//    @PutMapping("/profile/{id}")
//    public ResponseEntity<String> donateBlood(@PathVariable Long id,@Valid @RequestBody DonorRequestDto dto)
//    {
//        return new ResponseEntity<>(donorService.updateDonor(id,dto),HttpStatus.OK);
//    }

}
