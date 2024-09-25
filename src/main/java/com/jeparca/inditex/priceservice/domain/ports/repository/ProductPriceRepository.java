package com.jeparca.inditex.priceservice.domain.ports.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import com.jeparca.inditex.priceservice.domain.model.ProductPrice;

public interface ProductPriceRepository {
	
	Optional<ProductPrice> getApplicablePrice(OffsetDateTime applicationDate, Long brandId, Long productId);

}
