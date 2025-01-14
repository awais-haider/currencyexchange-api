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



To improve the readability and structure of the UML class diagram section in your `README.md`, you can use Markdown tables to represent the classes and their relationships. Here is an example of how you can format it:

```markdown
## UML Classes

### CalculationController
| Field | Type | Description |
|-------|------|-------------|
| discountService | DiscountService | - |
| exchangeService | ExchangeService | - |

| Method | Return Type | Description |
|--------|-------------|-------------|
| calculatePayableAmount(bill: Bill) | Mono<ResponseEntity<CalculatedDiscountResponse>> | - |

### DiscountService
| Method | Return Type | Description |
|--------|-------------|-------------|
| calculateDiscount(bill: Bill) | double | - |

### ExchangeService
| Method | Return Type | Description |
|--------|-------------|-------------|
| getExchangeRates(baseCurrency: String) | Mono<Map<String, Double>> | - |
| convertCurrency(amount: double, originalCurrency: String, targetCurrency: String) | Mono<Double> | - |

### DiscountServiceImpl
| Method | Return Type | Description |
|--------|-------------|-------------|
| calculateDiscount(bill: Bill) | double | - |

### ExchangeServiceImpl
| Field | Type | Description |
|-------|------|-------------|
| webClient | WebClient | - |
| currencyApiKey | String | - |

| Method | Return Type | Description |
|--------|-------------|-------------|
| getExchangeRates(baseCurrency: String) | Mono<Map<String, Double>> | - |
| convertCurrency(amount: double, originalCurrency: String, targetCurrency: String) | Mono<Double> | - |

### Bill
| Field | Type | Description |
|-------|------|-------------|
| user | User | - |
| items | List<Item> | - |
| totalAmount | double | - |
| originalCurrency | String | - |
| targetCurrency | String | - |

| Method | Return Type | Description |
|--------|-------------|-------------|
| user() | User | - |
| items() | List<Item> | - |
| totalAmount() | double | - |
| originalCurrency() | String | - |
| targetCurrency() | String | - |

### User
| Field | Type | Description |
|-------|------|-------------|
| type | String | - |
| tenure | int | - |

| Method | Return Type | Description |
|--------|-------------|-------------|
| type() | String | - |
| tenure() | int | - |

### Item
| Field | Type | Description |
|-------|------|-------------|
| category | String | - |
| price | double | - |

| Method | Return Type | Description |
|--------|-------------|-------------|
| category() | String | - |
| price() | double | - |

### CalculatedDiscountResponse
| Field | Type | Description |
|-------|------|-------------|
| finalPayableAmount | double | - |

| Method | Return Type | Description |
|--------|-------------|-------------|
| finalPayableAmount() | double | - |
```

This format uses tables to clearly present the fields and methods of each class, making it easier to read and understand.


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