package com.cpm.campaignservice.campaign;

import com.cpm.campaignservice.campaign.enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "campaigns")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long productId;

    @Column(nullable = false)
    Long accountId;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "campaign_keywords", joinColumns = @JoinColumn(name = "campaign_id"))
    List<String> keywords;

    @Column(nullable = false)
    BigDecimal bidAmount;

    @Column(nullable = false)
    BigDecimal campaignFund;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    CampaignStatus status;

    String town;

    @Column(nullable = false)
    Integer radiusInKm;
}
