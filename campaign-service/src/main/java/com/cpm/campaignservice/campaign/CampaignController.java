package com.cpm.campaignservice.campaign;

import com.cpm.campaignservice.campaign.dto.CreateCampaignRequest;
import com.cpm.campaignservice.campaign.dto.UpdateCampaignRequest;
import com.cpm.campaignservice.campaign.service.CampaignService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CampaignController {
    CampaignService service;

    @PostMapping
    public ResponseEntity<Campaign> createCampaign(
            @Valid @RequestBody CreateCampaignRequest request
    ) {
        var campaign = service.createCampaign(request);

        return ResponseEntity.ok(campaign);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(
            @PathVariable("id") Long id,
            @RequestBody UpdateCampaignRequest request
    ) {
        var campaign = service.updateCampaign(id, request);

        return ResponseEntity.ok(campaign);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable("id") Long id) {
        service.deleteCampaign(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Campaign>> getCampaigns(@PathVariable("id") Long accountId) {
        return ResponseEntity.ok(service.getCampaigns(accountId));
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getCampaigns() {
        return ResponseEntity.ok(service.getCampaigns());
    }
}