package com.jeparca.inditex.priceservice.application.exceptions;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2465457378427595414L;

	private static final String NOT_FOUND_EXCEPTION = "No price was found for product %s of brand %s in application date %s";

	public NotFoundException(Long productId, Long brandId, String applicationDate) {
		super(String.format(NOT_FOUND_EXCEPTION, productId, brandId, applicationDate));
	}

	public NotFoundException(String msg) {
		super(msg);
	}
}
