# Currency Exchange API

## Overview
This project is a Currency Exchange API built with Java and Spring Boot. It includes services for calculating discounts and converting currencies.

## Prerequisites
- Java 17 or higher
- Gradle 7.0 or higher

## Setup
1. Clone the repository:
    ```sh
    git clone https://github.com/ztasolutions/currency-exchange-api.git
    cd currency-exchange-api
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


##License
This project is licensed under the MIT License.