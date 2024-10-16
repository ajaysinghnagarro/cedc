# Currency Exchange and Discount Calculation Service

## Problem Statement

Develop a Spring Boot application that integrates with a third-party currency exchange API to retrieve real-time exchange rates. The application should calculate the total payable amount for a bill in a specified currency after applying applicable discounts. The discounts include:

- **Employee Discount:** 30%
- **Affiliate Discount:** 10%
- **Customer Discount:** 5% (for customers who have been with the store for more than 2 years)
- **$5 discount for every $100 spent** on the bill.

The percentage-based discounts do not apply to groceries, and a user can get only one of the percentage-based discounts on a bill. After applying the discounts, the bill should be converted into the target currency using the exchange rate.

### Key Features

- Apply various discounts to non-grocery items
- Convert the total payable amount from the original currency to the target currency using real-time exchange rates
- Secure API using JWT-based authentication

## Solution

This application exposes an API endpoint `/api/calculate` where users can submit their bill details, including items, user type, customer tenure, original currency, and target currency. The API calculates the total payable amount by applying applicable discounts and converting the total amount to the target currency.

The application follows a service-oriented architecture and includes components for discount calculation, currency conversion, and authentication.

### Technologies Used
- Spring Boot
- RestTemplate for external API integration
- JUnit 5 and Mockito for unit testing

## Project Structure

```bash
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com.assignement.cedc
│   │   │   │   ├── controller
│   │   │   │   │   └── BillController.java       # Exposes API endpoint
│   │   │   │   ├── dto
│   │   │   │   │   └── BillRequest.java          # Bill request DTO
│   │   │   │   │   └── BillResponse.java         # Bill response DTO
│   │   │   │   ├── service
│   │   │   │   │   └── BillService.java          # Interface for BillService
│   │   │   │   │   └── BillServiceImpl.java      # Business logic for bill calculation
│   │   │   │   │   └── DiscountService.java      # Interface for discount calculation
│   │   │   │   │   └── CurrencyExchangeService.java # Interface for currency conversion
│   │   │   │   ├── config
│   │   │   │   │   └── SecurityConfig.java       # Security configuration (JWT Authentication)
│   └── test                                      # Unit tests
│       ├── java
│       │   ├── com.assignement.cedc
│       │   │   ├── service
│       │   │   │   └── BillServiceImplTest.java  # Unit tests for BillServiceImpl
│       │   │   ├── controller
│       │   │   │   └── BillControllerTest.java   # Unit tests for BillController
├── pom.xml                                        # Maven project file
└── README.md
