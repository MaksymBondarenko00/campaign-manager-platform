package com.cpm.accountservice.account;

import com.cpm.accountservice.account.dto.AccountResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @PostMapping("/create")
    public void internalCreateAccount(@RequestParam("userId") Long userId) {
        accountService.createAccount(userId);
    }

    @PostMapping("/{accountId}/deposit")
    BigDecimal internalDeposit(
            @PathVariable("accountId") Long accountId,
            @RequestParam("amount") BigDecimal amount) {
        return accountService.deposit(accountId, amount);
    }

    @GetMapping("/{userId}")
    public Long internalGetByUserId(@PathVariable("userId") Long userId) {
        return accountService.getByUserId(userId);
    }

    @GetMapping("/user")
    public AccountResponse getByUser() {
        return accountService.getByUserId();
    }

    @PostMapping("/deposit")
    public AccountResponse deposit(@RequestParam("amount") BigDecimal amount) {
        return accountService.deposit(amount);
    }

    @PostMapping("/{accountId}/deduct")
    public BigDecimal deduct(
            @PathVariable("accountId") Long accountId,
            @RequestParam("amount") BigDecimal amount) {
        return accountService.deduct(accountId, amount);
    }

    @GetMapping("/balance")
    public BigDecimal getBalance() {
        return accountService.getBalance();
    }
}
