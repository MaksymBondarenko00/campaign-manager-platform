package com.cpm.campaignservice.campaign.repository;

import com.cpm.campaignservice.campaign.Campaign;
import com.cpm.campaignservice.campaign.enums.CampaignStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByAccountId(Long accountId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Campaign c where c.id = :id")
    Optional<Campaign> findByIdForUpdate(@Param("id") Long id);

    Page<Campaign> findAllByAccountIdNot(Pageable pageable, Long accountId);

    @Query("select c from Campaign c where c.status = :status")
    Page<Campaign> findAllByStatus(@Param("status") CampaignStatus status, Pageable pageable);
}
