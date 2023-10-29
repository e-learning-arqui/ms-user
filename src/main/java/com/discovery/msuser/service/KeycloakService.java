package com.discovery.msuser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@FeignClient(name = "keycloak", url = "http://localhost:8080/realms/cognito-hub/")
public interface KeycloakService {


    @PostMapping(value = "protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String,?> getToken(@RequestBody Map<String, ?> data);

}

