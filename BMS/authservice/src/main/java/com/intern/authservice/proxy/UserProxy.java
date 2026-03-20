package com.intern.authservice.proxy;

import com.intern.authservice.custom.validator.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProxy {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;

    @Size(min = 6 , max = 12 ,message = "Password size should be between 6 to 12")
    private String password;
    private String role;

    @ValidPhoneNumber
    private String phone;
    private String status;
}
