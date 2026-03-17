package com.cpm.campaignservice.campaign.controller;

import com.cpm.campaignservice.campaign.Campaign;
import com.cpm.campaignservice.campaign.dto.CreateCampaignRequest;
import com.cpm.campaignservice.campaign.dto.UpdateCampaignRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Campaigns", description = "API for managing advertising campaigns")
@RequestMapping("/campaigns")
public interface CampaignController {
    @Operation(
            summary = "Create campaign",
            description = "Creates a new advertising campaign for a product using the current user's account"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Campaign created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid campaign data"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PostMapping
    ResponseEntity<Campaign> createCampaign(
            @Valid
            @RequestBody
            CreateCampaignRequest request
    );

    @Operation(
            summary = "Update campaign",
            description = "Updates campaign parameters such as name, keywords, bid, status or budget"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Campaign updated successfully"),
            @ApiResponse(responseCode = "404", description = "Campaign not found"),
            @ApiResponse(responseCode = "400", description = "Invalid update request")
    })
    @PatchMapping("/{id}")
    ResponseEntity<Campaign> updateCampaign(
            @Parameter(description = "Campaign ID")
            @PathVariable("id") Long id,

            @RequestBody
            UpdateCampaignRequest request
    );

    @Operation(
            summary = "Delete campaign",
            description = "Deletes a campaign and returns remaining campaign funds back to the account balance"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Campaign deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Campaign not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCampaign(
            @Parameter(description = "Campaign ID")
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Get campaigns by account",
            description = "Returns all campaigns belonging to the specified account"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Campaign list returned")
    })
    @GetMapping("/{id}")
    ResponseEntity<List<Campaign>> getCampaigns(
            @Parameter(description = "Account ID")
            @PathVariable("id") Long accountId
    );

    @Operation(
            summary = "Get all campaigns",
            description = "Returns all campaigns in the system (used internally or for administrative purposes)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Campaign list returned")
    })
    @GetMapping
    ResponseEntity<List<Campaign>> getCampaigns();
}