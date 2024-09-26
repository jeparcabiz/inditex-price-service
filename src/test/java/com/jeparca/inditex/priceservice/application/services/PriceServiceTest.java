package com.jeparca.inditex.priceservice.application.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.jeparca.inditex.priceservice.application.exceptions.NotFoundException;
import com.jeparca.inditex.priceservice.application.mappers.PriceMapper;
import com.jeparca.inditex.priceservice.domain.model.ProductPrice;
import com.jeparca.inditex.priceservice.domain.ports.repository.ProductPriceRepository;
import com.jeparca.inditex.priceservice.model.v1.PriceDTO;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

	@InjectMocks
	private PriceService priceService;

	@Mock
	private ProductPriceRepository productPriceRepository;
	@Mock
	private PriceMapper priceMapper;

	@Test
	public void shouldCallToRepositoryAndMapper() {
		given(productPriceRepository.getApplicablePrice(any(), any(), any()))
				.willReturn(Optional.of(mock(ProductPrice.class)));
		given(priceMapper.fromProductPriceToPriceDto(any())).willReturn(mock(PriceDTO.class));

		priceService.getProductPriceByBrandAndProductIdAndApplicationDate(3L, 141L, OffsetDateTime.now());

		verify(productPriceRepository, times(1)).getApplicablePrice(any(), any(), any());
		verify(priceMapper, times(1)).fromProductPriceToPriceDto(any());
	}

	@Test
	public void shouldThrowNotFoundException() {
		given(productPriceRepository.getApplicablePrice(any(), any(), any())).willReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> priceService
				.getProductPriceByBrandAndProductIdAndApplicationDate(32L, 181L, OffsetDateTime.now()));

		verify(productPriceRepository, times(1)).getApplicablePrice(any(), any(), any());
		verify(priceMapper, times(0)).fromProductPriceToPriceDto(any());
	}
}
