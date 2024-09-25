package com.jeparca.inditex.priceservice.infrastructure.adapter.out.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.jeparca.inditex.priceservice.domain.model.Currency;
import com.jeparca.inditex.priceservice.infrastructure.adapter.out.db.entities.PriceEntity;

@ActiveProfiles("test")
@DataJpaTest
public class ProductPriceDbRepositoryTest {
	
	@Autowired
	private ProductPriceDbRepository repository;
	
	@Test
	public void shouldReturnsAProductPrice_noPriority() {
		LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
		Optional<PriceEntity> priceOpt = repository.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(1L, 35455L, applicationDate, applicationDate);
		
		assertTrue(priceOpt.isPresent());
		PriceEntity price = priceOpt.get();
		
		assertEquals(1L, price.getBrandId());
		assertEquals(35455L, price.getProductId());
		assertEquals(LocalDateTime.of(2020, 6, 14, 0, 0), price.getStartDate());
		assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), price.getEndDate());
		assertEquals(1L, price.getPriceList());
		assertEquals(Currency.EUR, price.getCurrency());
		assertEquals(35.50, price.getPrice());
		assertEquals(0, price.getPriority());
		
	}
	
	@Test
	public void shouldReturnsAProductPrice_withPriority() {
		LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
		Optional<PriceEntity> priceOpt = repository.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(1L, 35455L, applicationDate, applicationDate);
		
		assertTrue(priceOpt.isPresent());
		PriceEntity price = priceOpt.get();
		
		assertEquals(1L, price.getBrandId());
		assertEquals(35455L, price.getProductId());
		assertEquals(LocalDateTime.of(2020, 6, 14, 15, 0), price.getStartDate());
		assertEquals(LocalDateTime.of(2020, 6, 14, 18, 30), price.getEndDate());
		assertEquals(2L, price.getPriceList());
		assertEquals(Currency.EUR, price.getCurrency());
		assertEquals(25.45, price.getPrice());
		assertEquals(1, price.getPriority());
		
	}
	
	@Test
	public void shouldNoReturnsAProductPrice_wrongApplicationDate() {
		LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 13, 16, 0);
		Optional<PriceEntity> priceOpt = repository.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(1L, 35455L, applicationDate, applicationDate);
		
		assertTrue(priceOpt.isEmpty());
	}
	
	@Test
	public void shouldNoReturnsAProductPrice_wrongBrandId() {
		LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
		Optional<PriceEntity> priceOpt = repository.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(2L, 35455L, applicationDate, applicationDate);
		
		assertTrue(priceOpt.isEmpty());
	}
	
	@Test
	public void shouldNoReturnsAProductPrice_wrongProductId() {
		LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
		Optional<PriceEntity> priceOpt = repository.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(1L, 35255L, applicationDate, applicationDate);
		
		assertTrue(priceOpt.isEmpty());
	}

}
