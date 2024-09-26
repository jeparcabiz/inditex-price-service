package com.jeparca.inditex.priceservice.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Price {

	private Double amount;
	private Currency currency;
}
