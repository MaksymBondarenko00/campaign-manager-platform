package com.cpm.accountservice.account.dto;

import java.math.BigDecimal;

public record DepositRequest(
        Long accountId,
        BigDecimal amount
) {}
