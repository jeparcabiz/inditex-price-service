package com.jeparca.inditex.priceservice.acceptance;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

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
	private final static String ARTICLE_ID_QUERY_PARAM_NAME = "articleId";

	@LocalServerPort
	private Integer port;

	// Request parameters
	private OffsetDateTime applicationDate;
	private Integer brandId;
	private Integer articleId;

	// Response
	private int statusCode;
	private PriceDTO price;

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

	@Given("the article id is {int}")
	public void article_id_is(Integer articleId) {
		this.articleId = articleId;
	}

	@When("request price of article")
	public void request_price() {
		String applicationDate = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this.applicationDate);
		Response response = given().queryParams(APPLICATION_DATE_QUERY_PARAM_NAME, applicationDate,
				BRAND_ID_QUERY_PARAM_NAME, this.brandId, ARTICLE_ID_QUERY_PARAM_NAME, this.articleId).when()
				.get(PRICES_V1_ENDPOINT);
		this.statusCode = response.statusCode();
		if(this.statusCode == HttpStatus.OK.value()) {
			this.price = response.getBody().as(PriceDTO.class);
		}
	}
	
	@Then("status code should be {int}")
	public void status_code_should_be(int statusCode) throws Exception {
		assertEquals(statusCode, this.statusCode);
	}
	
	@Then("the price returned should be {float}")
	public void price_should_be(Float price) throws Exception {
		assertEquals(price.doubleValue(), this.price.getPrice());
	}
	
	@Then("the currency should be {string}")
	public void currency_should_be(String currency) throws Exception {
		assertEquals(currency, this.price.getCurrency());
	}
	
	@Then("the brand id should be {int}")
	public void brand_id_should_be(Integer brandId) throws Exception {
		assertEquals(brandId, this.price.getBrandId());
	}
	
	@Then("the article id should be {int}")
	public void article_id_should_be(Integer articleId) throws Exception {
		assertEquals(articleId, this.price.getArticleId());
	}
	
	@Then("the price list should be {int}")
	public void price_list_should_be(Integer priceList) throws Exception {
		assertEquals(priceList, this.price.getPriceList());
	}
	
	@Then("the start date should be {timestamp}")
	public void start_date_should_be(OffsetDateTime startDate) throws Exception {
		assertEquals(startDate, this.price.getStartDate());
	}
	
	@Then("the end date should be {timestamp}")
	public void end_date_should_be(OffsetDateTime endDate) throws Exception {
		assertEquals(endDate, this.price.getEndDate());
	}

}
