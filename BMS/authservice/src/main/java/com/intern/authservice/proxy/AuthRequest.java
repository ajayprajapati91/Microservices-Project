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
public class AuthRequest {
    @ValidEmail
    private String email;
    private String password;
}
