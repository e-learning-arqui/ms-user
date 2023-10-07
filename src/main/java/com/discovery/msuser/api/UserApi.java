package com.discovery.msuser.api;

import com.discovery.msuser.bl.UserBl;
import com.discovery.msuser.dto.ResponseDto;
import com.discovery.msuser.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserApi {

    @Autowired
    private UserBl userBl;

    Logger logger = LoggerFactory.getLogger(UserApi.class);
    public UserApi(UserBl userBl) {
        this.userBl = userBl;
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseDto<String>> createUser(@RequestBody UserDto userDto){

        logger.info("Starting to create user with username: {}", userDto.getUsername(), " and email: {}", userDto.getEmail());
        userBl.createUser(userDto);
        return ResponseEntity.ok(new ResponseDto<>(null , "0000","User created successfully"));
    }


}
