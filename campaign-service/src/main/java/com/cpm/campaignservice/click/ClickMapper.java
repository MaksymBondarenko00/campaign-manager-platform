package com.cpm.campaignservice.click;

import com.cpm.campaignservice.campaign.Campaign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClickMapper {

    @Mapping(target = "campaignId", source = "id")
    @Mapping(target = "cost", source = "bidAmount")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Click toEntity(Campaign campaign);

}
