package com.discovery.msuser.service;

import com.discovery.msuser.dto.CourseDto;
import com.discovery.msuser.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Service
@FeignClient(name = "ms-course")
public interface CourseService {
    @PostMapping("/api/v1/course")
    public ResponseDto<String> createCourse(@RequestBody CourseDto courseDto);
}
