package com.discovery.msuser.service;

import com.discovery.msuser.dto.SubscriptionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@FeignClient(name = "ms-payment")
public interface PaymentService {

    @GetMapping("/api/v1/subscription/users/{userId}")
    SubscriptionDto getSubscriptionByUserId(
            @RequestHeader("Authorization") String token,
            @PathVariable("userId") Long userId);
}
