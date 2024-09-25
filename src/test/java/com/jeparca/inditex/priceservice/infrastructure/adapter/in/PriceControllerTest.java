package com.jeparca.inditex.priceservice.infrastructure.adapter.in;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.jeparca.inditex.priceservice.application.exceptions.NotFoundException;
import com.jeparca.inditex.priceservice.application.port.in.GetProductPriceUseCase;
import com.jeparca.inditex.priceservice.model.v1.PriceDTO;
import com.jeparca.inditex.priceservice.model.v1.PriceDTO.CurrencyEnum;

@ActiveProfiles("test")
@WebMvcTest(PriceController.class)
public class PriceControllerTest {

	private final static String PRICES_V1_ENDPOINT = "/v1/prices";
	private final static String APPLICATION_DATE_QUERY_PARAM_NAME = "applicationDate";
	private final static String BRAND_ID_QUERY_PARAM_NAME = "brandId";
	private final static String PRODUCT_ID_QUERY_PARAM_NAME = "productId";
	private final static DateTimeFormatter DTF = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GetProductPriceUseCase getProductPriceUseCase;

	@Test
	public void shouldReturnProductPrice() throws Exception {
		OffsetDateTime startDate = OffsetDateTime.now().minusDays(150);
		OffsetDateTime endDate = OffsetDateTime.now().plusDays(30);

		PriceDTO price = PriceDTO.builder().brandId(1L).currency(CurrencyEnum.USD).endDate(endDate).startDate(startDate)
				.price(239.93).priceId(23L).productId(23111L).build();

		given(getProductPriceUseCase.getProductPriceByBrandAndProductIdAndApplicationDate(any(), any(), any()))
				.willReturn(price);

		mockMvc.perform(
				get(PRICES_V1_ENDPOINT).queryParam(APPLICATION_DATE_QUERY_PARAM_NAME, DTF.format(OffsetDateTime.now()))
						.queryParam(BRAND_ID_QUERY_PARAM_NAME, "1").queryParam(PRODUCT_ID_QUERY_PARAM_NAME, "23111")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json("""
						{
							"priceId": 23,
							"productId": 23111,
							"brandId": 1,
							"startDate": "%s",
							"endDate": "%s",
							"price": 239.93,
							"currency": "USD"
						}
						""".formatted(DTF.format(startDate), DTF.format(endDate))));
	}

	@Test
	public void shouldThrowException_missingParameters() throws Exception {
		mockMvc.perform(get(PRICES_V1_ENDPOINT).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message", is("Bad parameters")));
	}

	@Test
	public void shouldThrowException_notFoundPrice() throws Exception {
		given(getProductPriceUseCase.getProductPriceByBrandAndProductIdAndApplicationDate(any(), any(), any()))
				.willThrow(NotFoundException.class);

		mockMvc.perform(
				get(PRICES_V1_ENDPOINT).queryParam(APPLICATION_DATE_QUERY_PARAM_NAME, DTF.format(OffsetDateTime.now()))
						.queryParam(BRAND_ID_QUERY_PARAM_NAME, "1").queryParam(PRODUCT_ID_QUERY_PARAM_NAME, "23111")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldThrowException_internalServerError() throws Exception {
		given(getProductPriceUseCase.getProductPriceByBrandAndProductIdAndApplicationDate(any(), any(), any()))
				.willThrow(RuntimeException.class);

		mockMvc.perform(
				get(PRICES_V1_ENDPOINT).queryParam(APPLICATION_DATE_QUERY_PARAM_NAME, DTF.format(OffsetDateTime.now()))
						.queryParam(BRAND_ID_QUERY_PARAM_NAME, "1").queryParam(PRODUCT_ID_QUERY_PARAM_NAME, "23111")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

}
