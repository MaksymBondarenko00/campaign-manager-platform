package com.cpm.campaignservice.product;

import com.cpm.campaignservice.product.dto.CreateProductRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    Product toEntity(CreateProductRequest req);
}
