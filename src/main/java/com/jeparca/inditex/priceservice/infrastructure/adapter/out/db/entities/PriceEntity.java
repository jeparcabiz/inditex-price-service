package com.jeparca.inditex.priceservice.infrastructure.adapter.out.db.entities;

import java.time.LocalDateTime;

import com.jeparca.inditex.priceservice.domain.model.Currency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Table(name = "PRICES")
@Entity
public class PriceEntity {
	
	@Id
	@Column(name = "PRICE_LIST")
	private Long priceList;
	@Column(name = "BRAND_ID")
	private Long brandId;
	@Column(name = "PRODUCT_ID")
	private Long productId;
	@Column(name = "START_DATE")
	private LocalDateTime startDate;
	@Column(name = "END_DATE")
	private LocalDateTime endDate;
	@Column(name = "PRIORITY")
	private Long priority;
	@Column(name = "PRICE")
	private Double price;
	@Enumerated(EnumType.STRING)
	@Column(name = "CURR")
	private Currency currency;

}
