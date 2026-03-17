package com.cpm.accountservice.account.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "auth-service")
public interface UserClient {

    @PostMapping("/users/current")
    Long getCurrentUserId();
}
