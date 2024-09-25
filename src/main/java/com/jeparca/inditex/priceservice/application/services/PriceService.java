package com.jeparca.inditex.priceservice.application.services;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jeparca.inditex.priceservice.application.exceptions.NotFoundException;
import com.jeparca.inditex.priceservice.application.mappers.PriceMapper;
import com.jeparca.inditex.priceservice.application.port.in.GetProductPriceUseCase;
import com.jeparca.inditex.priceservice.domain.model.ProductPrice;
import com.jeparca.inditex.priceservice.domain.ports.repository.ProductPriceRepository;
import com.jeparca.inditex.priceservice.model.v1.PriceDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PriceService implements GetProductPriceUseCase {

	private final ProductPriceRepository productPriceRepository;
	private final PriceMapper priceMapper;

	@Override
	public PriceDTO getProductPriceByBrandAndProductIdAndApplicationDate(Long brandId, Long productId,
			OffsetDateTime applicationDate) {
		
		Optional<ProductPrice> productPrice = productPriceRepository.getApplicablePrice(applicationDate, brandId, productId);
		
		if(productPrice.isEmpty()) {
			throw new NotFoundException(productId, brandId, DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(applicationDate));
		}
		
		return priceMapper.fromProductPriceToPriceDto(productPrice.get());
	}
	
	
	
}
