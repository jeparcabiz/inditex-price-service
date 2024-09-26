package com.jeparca.inditex.priceservice.application.port.in;

import java.time.OffsetDateTime;

import com.jeparca.inditex.priceservice.model.v1.PriceDTO;

public interface GetProductPriceUseCase {

	PriceDTO getProductPriceByBrandAndProductIdAndApplicationDate(Long brandId, Long productId, OffsetDateTime applicationDate);
	
}
