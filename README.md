# Currency Exchange API

## Overview
This project is a Currency Exchange API built with Java and Spring Boot. It includes services for calculating discounts and converting currencies.

## Currency API Used (Exchange Rate API)
https://v6.exchangerate-api.com/v6/your-api-key/latest/USD

## Authentication:
Implemented authentication for the exposed endpoints 
**/api/calculate** using basic authentication. **The username is admin and the password is 1234.**

## Prerequisites
- Java 17 or higher
- Gradle 7.0 or higher

## Setup
1. Clone the repository:
    ```sh
    git clone https://github.com/awais-haider/currencyexchange-api.git
    cd currencyexchange-api
    ```

2. Build the project:
    ```sh
    ./gradlew build
    ```

## Running the Application
To run the application, use the following command:
```sh
./gradlew bootRun
```

The application will start on http://localhost:8080.
## Running Tests
To run the tests, use the following command:
```sh
./gradlew test
```

## Generating Coverage Reports
To generate test coverage reports, use the following command:

```sh
./gradlew jacocoTestReport
```

The coverage report will be generated in build/reports/jacoco/test/html/index.html.

## Static Code Analysis
Sonar Cloud account created and used
```sh
./gradlew sonarqube
```


## Endpoint
The main endpoint integrated with this project is:  
Calculate Payable Amount API (with basic user authentication username: admin, password: 1234)
URL: /api/calculate
Method: POST
Request Body:
{
"items": [
{"name": "TV", "category": "Electronics", "price": 299.99},
{"name": "ModelsForWriting", "category": "Books", "price": 19.99}
],
"totalAmount": 319.98,
"user": {"type": "Regular", "tenure": 5},
"originalCurrency": "USD",
"targetCurrency": "EUR"
}


Response:
{
"finalPayableAmount": 282.392
}




## UML Classes

+-----------------------------+
|      CalculationController  |
+-----------------------------+
| - discountService: DiscountService |
| - exchangeService: ExchangeService |
+-----------------------------+
| + calculatePayableAmount(bill: Bill): Mono<ResponseEntity<CalculatedDiscountResponse>> |
+-----------------------------+

+-----------------------------+
|      DiscountService        |
+-----------------------------+
| + calculateDiscount(bill: Bill): double |
+-----------------------------+

+-----------------------------+
|      ExchangeService        |
+-----------------------------+
| + getExchangeRates(baseCurrency: String): Mono<Map<String, Double>> |
| + convertCurrency(amount: double, originalCurrency: String, targetCurrency: String): Mono<Double> |
+-----------------------------+

+-----------------------------+
|      DiscountServiceImpl    |
+-----------------------------+
| + calculateDiscount(bill: Bill): double |
+-----------------------------+

+-----------------------------+
|      ExchangeServiceImpl    |
+-----------------------------+
| - webClient: WebClient      |
| - currencyApiKey: String    |
+-----------------------------+
| + getExchangeRates(baseCurrency: String): Mono<Map<String, Double>> |
| + convertCurrency(amount: double, originalCurrency: String, targetCurrency: String): Mono<Double> |
+-----------------------------+

+-----------------------------+
|      Bill                   |
+-----------------------------+
| - user: User                |
| - items: List<Item>         |
| - totalAmount: double       |
| - originalCurrency: String  |
| - targetCurrency: String    |
+-----------------------------+
| + user(): User              |
| + items(): List<Item>       |
| + totalAmount(): double     |
| + originalCurrency(): String|
| + targetCurrency(): String  |
+-----------------------------+

+-----------------------------+
|      User                   |
+-----------------------------+
| - type: String              |
| - tenure: int               |
+-----------------------------+
| + type(): String            |
| + tenure(): int             |
+-----------------------------+

+-----------------------------+
|      Item                   |
+-----------------------------+
| - category: String          |
| - price: double             |
+-----------------------------+
| + category(): String        |
| + price(): double           |
+-----------------------------+

+-----------------------------+
|      CalculatedDiscountResponse |
+-----------------------------+
| - finalPayableAmount: double |
+-----------------------------+
| + finalPayableAmount(): double |
+-----------------------------+


## Bonus Activities:
•	Create build scripts using Gradle to: 
Build the project from the command line.    **./gradlew build**
•	Run unit tests and generate code coverage reports. **./gradlew test ./gradlew jacocoTestReport**
•	Generate a SonarQube report for the code quality summary 	Run static code analysis such as linting.  **./gradlew sonarqube**
•	Implement caching for exchange rates to reduce API calls. Implemented caching using Caffeine cache
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("exchangeRates");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100));
        return cacheManager;
    }
}