# Currency Converter API

A simple API for storing and retrieving purchase transactions with currency conversion capabilities.

## Getting Started

Follow these instructions to get the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Docker
- Docker Compose
- Maven

### Running with Docker Compose

1. Clone the repository.
   ```bash
   git clone https://github.com/wilianhw/currency-converter.git
2. Navigate to the project directory
   ```bash
	  cd currency-converter-api
3. Run the command:
   ```bash
	  mvn install -DskipTests
4. Build and run the application with Docker Compose.
   ```bash
   docker-compose up --build

The application will be accessible at [http://localhost:8080](http://localhost:8080/).

A simple API for storing and retrieving purchase transactions with currency conversion capabilities.

## Requirements

### Requirement #1: Store a Purchase Transaction

To register a new purchase transaction, use the following endpoint:

### Request Body

POST - `http://localhost:8080/transactions`

```json
{
  "description": String,
  "transactionDate": Date,
  "purchaseAmount": Number
}
```

Example:

```json
{
  "description": "Teste",
  "transactionDate": "2023-11-29",
  "purchaseAmount": 14.56
}
```

### Requirement #2: Retrieve a Purchase Transaction in a Specified Country’s Currency

To retrieve details of a purchase transaction with currency conversion for a specific country, use the following
endpoint:

GET - `http://localhost:8080/transactions/{transaction_id}?country={country_currency}`

- `transaction_id`: The unique identifier of the purchase transaction.
- `country_code`: The desired country's code for currency conversion (e.g., `Canada-Dollar`).

Example URL:

`http://localhost:8080/transactions/1?country=Canada-Dollar`

### Supported Countries and Currency Codes

The API supports currency conversion for transactions in various countries. Refer to
the [Treasury Reporting Rates of Exchange](https://fiscaldata.treasury.gov/datasets/treasury-reporting-rates-exchange/treasury-reporting-rates-of-exchange)
dataset for a comprehensive list of supported countries and their respective country currency .

Ensure that the `country` parameter used in the API requests corresponds to the accepted country currency provided by
the Treasury Reporting Rates of Exchange dataset.
