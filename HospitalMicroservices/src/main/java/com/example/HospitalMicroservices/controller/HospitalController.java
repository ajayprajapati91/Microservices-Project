package com.example.HospitalMicroservices.controller;

import com.example.HospitalMicroservices.proxy.HospitalProxy;
import com.example.HospitalMicroservices.repository.HospitalRepo;
import com.example.HospitalMicroservices.service.HospitalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/register")
    public ResponseEntity<HospitalProxy> registerHospital(@RequestBody HospitalProxy hospitalProxy, HttpServletRequest req){

        return ResponseEntity.ok(hospitalService.registerHospital(hospitalProxy,req));
    }

//    @PostMapping("/request")
//    public ResponseEntity<String> requestBlood(  @RequestBody BloodRequestRequestProxy bloodRequestRequestProxy){
//
//        return ResponseEntity.ok(hospitalService.requestBlood(bloodRequestRequestProxy));
//    }

//    @GetMapping("/profile/{hospitalId}")
//    public ResponseEntity<HospitalProxy> getCurrentHospitalProfile(@PathVariable Long hospitalId){
//
//        return ResponseEntity.ok(hospitalService.getCurrentHospitalProfile(hospitalId));
//    }

//    @PutMapping("update/{id}")
//    public ResponseEntity<String>  updateHospital(@PathVariable Long id,@RequestBody HospitalRepo hospitalRepo)
//    {
//        return new ResponseEntity<>(hospitalService.updateHospital(id,hospitalRepo), HttpStatus.OK);
//    }
}
