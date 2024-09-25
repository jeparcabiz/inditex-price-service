package com.jeparca.inditex.priceservice.domain.model;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class ProductPrice {

	private Long id;
	private Brand brand;
	private Product product;
	private OffsetDateTime startDate;
	private OffsetDateTime endDate;
	private Long priority;
	private Price price;
	
}
