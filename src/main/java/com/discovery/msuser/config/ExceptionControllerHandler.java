package com.discovery.msuser.config;

import com.discovery.msuser.dto.ResponseDto;
import com.discovery.msuser.exception.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionControllerHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(UserException.class)
    ResponseEntity<ResponseDto<String>> handleUserException(UserException userException) {

        return new ResponseEntity<>(new ResponseDto<String>(userException.getMessage(),"0001",
                "null" ),
                HttpStatusCode.valueOf(userException.getHttpStatus()));
    }


}
