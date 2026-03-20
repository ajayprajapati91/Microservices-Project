package com.intern.adminservice.controller;

import com.intern.adminservice.domain.BloodStock;
import com.intern.adminservice.proxy.BloodStockProxy;
import com.intern.adminservice.proxy.RoleProxy;
import com.intern.adminservice.proxy.UserProxy;
import com.intern.adminservice.proxy.Users;
import com.intern.adminservice.service.AdminService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@OpenAPIDefinition
@RestController
@RequestMapping(value = "/v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/users")
    public ResponseEntity<List<Users>> getAllUsers(HttpServletRequest request){
        return new ResponseEntity<>(adminService.getAllUser(request.getHeader("Authorization")) , HttpStatus.OK);
    }

    @PutMapping(value = "/donor/{id}/approve")
    public ResponseEntity<String> approveRequest(@PathVariable Long id , HttpServletRequest request){
        return new ResponseEntity<>(adminService.approve(id , request.getHeader("Authorization")) , HttpStatus.OK);
    }

    @PostMapping(value = "/blood-stock/add")
    public ResponseEntity<String> addBloodStock(@Valid @RequestBody BloodStockProxy bloodStockProxy){
        return new ResponseEntity<>(adminService.addBloodStock(bloodStockProxy), HttpStatus.CREATED);
    }

    @PostMapping(value = "hospital/{id}/approve")
    public ResponseEntity<String> approveBloodRequest(@PathVariable Long id , HttpServletRequest request){
        return new ResponseEntity<>(adminService.approveBloodRequest(id , request.getHeader("Authorization")) , HttpStatus.OK);
    }

    @GetMapping(value = "/reports/blood-stock")
    public ResponseEntity<String> downloadExcelFile(HttpServletRequest request){
        byte[] bytes = adminService.getExcel( request.getHeader("Authorization"));
        String folderPath = "downloads";
        File folder = new File(folderPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String filename = "bloodStock.xlsx";

        Path path = Paths.get(folderPath , filename);

        try{
            Files.write(path , bytes);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return  new ResponseEntity<>("File saved at: " + path.toAbsolutePath(),
                HttpStatus.OK);

    }

    @GetMapping("/{bloodGroup}")
    public BloodStock getbyBloodGroup(@PathVariable String bloodGroup){
        return adminService.findbyBloodGroup(bloodGroup);
    }

//    @GetMapping(value = "/reports/users")
//    public ResponseEntity<String> downloaduserReport(){
//        byte[] bytes = adminService.getUserReport();
//
//        String folderPath = "downloads";
//        File folder = new File(folderPath);
//        if(!folder.exists()){
//            folder.mkdirs();
//        }
//        String filename = "usersreport.xlsx";
//
//        Path path = Paths.get(folderPath , filename);
//
//        try{
//            Files.write(path , bytes);
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//
//        return  new ResponseEntity<>("File saved at: " + path.toAbsolutePath(),
//                HttpStatus.OK);
//
//    }
}
