package com.jeparca.inditex.priceservice.infrastructure.adapter.in;

import org.springframework.web.bind.annotation.RestController;

import com.jeparca.inditex.priceservice.api.v1.PricesApi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Inditex eCommerce Price Service", description = "This is a sample microservice to get price of articles", version = "v1"))
@RestController
public class PriceController implements PricesApi {

}
