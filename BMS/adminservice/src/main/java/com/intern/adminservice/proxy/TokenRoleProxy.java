package com.intern.adminservice.proxy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRoleProxy {
    private String token;
    private String role;
}
