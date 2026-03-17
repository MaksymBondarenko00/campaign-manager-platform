package com.cpm.authservice.system.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "account-service")
public interface AccountClient {

    @PostMapping("/accounts/create")
    String createAccount(@RequestParam("userId") Long userId);

}
