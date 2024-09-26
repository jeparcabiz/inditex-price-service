package com.jeparca.inditex.priceservice.infrastructure.adapter.out.db;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeparca.inditex.priceservice.infrastructure.adapter.out.db.entities.PriceEntity;

interface ProductPriceDbRepository extends JpaRepository<PriceEntity, Long> {

	Optional<PriceEntity> findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(Long brandId,
			Long productId, LocalDateTime applicationDateStartDate, LocalDateTime applicationDateEndDate);

}
