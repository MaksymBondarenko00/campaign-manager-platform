package com.cpm.campaignservice.campaign.dto;

import com.cpm.campaignservice.campaign.enums.CampaignStatus;

import java.math.BigDecimal;
import java.util.List;

public record UpdateCampaignRequest(
        String name,
        List<String> keywords,
        BigDecimal bidAmount,
        BigDecimal campaignFund,
        CampaignStatus status,
        String town,
        Integer radiusInKm
) {}
