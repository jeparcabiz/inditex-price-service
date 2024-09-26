package com.jeparca.inditex.priceservice.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Brand {

	private Long id;
	private String name;
	
}
