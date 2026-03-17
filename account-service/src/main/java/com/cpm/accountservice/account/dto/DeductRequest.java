package com.cpm.accountservice.account.dto;

import java.math.BigDecimal;

public record DeductRequest(
        Long accountId,
        BigDecimal amount
) {}
