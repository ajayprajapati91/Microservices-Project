package com.intern.adminservice.proxy;

import lombok.Data;

@Data
public class Hospital {
    private Long id;
    private String hospitalName;
    private String address;
    private String contactNumber;
    private String licenseNumber;
    private Long userId;
}
