package com.cpm.accountservice.account;

import com.cpm.accountservice.account.client.UserClient;
import com.cpm.accountservice.account.dto.AccountResponse;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository accountRepository;
    UserClient userClient;

    @Transactional
    public void createAccount(Long userId) {
        if(accountRepository.findByUserId(userId).isPresent()){
            return;
        }

        var account = new Account();
        account.setUserId(userId);
        account.setBalance(BigDecimal.ZERO);

        accountRepository.save(account);
    }

    public AccountResponse getByUserId() {
        var account = accountRepository.findByUserId(getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return map(account);
    }

    public Long getByUserId(Long userId) {
        var account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return account.getId();
    }

    @Transactional
    public AccountResponse deposit(BigDecimal amount) {
        var account = accountRepository.findByUserIdForUpdate(getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));

        return map(accountRepository.save(account));
    }

    @Transactional
    public BigDecimal deposit(Long accountId, BigDecimal amount) {
        var account = accountRepository.findByUserIdForUpdate(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));

        accountRepository.save(account);

        return account.getBalance();
    }

    @Transactional
    public BigDecimal deduct(Long accountId, BigDecimal amount) {
        var account = accountRepository.findByUserIdForUpdate(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        return account.getBalance();
    }

    private AccountResponse map(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getUserId(),
                account.getBalance()
        );
    }

    public BigDecimal getBalance() {
        return accountRepository
                .findByUserId(getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("Account not found"))
                .getBalance();
    }

    private Long getCurrentUserId() {
        return userClient.getCurrentUserId();
    }
}