package com.intern.donorservice.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class MapperHelper {
    @Autowired
    private ObjectMapper mapper;

    public <T> T map(Object source, Class<T> targetClass) {
        return mapper.convertValue(source, targetClass);
    }
}
