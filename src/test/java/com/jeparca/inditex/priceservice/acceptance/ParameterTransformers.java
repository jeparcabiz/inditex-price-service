package com.jeparca.inditex.priceservice.acceptance;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import io.cucumber.java.ParameterType;

public class ParameterTransformers {

	@ParameterType("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z")
	public OffsetDateTime timestamp(String date) {
		return OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}
	
}
