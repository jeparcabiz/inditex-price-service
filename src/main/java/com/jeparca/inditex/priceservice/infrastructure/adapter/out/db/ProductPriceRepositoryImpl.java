package com.jeparca.inditex.priceservice.infrastructure.adapter.out.db;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jeparca.inditex.priceservice.application.mappers.PriceMapper;
import com.jeparca.inditex.priceservice.domain.model.ProductPrice;
import com.jeparca.inditex.priceservice.domain.ports.repository.ProductPriceRepository;
import com.jeparca.inditex.priceservice.infrastructure.adapter.out.db.entities.PriceEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductPriceRepositoryImpl implements ProductPriceRepository {

	private final ProductPriceDbRepository repository;
	private final PriceMapper priceMapper;
	
	@Override
	public Optional<ProductPrice> getApplicablePrice(OffsetDateTime applicationDate, Long brandId, Long productId) {
		LocalDateTime localApplicationDate = applicationDate.toLocalDateTime();
		Optional<PriceEntity> price = repository
				.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(brandId, productId,
						localApplicationDate, localApplicationDate);
		if(price.isEmpty()) {
			return Optional.empty();
		}
		
		return Optional.of(priceMapper.fromPriceEntityToProductPrice(price.get()));
	}

}
