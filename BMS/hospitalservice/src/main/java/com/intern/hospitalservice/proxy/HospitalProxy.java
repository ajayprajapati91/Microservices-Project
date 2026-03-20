package com.intern.hospitalservice.proxy;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalProxy {
    private Long id;
    @NotBlank(message = "Hospital name cant be blank")
    private String hospitalName;
    @NotBlank
    private String address;
    private String contactNumber;
    private String licenseNumber;
    private Long userId;
}
