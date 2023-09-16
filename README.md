# Prerequisites
- Java 17 
- Maven

# Unit Tests
`mvn test`

# Start
`mvn spring-boot:run`

# API
The service will be deployed at port `8080`

The endpoint: `POST /calculate`

# Info
I used information from [this website](https://www.ratehub.ca/mortgage-payment-options) to understand how to calculate bi-weekly and accelerated bi-weekly payments.

# UI
Please use Swagger to test API

http://localhost:8080/swagger-ui/index.html

Examples
![](./images/UI.png)

## Happy Path
### Monthly
![](./images/monthly.png)

### Bi-weekly
![](./images/biweekly.png)

### Accelerated Bi-weekly
![](./images/accelerated_biweekly.png)

## Examples of Exceptions
### Property price is 0
![](./images/error_zero.png)
### Down payment is too small
![](./images/error_small_downpayment.png)
### Annual interest rate is negative
![](./images/error_negative.png)
### Amortization period is wrong
![](./images/error_wrong_period.png)
### Payment schedule yype is NULL
![](./images/error_null.png)

