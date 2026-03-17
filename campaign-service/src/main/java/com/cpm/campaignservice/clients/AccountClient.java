package com.cpm.campaignservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Component
@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/accounts/{userId}")
    Long getCurrentAccountId(@PathVariable("userId") Long userId);

    @GetMapping("/accounts/balance")
    BigDecimal getBalance();

    @PostMapping("/accounts/{accountId}/deduct")
    BigDecimal deduct(
            @PathVariable("accountId") Long accountId,
            @RequestParam("amount") BigDecimal amount
    );

    @PostMapping("/accounts/{accountId}/deposit")
    BigDecimal deposit(
            @PathVariable("accountId") Long accountId,
            @RequestParam("amount") BigDecimal amount
    );


}
