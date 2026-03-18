package com.cpm.campaignservice.campaign;

import com.cpm.campaignservice.campaign.dto.CreateCampaignRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CampaignMapper {

    @Mapping(target = "status", constant = "ON")
    @Mapping(target = "keywords", expression = "java(normalizeKeywords(request.keywords()))")
    Campaign toEntity(CreateCampaignRequest request);

    default List<String> normalizeKeywords(List<String> keywords) {
        return keywords.stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .distinct()
                .toList();
    }

}
