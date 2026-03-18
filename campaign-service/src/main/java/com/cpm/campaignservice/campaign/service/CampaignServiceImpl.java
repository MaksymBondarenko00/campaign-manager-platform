package com.cpm.campaignservice.campaign.service;

import com.cpm.campaignservice.campaign.Campaign;
import com.cpm.campaignservice.campaign.CampaignMapper;
import com.cpm.campaignservice.campaign.dto.CreateCampaignRequest;
import com.cpm.campaignservice.campaign.dto.UpdateCampaignRequest;
import com.cpm.campaignservice.campaign.enums.CampaignStatus;
import com.cpm.campaignservice.campaign.repository.CampaignRepository;
import com.cpm.campaignservice.campaign.repository.CampaignSearchRepository;
import com.cpm.campaignservice.clients.AccountClient;
import com.cpm.campaignservice.clients.UserClient;
import com.cpm.campaignservice.product.Product;
import com.cpm.campaignservice.product.ProductRepository;
import com.cpm.campaignservice.system.exceptions.InsufficientBalanceException;
import com.cpm.campaignservice.system.exceptions.InvalidCampaignException;
import com.cpm.campaignservice.system.exceptions.ProductOwnershipException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CampaignServiceImpl implements CampaignService {
    CampaignRepository campaignRepository;
    ProductRepository productRepository;
    CampaignMapper campaignMapper;
    UserClient userClient;
    AccountClient accountClient;
    CampaignSearchRepository campaignSearchRepository;

    @Override
    public Campaign createCampaign(CreateCampaignRequest request) {
        var product = productRepository
                .findByIdAndAccountId(request.productId(), request.accountId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        validateCampaign(request, product);

        accountClient.deduct(
                request.accountId(),
                request.campaignFund()
        );

        var campaign = campaignMapper.toEntity(request);
        campaign.setProductId(product.getId());

        log.info("Campaign {} created for account {}", campaign.getId(), campaign.getAccountId());

        return campaignRepository.save(campaign);
    }

    @Override
    public Campaign updateCampaign(Long id, UpdateCampaignRequest request) {
        var campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        updateIfNotNull(request.name(), campaign::setName);
        updateIfNotNull(normalizeKeywords(request.keywords()), campaign::setKeywords);
        updateIfNotNull(request.bidAmount(), campaign::setBidAmount);
        updateIfNotNull(request.town(), campaign::setTown);
        updateIfNotNull(request.radiusInKm(), campaign::setRadiusInKm);
        updateIfNotNull(request.status(), campaign::setStatus);

        if (request.campaignFund() != null) {
            syncCampaignFundWithAccount(campaign, request.campaignFund());
            campaign.setCampaignFund(request.campaignFund());
        }

        return campaignRepository.save(campaign);
    }

    @Override
    public void deleteCampaign(Long id) {
        var campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        accountClient.deposit(campaign.getAccountId(), campaign.getCampaignFund());

        log.info("Campaign {} stopped", campaign.getId());

        campaignRepository.deleteById(id);
    }

    @Override
    public List<Campaign> getCampaigns(Long accountId) {
        return campaignRepository.findByAccountId(accountId);
    }

    @Override
    public List<Campaign> getCampaigns() {
        return campaignRepository.findByAccountId(getCurrentAccountId());
    }

    @Override
    public Page<Campaign> getAllCampaigns(Pageable pageable) {
        return campaignRepository.findAllByStatus(CampaignStatus.ON, pageable);
    }

    @Override
    public Page<Campaign> getAllCampaignsWithoutAccount(Pageable pageable) {
        return campaignRepository.findAllByAccountIdNot(pageable, getCurrentAccountId());
    }

    @Override
    public Page<Campaign> searchCampaigns(String query, String town, Integer radius, Pageable pageable) {
        return campaignSearchRepository.searchCampaigns(query, town, radius, pageable);
    }

    private void validateCampaign(CreateCampaignRequest request, Product product) {
        if (request.campaignFund().compareTo(accountClient.getBalance()) > 0) {
            throw new InsufficientBalanceException("Campaign fund exceeds account balance");
        }

        if (!product.getAccountId().equals(request.accountId())) {
            throw new ProductOwnershipException("Product does not belong to account");
        }

        if (request.bidAmount().compareTo(request.campaignFund()) > 0) {
            throw new InvalidCampaignException("Bid amount cannot exceed campaign fund");
        }

        if (request.campaignFund().compareTo(
                request.bidAmount().multiply(BigDecimal.valueOf(10))
        ) < 0) {
            throw new InvalidCampaignException("Campaign fund too small for bid");
        }

        if (request.keywords().isEmpty()) {
            throw new InvalidCampaignException("At least one keyword required");
        }

        if (request.keywords().size() > 20) {
            throw new InvalidCampaignException("Too many keywords (max 20)");
        }
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    private List<String> normalizeKeywords(List<String> keywords) {
        if (keywords == null) {
            return null;
        }

        return keywords.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void syncCampaignFundWithAccount(Campaign campaign, BigDecimal newFund) {
        if (newFund == null) {
            return;
        }

        var oldFund = campaign.getCampaignFund();
        var comparison = newFund.compareTo(oldFund);

        if (comparison > 0) {

            var difference = newFund.subtract(oldFund);
            var balance = accountClient.getBalance();

            if (balance.compareTo(difference) < 0) {
                throw new InsufficientBalanceException("Campaign fund exceeds account balance");
            }

            accountClient.deduct(campaign.getAccountId(), difference);
        } else if (comparison < 0) {
            var difference = oldFund.subtract(newFund);
            accountClient.deposit(campaign.getAccountId(), difference);
        }
    }

    private Long getCurrentAccountId() {
        return accountClient.getCurrentAccountId(
                userClient.getCurrentUserId()
        );
    }
}
