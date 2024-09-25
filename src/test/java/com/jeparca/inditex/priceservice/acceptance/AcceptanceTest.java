package com.jeparca.inditex.priceservice.acceptance;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.jeparca.inditex.priceservice.model.v1.ErrorDTO;
import com.jeparca.inditex.priceservice.model.v1.PriceDTO;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class AcceptanceTest {

	private final static String PRICES_V1_ENDPOINT = "/v1/prices";
	private final static String APPLICATION_DATE_QUERY_PARAM_NAME = "applicationDate";
	private final static String BRAND_ID_QUERY_PARAM_NAME = "brandId";
	private final static String PRODUCT_ID_QUERY_PARAM_NAME = "productId";

	@LocalServerPort
	private Integer port;

	// Request parameters
	private OffsetDateTime applicationDate;
	private Integer brandId;
	private Integer productId;

	// Response
	private int statusCode;
	private PriceDTO price;
	private ErrorDTO error;

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost:" + port;
	}

	@Given("the application date is {timestamp}")
	public void application_date_is(OffsetDateTime applicationDate) {
		this.applicationDate = applicationDate;
	}

	@Given("the brand id is {int}")
	public void brand_id_is(Integer brandId) {
		this.brandId = brandId;
	}

	@Given("the product id is {int}")
	public void product_id_is(Integer productId) {
		this.productId = productId;
	}

	@When("request price of product")
	public void request_price() {
		String applicationDate = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this.applicationDate);
		Response response = given().queryParams(APPLICATION_DATE_QUERY_PARAM_NAME, applicationDate,
				BRAND_ID_QUERY_PARAM_NAME, this.brandId, PRODUCT_ID_QUERY_PARAM_NAME, this.productId).when()
				.get(PRICES_V1_ENDPOINT);
		this.statusCode = response.statusCode();
		if(this.statusCode == HttpStatus.OK.value()) {
			this.price = response.getBody().as(PriceDTO.class);
		} else {
			this.error = response.getBody().as(ErrorDTO.class);
		}
	}
	
	@Then("status code should be {int}")
	public void status_code_should_be(int statusCode) throws Exception {
		assertEquals(statusCode, this.statusCode);
	}
	
	@Then("the price returned should be {double}")
	public void price_should_be(Double price) throws Exception {
		assertEquals(price, this.price.getPrice());
	}
	
	@Then("the currency should be {string}")
	public void currency_should_be(String currency) throws Exception {
		assertEquals(currency, this.price.getCurrency().getValue());
	}
	
	@Then("the brand id should be {int}")
	public void brand_id_should_be(Integer brandId) throws Exception {
		assertEquals(brandId, this.price.getBrandId().intValue());
	}
	
	@Then("the product id should be {int}")
	public void product_id_should_be(Integer productId) throws Exception {
		assertEquals(productId, this.price.getProductId().intValue());
	}
	
	@Then("the price list should be {long}")
	public void price_list_should_be(Long priceList) throws Exception {
		assertEquals(priceList, this.price.getPriceId());
	}
	
	@Then("the start date should be {timestamp}")
	public void start_date_should_be(OffsetDateTime startDate) throws Exception {
		assertEquals(startDate, this.price.getStartDate());
	}
	
	@Then("the end date should be {timestamp}")
	public void end_date_should_be(OffsetDateTime endDate) throws Exception {
		assertEquals(endDate, this.price.getEndDate());
	}
	
	@Then("the error message should be {string}")
	public void error_message_should_be(String errorMessage) throws Exception {
		assertEquals(errorMessage, this.error.getMessage());
	}

}
