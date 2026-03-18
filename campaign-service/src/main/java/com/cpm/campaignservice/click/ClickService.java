package com.cpm.campaignservice.click;

import com.cpm.campaignservice.campaign.enums.CampaignStatus;
import com.cpm.campaignservice.campaign.repository.CampaignRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClickService {
    CampaignRepository campaignRepository;
    ClickRepository clickRepository;
    ClickMapper clickMapper;

    @Transactional
    public void registerClick(Long campaignId) {
        var campaign = campaignRepository.findByIdForUpdate(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        if (campaign.getStatus() != CampaignStatus.ON) {
            throw new RuntimeException("Campaign not active");
        }

        var newFund = campaign.getCampaignFund()
                .subtract(campaign.getBidAmount());

        campaign.setCampaignFund(newFund);

        if (newFund.compareTo(BigDecimal.ZERO) <= 0) {
            campaign.setStatus(CampaignStatus.OFF);
            log.info("Campaign {} stopped due to exhausted budget", campaignId);
        }

        clickRepository.save(
                clickMapper.toEntity(campaign)
        );

        campaignRepository.save(campaign);
        log.info("Click registered for campaign {}", campaignId);
    }
}
