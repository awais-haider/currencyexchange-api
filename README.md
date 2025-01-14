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
- Gradle 8.x or higher

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
| Field | Type | Visibility | Description |
|-------|------|------------|-------------|
| discountService | DiscountService | public | - |
| exchangeService | ExchangeService | public | - |

| Method | Return Type | Visibility | Description |
|--------|-------------|------------|-------------|
| calculatePayableAmount(bill: Bill) | Mono<ResponseEntity<CalculatedDiscountResponse>> | public | - |

### DiscountService
| Method | Return Type | Visibility | Description |
|--------|-------------|------------|-------------|
| calculateDiscount(bill: Bill) | double | public | - |

### ExchangeService
| Method | Return Type | Visibility | Description |
|--------|-------------|------------|-------------|
| getExchangeRates(baseCurrency: String) | Mono<Map<String, Double>> | public | - |
| convertCurrency(amount: double, originalCurrency: String, targetCurrency: String) | Mono<Double> | public | - |

### DiscountServiceImpl
| Method | Return Type | Visibility | Description |
|--------|-------------|------------|-------------|
| calculateDiscount(bill: Bill) | double | public | - |

### ExchangeServiceImpl
| Field | Type | Visibility | Description |
|-------|------|------------|-------------|
| webClient | WebClient | public | - |
| currencyApiKey | String | public | - |

| Method | Return Type | Visibility | Description |
|--------|-------------|------------|-------------|
| getExchangeRates(baseCurrency: String) | Mono<Map<String, Double>> | public | - |
| convertCurrency(amount: double, originalCurrency: String, targetCurrency: String) | Mono<Double> | public | - |

### Bill
| Field | Type | Visibility | Description |
|-------|------|------------|-------------|
| user | User | public | - |
| items | List<Item> | public | - |
| totalAmount | double | public | - |
| originalCurrency | String | public | - |
| targetCurrency | String | public | - |

| Method | Return Type | Visibility | Description |
|--------|-------------|------------|-------------|
| user() | User | public | - |
| items() | List<Item> | public | - |
| totalAmount() | double | public | - |
| originalCurrency() | String | public | - |
| targetCurrency() | String | public | - |

### User
| Field | Type | Visibility | Description |
|-------|------|------------|-------------|
| type | String | public | - |
| tenure | int | public | - |

| Method | Return Type | Visibility | Description |
|--------|-------------|------------|-------------|
| type() | String | public | - |
| tenure() | int | public | - |

### Item
| Field | Type | Visibility | Description |
|-------|------|------------|-------------|
| category | String | public | - |
| price | double | public | - |

| Method | Return Type | Visibility | Description |
|--------|-------------|------------|-------------|
| category() | String | public | - |
| price() | double | public | - |

### CalculatedDiscountResponse
| Field | Type | Visibility | Description |
|-------|------|------------|-------------|
| finalPayableAmount | double | public | - |

| Method | Return Type | Visibility | Description |
|--------|-------------|------------|-------------|
| finalPayableAmount() | double | public | - |
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
