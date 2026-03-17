package com.cpm.campaignservice.campaign.service;

import com.cpm.campaignservice.campaign.Campaign;
import com.cpm.campaignservice.campaign.dto.CreateCampaignRequest;
import com.cpm.campaignservice.campaign.dto.UpdateCampaignRequest;

import java.util.List;

public interface CampaignService {

    Campaign createCampaign(CreateCampaignRequest request);

    Campaign updateCampaign(Long id, UpdateCampaignRequest request);
    void deleteCampaign(Long id);

    List<Campaign> getCampaigns(Long accountId);

    List<Campaign> getCampaigns();
}
