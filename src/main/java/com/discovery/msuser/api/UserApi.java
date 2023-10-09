package com.discovery.msuser.api;

import com.discovery.msuser.bl.UserBl;
import com.discovery.msuser.dto.CourseDto;
import com.discovery.msuser.dto.KeycloakUserDto;
import com.discovery.msuser.dto.ResponseDto;
import com.discovery.msuser.dto.UserDto;
import com.discovery.msuser.entity.Professor;
import com.discovery.msuser.exception.UserException;
import com.discovery.msuser.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/api/v1/users")
public class UserApi {

    @Autowired
    private UserBl userBl;

    @Autowired
    private final CourseService courseService;


    @Value("${server.port}")
    private String port;
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

    @GetMapping("/professors/{id}")
    public ResponseEntity<ResponseDto<Professor>> getProfessorById(@PathVariable Long id) throws UserException {
        logger.info("Starting to get professor with id: {}", id);
        Professor professor = userBl.getProfessorById(id);
        return ResponseEntity.ok(new ResponseDto<>(null, "0000", professor));
    }


    //Register course for professor
    @PostMapping("/professors/course")
    public ResponseEntity<ResponseDto<String>> registerCourse(@RequestBody CourseDto courseDto) throws UserException {
        logger.info("Starting to register course for professor from ms-user");
        courseService.createCourse(courseDto);
        return ResponseEntity.ok(new ResponseDto<>(null, "0000", "Course registered successfully"));
    }

    //Get courses by professor id
    @GetMapping("/professors/{professorId}/courses")
    public ResponseEntity<Page<CourseDto>> getCoursesByProfessorId(
            @PathVariable String professorId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {

        logger.info("Starting to get courses from {}", port);
        Page<CourseDto> courseDtoPage = courseService.getCoursesByProfessorId(professorId, page, size);
        return ResponseEntity.ok(courseDtoPage);
    }



}

