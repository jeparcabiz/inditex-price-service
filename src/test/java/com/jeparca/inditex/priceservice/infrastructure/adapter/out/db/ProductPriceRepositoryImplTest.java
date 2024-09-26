package com.jeparca.inditex.priceservice.infrastructure.adapter.out.db;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.jeparca.inditex.priceservice.application.mappers.PriceMapper;
import com.jeparca.inditex.priceservice.domain.model.ProductPrice;
import com.jeparca.inditex.priceservice.infrastructure.adapter.out.db.entities.PriceEntity;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ProductPriceRepositoryImplTest {

	@InjectMocks
	private ProductPriceRepositoryImpl productPriceRepositoryImpl;

	@Mock
	private ProductPriceDbRepository productPriceDbRepository;
	@Mock
	private PriceMapper priceMapper;

	@Test
	public void shouldCallToRepositoryAndMapper() {
		given(productPriceDbRepository
				.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
						any(), any(), any(), any()))
				.willReturn(Optional.of(mock(PriceEntity.class)));
		given(priceMapper.fromPriceEntityToProductPrice(any())).willReturn(mock(ProductPrice.class));

		Optional<ProductPrice> applicablePrice = productPriceRepositoryImpl.getApplicablePrice(OffsetDateTime.now(), 28L, 289L);

		verify(productPriceDbRepository, times(1))
				.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
						any(), any(), any(), any());
		verify(priceMapper, times(1)).fromPriceEntityToProductPrice(any());
		assertTrue(applicablePrice.isPresent());
	}
	
	@Test
	public void shouldCallToRepository_emptyResponseFromRepository() {
		given(productPriceDbRepository
				.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
						any(), any(), any(), any()))
				.willReturn(Optional.empty());
		Optional<ProductPrice> applicablePrice = productPriceRepositoryImpl.getApplicablePrice(OffsetDateTime.now(), 28L, 289L);

		verify(productPriceDbRepository, times(1))
				.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
						any(), any(), any(), any());
		verify(priceMapper, times(0)).fromPriceEntityToProductPrice(any());
		assertTrue(applicablePrice.isEmpty());
	}

}
