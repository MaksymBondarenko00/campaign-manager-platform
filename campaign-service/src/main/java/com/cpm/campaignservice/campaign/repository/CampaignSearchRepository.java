package com.cpm.campaignservice.campaign.repository;


import com.cpm.campaignservice.campaign.Campaign;
import com.cpm.campaignservice.campaign.enums.CampaignStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CampaignSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<Campaign> searchCampaigns(String query, String town, Integer radius, Pageable pageable) {

        var searchPattern = "%" + query.toLowerCase() + "%";

        var jpql = """
                    SELECT DISTINCT c FROM Campaign c
                    LEFT JOIN c.keywords k
                    WHERE c.status = :status
                    AND (
                        lower(c.name) LIKE :query
                        OR lower(k) LIKE :query
                    )
                """;

        if (town != null && !town.isBlank()) {
            jpql += " AND lower(c.town) = :town";
        }

        if (town != null && radius != null) {
            jpql += " AND c.radiusInKm <= :radius";
        }

        jpql += " ORDER BY c.bidAmount DESC";

        TypedQuery<Campaign> queryObj = entityManager.createQuery(jpql, Campaign.class);

        queryObj.setParameter("query", searchPattern);
        queryObj.setParameter("status", CampaignStatus.ON);

        if (town != null && !town.isBlank()) {
            queryObj.setParameter("town", town.toLowerCase());
        }

        if (town != null && radius != null) {
            queryObj.setParameter("radius", radius);
        }

        queryObj.setFirstResult((int) pageable.getOffset());
        queryObj.setMaxResults(pageable.getPageSize());

        var result = queryObj.getResultList();

        return new PageImpl<>(result, pageable, result.size());
    }
}