package com.example.AdminMicroservices.global;

import com.example.AdminMicroservices.customizedException.BloodStockNotFoundException;
import com.example.AdminMicroservices.exception.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BloodStockNotFoundException.class )
    public ResponseEntity<Response> bloodStockNotFoundException(BloodStockNotFoundException ex, HttpServletRequest req){
        Response res = new Response();

        res.setMessage(ex.getMessage());
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setDateTime(LocalDateTime.now());
        res.setPath(req.getRequestURI());

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
