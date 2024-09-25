Feature: List prices of articles
	Scenario: Retrieving price of existing article - Case 1
		Given the application date is 2020-06-14T10:00:00Z
			And the brand id is 1
			And the article id is 35455
		When request price of article
		Then status code should be 200 
			And the price returned should be 35.50
			And the currency should be 'EUR'
			And the brand id should be 1
			And the article id should be 35455
			And the price list should be 1
			And the start date should be 2020-06-14T00:00:00Z
			And the end date should be 2020-12-31T23:59:59Z