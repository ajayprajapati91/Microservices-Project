package com.intern.authservice.proxy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenRoleProxy {
    private String token;
    private String role;
}
