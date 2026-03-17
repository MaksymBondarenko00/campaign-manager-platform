package com.cpm.accountservice.account;

import com.cpm.accountservice.account.dto.AccountResponse;
import com.cpm.accountservice.account.dto.DeductRequest;
import com.cpm.accountservice.account.dto.DepositRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    @PostMapping("/create")
    public void internalCreateAccount(@RequestParam("userId") Long userId){
        service.createAccount(userId);
    }

    @GetMapping("/user/{userId}")
    public AccountResponse getByUser(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PostMapping("/deposit")
    public AccountResponse deposit(@RequestBody DepositRequest request) {
        return service.deposit(request);
    }

    @PostMapping("/deduct")
    public AccountResponse deduct(@RequestBody DeductRequest request) {
        return service.deduct(request);
    }
}
