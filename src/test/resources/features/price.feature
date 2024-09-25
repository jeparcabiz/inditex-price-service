Feature: List prices of products
	Scenario: Retrieving price of existing product - Case 1
		Given the application date is 2020-06-14T10:00:00Z
			And the brand id is 1
			And the product id is 35455
		When request price of product
		Then status code should be 200 
			And the price returned should be 35.50
			And the currency should be 'EUR'
			And the brand id should be 1
			And the product id should be 35455
			And the price list should be 1
			And the start date should be 2020-06-14T00:00:00Z
			And the end date should be 2020-12-31T23:59:59Z
			
	Scenario: Retrieving price of existing product - Case 2
		Given the application date is 2020-06-14T16:00:00Z
			And the brand id is 1
			And the product id is 35455
		When request price of product
		Then status code should be 200 
			And the price returned should be 25.45
			And the currency should be 'EUR'
			And the brand id should be 1
			And the product id should be 35455
			And the price list should be 2
			And the start date should be 2020-06-14T15:00:00Z
			And the end date should be 2020-06-14T18:30:00Z
			
	Scenario: Retrieving price of existing product - Case 3
		Given the application date is 2020-06-14T21:00:00Z
			And the brand id is 1
			And the product id is 35455
		When request price of product
		Then status code should be 200 
			And the price returned should be 35.50
			And the currency should be 'EUR'
			And the brand id should be 1
			And the product id should be 35455
			And the price list should be 1
			And the start date should be 2020-06-14T00:00:00Z
			And the end date should be 2020-12-31T23:59:59Z
			
	Scenario: Retrieving price of existing product - Case 4
		Given the application date is 2020-06-15T10:00:00Z
			And the brand id is 1
			And the product id is 35455
		When request price of product
		Then status code should be 200 
			And the price returned should be 30.50
			And the currency should be 'EUR'
			And the brand id should be 1
			And the product id should be 35455
			And the price list should be 3
			And the start date should be 2020-06-15T00:00:00Z
			And the end date should be 2020-06-15T11:00:00Z
			
	Scenario: Retrieving price of existing product - Case 5
		Given the application date is 2020-06-16T21:00:00Z
			And the brand id is 1
			And the product id is 35455
		When request price of product
		Then status code should be 200 
			And the price returned should be 38.95
			And the currency should be 'EUR'
			And the brand id should be 1
			And the product id should be 35455
			And the price list should be 4
			And the start date should be 2020-06-15T16:00:00Z
			And the end date should be 2020-12-31T23:59:59Z
			
	Scenario: Retrieving price of non existing product
		Given the application date is 2020-06-14T10:00:00Z
			And the brand id is 1
			And the product id is 35453
		When request price of product
		Then status code should be 404
			And the error message should be 'No price was found for product 35453 of brand 1 in application date 2020-06-14T10:00:00Z'
