package com.cpm.campaignservice.product.dto;

import java.math.BigDecimal;

public record CreateProductRequest(
        Long accountId,
        String name,
        String description,
        BigDecimal price
) {}
