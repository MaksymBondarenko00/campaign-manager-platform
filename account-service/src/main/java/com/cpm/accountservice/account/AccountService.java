package com.cpm.accountservice.account;

import com.cpm.accountservice.account.dto.AccountResponse;
import com.cpm.accountservice.account.dto.DeductRequest;
import com.cpm.accountservice.account.dto.DepositRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void createAccount(Long userId) {

        if(accountRepository.findByUserId(userId).isPresent()){
            return;
        }

        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(BigDecimal.ZERO);

        accountRepository.save(account);
    }

    public AccountResponse getByUserId(Long userId) {
        var account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return map(account);
    }

    public AccountResponse deposit(DepositRequest request) {

        var account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(request.amount()));

        return map(accountRepository.save(account));
    }

    public AccountResponse deduct(DeductRequest request) {

        var account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(request.amount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(request.amount()));

        return map(accountRepository.save(account));
    }

    private AccountResponse map(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getUserId(),
                account.getBalance()
        );
    }
}