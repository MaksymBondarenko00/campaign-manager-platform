package com.cpm.campaignservice.campaign.controller;

import com.cpm.campaignservice.campaign.Campaign;
import com.cpm.campaignservice.campaign.dto.CreateCampaignRequest;
import com.cpm.campaignservice.campaign.dto.UpdateCampaignRequest;
import com.cpm.campaignservice.campaign.service.CampaignServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CampaignControllerImpl implements CampaignController {
    CampaignServiceImpl service;

    @Override
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(
            @Valid @RequestBody CreateCampaignRequest request
    ) {
        var campaign = service.createCampaign(request);

        return ResponseEntity.ok(campaign);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(
            @PathVariable("id") Long id,
            @RequestBody UpdateCampaignRequest request
    ) {
        var campaign = service.updateCampaign(id, request);

        return ResponseEntity.ok(campaign);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable("id") Long id) {
        service.deleteCampaign(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<List<Campaign>> getCampaigns(@PathVariable("id") Long accountId) {
        return ResponseEntity.ok(service.getCampaigns(accountId));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Campaign>> getCampaigns() {
        return ResponseEntity.ok(service.getCampaigns());
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Page<Campaign>> getAllCampaignsWithoutAccount(Pageable pageable) {
        return ResponseEntity.ok(service.getAllCampaignsWithoutAccount(pageable));
    }

    @Override
    @GetMapping("/view")
    public ResponseEntity<Page<Campaign>> getAllCampaigns(Pageable pageable) {
        return ResponseEntity.ok(service.getAllCampaigns(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Campaign>> searchCampaigns(
            @RequestParam("query") String query,
            @RequestParam(value = "town", required = false) String town,
            @RequestParam(value = "radius", required = false) Integer radius,
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.searchCampaigns(query, town, radius, pageable));
    }
}