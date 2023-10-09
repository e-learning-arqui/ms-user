package com.discovery.msuser.service;

import com.discovery.msuser.dto.CourseDto;
import com.discovery.msuser.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;

@Service
@FeignClient(name = "ms-course")
public interface CourseService {
    @PostMapping("/api/v1/courses")
    public ResponseDto<String> createCourse(@RequestBody CourseDto courseDto);

    @GetMapping("api/v1/courses/professor/{professorId}")
    Page<CourseDto> getCoursesByProfessorId(@PathVariable String professorId,
                                                                         @RequestParam(defaultValue = "0") Integer page,
                                                                         @RequestParam(defaultValue = "10") Integer size);
}
