package com.jeparca.inditex.priceservice.infrastructure.adapter.in;

import java.time.OffsetDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.jeparca.inditex.priceservice.api.v1.PricesApi;
import com.jeparca.inditex.priceservice.application.port.in.GetProductPriceUseCase;
import com.jeparca.inditex.priceservice.model.v1.PriceDTO;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(info = @Info(title = "Inditex eCommerce Price Service", description = "This is a sample microservice to get price of products", version = "v1"))
@RequiredArgsConstructor
@RestController
public class PriceController implements PricesApi {

	private final GetProductPriceUseCase getProductPriceUseCase;
	
	@Override
	public ResponseEntity<PriceDTO> getPrice(OffsetDateTime applicationDate, Long brandId, Long productId) {
		return ResponseEntity.ok(getProductPriceUseCase.getProductPriceByBrandAndProductIdAndApplicationDate(brandId.longValue(), productId.longValue(), applicationDate));
	} 
	
}
