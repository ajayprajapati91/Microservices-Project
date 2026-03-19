package com.example.AdminMicroservices.controller;

import com.example.AdminMicroservices.enums.BloodGroup;
import com.example.AdminMicroservices.proxy.BloodStockProxy;
import com.example.AdminMicroservices.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/donor/approve/{id}")
    public String registerUser(@PathVariable Long id,HttpServletRequest req)
    {
        return adminService.checkDonorEligibility(id,req);
    }

//    @PostMapping("/blood-stock/add")
//    public ResponseEntity<String> addBloodStock(@Valid @RequestBody BloodStockRequestDto dto)
//    {
//        return new ResponseEntity<>(adminService.addBloodStock(dto), HttpStatus.CREATED) ;
//    }
//    @GetMapping("/approve-bloodRequest/{id}")
//    public  ResponseEntity<String> approveBloodRequest(@PathVariable Long id)
//    {
//        return new ResponseEntity<>(adminService.approveBloodRequest(id),HttpStatus.OK);
//    }
//
//    @GetMapping("/allUsers")
//    public ResponseEntity<List<UserResponseDto>> getAllDonors()
//    {
//        return new ResponseEntity<>(adminService.getAllUsers(),HttpStatus.OK);
//    }
//    @GetMapping(value = "/report/")
//    public ResponseEntity<byte[]> downloadExcelData()
//    {
//        String filename="Blood_Management"+ UUID.randomUUID().toString()+".xlsx";
//        byte[] bytes = adminService.downloadExcel();
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+filename)
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(bytes);
//    }
//    @GetMapping("/getBloodRequests")
//    public List<BloodRequestResponseDto> getAllBloodRequest()
//    {
//        return adminService.getBloodRequest();
//    }


//    @GetMapping("/download-excel")
//    public ResponseEntity<byte[]> downloadExcel(){
//        byte[] bytes = adminService.downloadExcel();
//        String fileName = "Donor_" + UUID.randomUUID().toString() + ".xlsx";
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,"attachent;fileName="+fileName)
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(bytes);
//    }

//    @GetMapping("/approve/{bloodRequestId}")
//    public ResponseEntity<String> approveBloodRequest(@PathVariable Long bloodRequestId){
//        return new  ResponseEntity<>(adminService.approveBloodRequest(bloodRequestId),HttpStatus.OK);
//    }

//    @GetMapping("/approve-donor/{donorId}")
//    public ResponseEntity<String> approveDonor(@PathVariable Long donorId){
//        return new ResponseEntity<>(adminService.approveDonor(donorId),HttpStatus.OK);
//    }

//    @PostMapping("/add-bloodStock")
//    public ResponseEntity<String> addBloodStock(@RequestBody BloodStockProxy bloodStockProxy) {
//        return new ResponseEntity<>(adminService.addBloodStock(bloodStockProxy), HttpStatus.CREATED);
//    }

    @GetMapping("/get-byBloodGroup/{bloodGroup}")
    public ResponseEntity<BloodStockProxy> getBloodStockByBloodGroup(@PathVariable BloodGroup bloodGroup,HttpServletRequest req) {
        return new ResponseEntity<>(adminService.getStockByBloodGroup(bloodGroup,req), HttpStatus.OK);
    }

    @GetMapping("/get-allBloodStock")
    public ResponseEntity<List<BloodStockProxy>> getAllBloodStock(HttpServletRequest req) {
        return new ResponseEntity<>(adminService.getAllBloodStock(req), HttpStatus.OK);
    }

}
