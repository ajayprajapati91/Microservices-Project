package com.example.HospitalMicroservices.global;

import com.example.HospitalMicroservices.customizedException.HospitalNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class GlobalExceptionHandler {

//    @ExceptionHandler(RuntimeException.class )
//    public ResponseEntity<Response1> runTimeException(RuntimeException ex, HttpServletRequest req){
//        Response res = new Response();
//
//        res.setMessage(ex.getMessage());
//        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        res.setDateTime(LocalDateTime.now());
//        res.setPath(req.getRequestURI());
//
//        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(HospitalNotFoundException.class )
//    public ResponseEntity<Response> hospitalNotFoundException(HospitalNotFoundException ex, HttpServletRequest req){
//        Response res = new Response();
//
//        res.setMessage(ex.getMessage());
//        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        res.setDateTime(LocalDateTime.now());
//        res.setPath(req.getRequestURI());
//
//        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
