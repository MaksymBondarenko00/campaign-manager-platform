package com.cpm.campaignservice.campaign.dto;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record CreateCampaignRequest(

        @NotNull
        Long productId,

        @NotNull
        Long accountId,

        @NotBlank
        String name,

        @NotEmpty
        List<String> keywords,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal bidAmount,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal campaignFund,

        String town,

        @NotNull
        @Min(1)
        Integer radiusInKm

) {}
