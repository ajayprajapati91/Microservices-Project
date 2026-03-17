package com.example.AuthMicroservices.helper;

import com.example.AuthMicroservices.entity.Auth;
import com.example.AuthMicroservices.proxy.AuthProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class MapperHelper {

    @Autowired
    private ObjectMapper objectMapper;

    public Auth proxyToEntityAuth(AuthProxy authProxy){
        return objectMapper.convertValue(authProxy, Auth.class);
    }
    public AuthProxy entityToProxyAuth(Auth auth){
        return objectMapper.convertValue(auth, AuthProxy.class);
    }
}
