package com.example.HospitalMicroservices.proxy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HospitalProxy {

    private Long hospitalId;
    private String hospitalName;
    private String email;
    private String password;
    private String address;
    private String contactNo;
}
