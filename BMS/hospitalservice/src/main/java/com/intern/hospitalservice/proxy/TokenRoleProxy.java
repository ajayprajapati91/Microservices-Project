package com.intern.hospitalservice.proxy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRoleProxy {
    private String role;
    private String token;
}
