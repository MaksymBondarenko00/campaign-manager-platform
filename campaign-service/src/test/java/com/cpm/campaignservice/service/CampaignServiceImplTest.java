package com.cpm.campaignservice.service;

import com.cpm.campaignservice.campaign.Campaign;
import com.cpm.campaignservice.campaign.CampaignMapper;
import com.cpm.campaignservice.campaign.dto.CreateCampaignRequest;
import com.cpm.campaignservice.campaign.dto.UpdateCampaignRequest;
import com.cpm.campaignservice.campaign.enums.CampaignStatus;
import com.cpm.campaignservice.campaign.repository.CampaignRepository;
import com.cpm.campaignservice.campaign.service.CampaignServiceImpl;
import com.cpm.campaignservice.clients.AccountClient;
import com.cpm.campaignservice.clients.UserClient;
import com.cpm.campaignservice.product.Product;
import com.cpm.campaignservice.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaignServiceImplTest {

    @Mock
    CampaignRepository campaignRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CampaignMapper campaignMapper;

    @Mock
    UserClient userClient;

    @Mock
    AccountClient accountClient;

    @InjectMocks
    CampaignServiceImpl service;

    @Test
    void createCampaign_success_test() {

        var request = new CreateCampaignRequest(
                1L,
                10L,
                "test",
                List.of("phone"),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(20),
                "warsaw",
                10
        );

        var product = new Product();
        product.setId(1L);
        product.setAccountId(10L);

        var campaign = new Campaign();
        campaign.setId(5L);
        campaign.setAccountId(10L);

        when(productRepository.findByIdAndAccountId(1L, 10L))
                .thenReturn(Optional.of(product));

        when(accountClient.getBalance()).thenReturn(BigDecimal.valueOf(100));

        when(campaignMapper.toEntity(request)).thenReturn(campaign);

        when(campaignRepository.save(campaign)).thenReturn(campaign);

        var result = service.createCampaign(request);

        assertNotNull(result);

        verify(accountClient).deduct(10L, BigDecimal.valueOf(20));
        verify(campaignRepository).save(campaign);
    }

    @Test
    void createCampaign_productNotFound_test() {

        var request = new CreateCampaignRequest(
                1L,
                10L,
                "test",
                List.of("phone"),
                BigDecimal.ONE,
                BigDecimal.TEN,
                "warsaw",
                10
        );

        when(productRepository.findByIdAndAccountId(1L, 10L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.createCampaign(request));
    }

    @Test
    void updateCampaign_success_test() {

        var campaign = new Campaign();
        campaign.setId(1L);
        campaign.setAccountId(10L);
        campaign.setCampaignFund(BigDecimal.valueOf(10));

        var request = new UpdateCampaignRequest(
                "new name",
                List.of("Laptop"),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(20),
                CampaignStatus.ON,
                "krakow",
                15
        );

        when(campaignRepository.findById(1L))
                .thenReturn(Optional.of(campaign));

        when(campaignRepository.save(campaign))
                .thenReturn(campaign);

        var result = service.updateCampaign(1L, request);

        assertEquals("new name", result.getName());
        assertEquals(BigDecimal.valueOf(20), result.getCampaignFund());

        verify(accountClient).deduct(10L, BigDecimal.valueOf(10));
        verify(campaignRepository).save(campaign);
    }

    @Test
    void deleteCampaign_success_test() {

        var campaign = new Campaign();
        campaign.setId(1L);
        campaign.setAccountId(10L);
        campaign.setCampaignFund(BigDecimal.valueOf(30));

        when(campaignRepository.findById(1L))
                .thenReturn(Optional.of(campaign));

        service.deleteCampaign(1L);

        verify(accountClient).deposit(10L, BigDecimal.valueOf(30));
        verify(campaignRepository).deleteById(1L);
    }

    @Test
    void deleteCampaign_notFound_test() {

        when(campaignRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.deleteCampaign(1L));
    }

    @Test
    void getAllCampaignsByAccount_test() {

        var campaigns = List.of(new Campaign(), new Campaign());

        when(campaignRepository.findByAccountId(10L))
                .thenReturn(campaigns);

        var result = service.getCampaigns(10L);

        assertEquals(2, result.size());
    }

    @Test
    void getAllCampaigns_currentAccount_test() {

        var campaigns = List.of(new Campaign());

        when(userClient.getCurrentUserId()).thenReturn(5L);
        when(accountClient.getCurrentAccountId(5L)).thenReturn(10L);
        when(campaignRepository.findByAccountId(10L)).thenReturn(campaigns);

        var result = service.getCampaigns();

        assertEquals(1, result.size());
    }

}