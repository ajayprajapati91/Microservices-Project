package com.intern.authservice.proxy;

import com.intern.authservice.custom.validator.ValidEmail;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgetPassword {

    @ValidEmail
    private String email;
    @Size(min = 6 , max = 12 ,message = "Password size should be between 6 to 12")
    private String password;
}
