package com.discovery.msuser.api;

import com.discovery.msuser.bl.UserBl;
import com.discovery.msuser.dto.CourseDto;
import com.discovery.msuser.dto.KeycloakUserDto;
import com.discovery.msuser.dto.ResponseDto;
import com.discovery.msuser.dto.UserDto;
import com.discovery.msuser.exception.UserException;
import com.discovery.msuser.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserApi {

    @Autowired
    private UserBl userBl;

    @Autowired
    private final CourseService courseService;

    Logger logger = LoggerFactory.getLogger(UserApi.class);

    public UserApi(UserBl userBl, CourseService courseService) {
        this.userBl = userBl;
        this.courseService = courseService;
    }

    @PostMapping("/students")
    public ResponseEntity<ResponseDto<String>> createUser(@RequestBody UserDto userDto) throws UserException {

        logger.info("Starting to create user with username: {}", userDto.getUsername(), " and email: {}", userDto.getEmail());
        userBl.createUser(userDto);
        return ResponseEntity.ok(new ResponseDto<>(null, "0000", "User created successfully"));
    }

    @PostMapping("/professors")
    public ResponseEntity<ResponseDto<String>> createProfessor(@RequestBody UserDto userDto) throws UserException {

        logger.info("Starting to create professor with username: {}", userDto.getUsername());
        userBl.createProfessor(userDto);
        return ResponseEntity.ok(new ResponseDto<>(null, "0000", "Professor created successfully"));
    }


    @GetMapping("/users/{uuid}")
    public ResponseEntity<ResponseDto<KeycloakUserDto>> getUser(@PathVariable("uuid") String uuid) throws UserException {
        logger.info("Starting to get user with uuid: {}", uuid);
        KeycloakUserDto userDto = userBl.getUserByKeycloakId(uuid);
        return ResponseEntity.ok(new ResponseDto<>(null, "0000", userDto));

    }

    //Register course for professor
    @PostMapping("/professors/course")
    public ResponseEntity<ResponseDto<String>> registerCourse(@RequestBody CourseDto courseDto) throws UserException {
        logger.info("Starting to register course for professor from ms-user");
        courseService.createCourse(courseDto);
        return ResponseEntity.ok(new ResponseDto<>(null, "0000", "Course registered successfully"));
    }

}

