package com.jeparca.inditex.priceservice.application.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.jeparca.inditex.priceservice.domain.model.Brand;
import com.jeparca.inditex.priceservice.domain.model.Currency;
import com.jeparca.inditex.priceservice.domain.model.Price;
import com.jeparca.inditex.priceservice.domain.model.Product;
import com.jeparca.inditex.priceservice.domain.model.ProductPrice;
import com.jeparca.inditex.priceservice.infrastructure.adapter.out.db.entities.PriceEntity;
import com.jeparca.inditex.priceservice.model.v1.PriceDTO;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PriceMapperTest {

	@InjectMocks
	private PriceMapperImpl priceMapper;
	
	@Test
	public void shouldMapProductPriceToProductDto() {
		OffsetDateTime startDate = OffsetDateTime.now().minusDays(365);
		OffsetDateTime endDate = OffsetDateTime.now().plusDays(180);
		Brand brand = Brand.builder().id(8327L).build();
		Price price = Price.builder().amount(324.12).currency(Currency.GBP).build();
		Product product = Product.builder().id(13487L).build();
		ProductPrice productPrice = ProductPrice.builder().brand(brand).endDate(endDate).id(990L).price(price)
				.priority(3L).product(product).startDate(startDate).build();
		
		PriceDTO priceDto = priceMapper.fromProductPriceToPriceDto(productPrice);
		
		assertNotNull(priceDto);
		assertEquals(8327L, priceDto.getBrandId());
		assertEquals(990L, priceDto.getPriceId());
		assertEquals(13487L, priceDto.getProductId());
		assertEquals(startDate, priceDto.getStartDate());
		assertEquals(endDate, priceDto.getEndDate());
		assertEquals(324.12, priceDto.getPrice());
		assertEquals(Currency.GBP.name(), priceDto.getCurrency().getValue());
	}
	
	@Test
	public void shouldMapPriceEntityToProductPrice() {
		LocalDateTime startDate = LocalDateTime.now().minusDays(365);
		LocalDateTime endDate = LocalDateTime.now().plusDays(180);
		
		PriceEntity priceEntity = PriceEntity.builder().brandId(1542L).currency(Currency.EUR).endDate(endDate)
				.price(1488.2).priceList(4L).priority(1L).productId(9L).startDate(startDate).build();
		
		ProductPrice productPrice = priceMapper.fromPriceEntityToProductPrice(priceEntity);
		
		assertNotNull(productPrice);
		assertEquals(4L, productPrice.getId());
		assertEquals(1542L, productPrice.getBrand().getId());
		assertEquals(9L, productPrice.getProduct().getId());
		assertEquals(startDate.atOffset(ZoneOffset.UTC), productPrice.getStartDate());
		assertEquals(endDate.atOffset(ZoneOffset.UTC), productPrice.getEndDate());
		assertEquals(1488.2, productPrice.getPrice().getAmount());
		assertEquals(Currency.EUR.name(), productPrice.getPrice().getCurrency().name());
		assertEquals(1L, productPrice.getPriority());
	}
}
