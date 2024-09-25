package com.jeparca.inditex.priceservice.application.mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.jeparca.inditex.priceservice.domain.model.ProductPrice;
import com.jeparca.inditex.priceservice.infrastructure.adapter.out.db.entities.PriceEntity;
import com.jeparca.inditex.priceservice.model.v1.PriceDTO;

@Mapper(componentModel = "spring")
public interface PriceMapper {
	
	@Mapping(target = "priceId", source = "id")
	@Mapping(target = "productId", source = "product.id")
	@Mapping(target = "brandId", source = "brand.id")
	@Mapping(target = "price", source = "price.amount")
	@Mapping(target = "currency", source = "price.currency")
	PriceDTO fromProductPriceToPriceDto(ProductPrice productPrice);
	
	@Mapping(target = "id", source = "priceList")
	@Mapping(target = "product.id", source = "productId")
	@Mapping(target = "brand.id", source = "brandId")
	@Mapping(target = "price.amount", source = "price")
	@Mapping(target = "price.currency", source = "currency")
	@Mapping(target = "startDate", source = "startDate", qualifiedByName = "convertLocalToOffsetDateTime")
	@Mapping(target = "endDate", source = "endDate", qualifiedByName = "convertLocalToOffsetDateTime")
	ProductPrice fromPriceEntityToProductPrice(PriceEntity priceEntity);
	
	@Named("convertLocalToOffsetDateTime")
	default OffsetDateTime fromLocalDateTime(LocalDateTime localDateTime) {
		return localDateTime.atOffset(ZoneOffset.UTC);
	}

}
