package com.intern.hospitalservice.controller;

import com.intern.hospitalservice.domain.BloodRequest;
import com.intern.hospitalservice.proxy.BloodRequestHistoryProxy;
import com.intern.hospitalservice.proxy.BloodRequestProxy;
import com.intern.hospitalservice.proxy.HospitalProxy;
import com.intern.hospitalservice.service.HospitalService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/hospital")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createHospital(@Valid @RequestBody HospitalProxy hospitalProxy , HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        return new ResponseEntity<>(hospitalService.createHospital(hospitalProxy , authorization) , HttpStatus.CREATED);
    }

    @GetMapping(value = "/profile/{id}")
    public ResponseEntity<HospitalProxy> getProfile(@PathVariable Long id , HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        return new ResponseEntity<>(hospitalService.getProfile(id , authorization) , HttpStatus.OK);
    }

    @PostMapping(value = "/request")
    public ResponseEntity<String> requestBlood(@Valid @RequestBody BloodRequestProxy bloodRequestProxy , HttpServletRequest request ){
        String authorization = request.getHeader("Authorization");
        return new ResponseEntity<>(hospitalService.hospitalBloodRequest(bloodRequestProxy , authorization) , HttpStatus.CREATED);
    }

    @GetMapping(value = "/request/history/{id}")
    public ResponseEntity<List<BloodRequestHistoryProxy>> history(@PathVariable Long id, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        return new ResponseEntity<>(hospitalService.requestHistory(id , authorization) , HttpStatus.OK);
    }

    @GetMapping(value ="/blood-request/{id}")
    public BloodRequest getBloodRequest(@PathVariable Long id){
        return hospitalService.getBloodRequest(id);
    }

    @PostMapping(value = "/save-blood-request")
    public ResponseEntity<String> savebloodRequest(@RequestBody BloodRequest bloodRequest){
        return new ResponseEntity<>(hospitalService.saveBloodRequest(bloodRequest) , HttpStatus.OK);
    }
}
