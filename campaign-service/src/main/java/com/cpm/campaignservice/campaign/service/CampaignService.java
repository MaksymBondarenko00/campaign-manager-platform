package com.cpm.campaignservice.campaign.service;

import com.cpm.campaignservice.campaign.Campaign;
import com.cpm.campaignservice.campaign.dto.CreateCampaignRequest;
import com.cpm.campaignservice.campaign.dto.UpdateCampaignRequest;

import java.util.List;

/**
 * Service responsible for managing advertising campaigns.
 * <p>
 * This service handles the full lifecycle of campaigns including creation,
 * updates, deletion and retrieval. It also ensures proper budget handling,
 * including transferring funds from the account balance to the campaign
 * budget and returning remaining funds when a campaign is deleted.
 */
public interface CampaignService {

    /**
     * Creates a new campaign for the current user's account.
     *
     * @param request campaign creation request containing campaign parameters
     * @return created campaign entity
     */
    Campaign createCampaign(CreateCampaignRequest request);

    /**
     * Updates an existing campaign.
     *
     * @param id campaign identifier
     * @param request request containing updated campaign parameters
     * @return updated campaign entity
     */
    Campaign updateCampaign(Long id, UpdateCampaignRequest request);

    /**
     * Deletes a campaign and returns remaining campaign funds
     * back to the associated account balance.
     *
     * @param id campaign identifier
     */
    void deleteCampaign(Long id);

    /**
     * Returns all campaigns belonging to the specified account.
     *
     * @param accountId account identifier
     * @return list of campaigns
     */
    List<Campaign> getCampaigns(Long accountId);

    /**
     * Returns all campaigns in the system.
     * <p>
     * This method is primarily used for internal or administrative purposes.
     *
     * @return list of campaigns
     */
    List<Campaign> getCampaigns();
}