package com.jeparca.inditex.priceservice.domain.model;

import lombok.Data;

@Data
public class Price {

	private Double amount;
	private Currency currency;
}
