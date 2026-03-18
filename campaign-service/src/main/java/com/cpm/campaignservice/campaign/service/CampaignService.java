package com.cpm.campaignservice.campaign.service;

import com.cpm.campaignservice.campaign.Campaign;
import com.cpm.campaignservice.campaign.dto.CreateCampaignRequest;
import com.cpm.campaignservice.campaign.dto.UpdateCampaignRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param id      campaign identifier
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

    /**
     * Returns all campaigns in the system using pagination.
     * <p>
     * This method is mainly intended for administrative or internal operations
     * where access to the full list of campaigns is required.
     *
     * @param pageable pagination and sorting configuration
     * @return paginated list of campaigns
     */
    Page<Campaign> getAllCampaigns(Pageable pageable);

    Page<Campaign> getAllCampaignsWithoutAccount(Pageable pageable);

    /**
     * Searches campaigns by name or keywords with optional location filters.
     * <p>
     * The search is performed against campaign names and associated keywords.
     * Results can optionally be filtered by town and campaign radius.
     * Only active campaigns are typically returned.
     *
     * @param query    text used to search campaign names and keywords
     * @param town     optional town filter
     * @param radius   optional radius filter in kilometers
     * @param pageable pagination and sorting configuration
     * @return paginated list of campaigns matching the search criteria
     */
    Page<Campaign> searchCampaigns(String query, String town, Integer radius, Pageable pageable);
}