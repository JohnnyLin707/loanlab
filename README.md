# CSIS 3275 Loan Lab

A Spring Boot MVC client loan management system built for CSIS 3275 Software Engineering Practice Lab.

## Features

- Add client loan records
- Edit existing client loan records
- Delete client loan records with confirmation
- Display all client loan records on the index page
- Validate input fields
- Prevent duplicate client numbers
- Generate amortization schedule dynamically
- Use H2 database with JPA
- Include JUnit and Mockito tests

## Technologies Used

- Java 17
- Spring Boot 3.5.5
- Spring MVC
- Thymeleaf
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito
- Maven

## Project Structure

```text
src/main/java/com/example/loanlab
 ┣ controller
 ┃ ┗ ClientLoanController.java
 ┣ entity
 ┃ ┗ ClientLoan.java
 ┣ repository
 ┃ ┗ ClientLoanRepository.java
 ┣ model
 ┃ ┗ AmortizationRow.java
 ┗ service
   ┗ AmortizationService.java

src/main/resources/templates
 ┣ index.html
 ┣ add-client.html
 ┣ edit-client.html
 ┗ amortization.html
```

## Functional Requirements Implemented

- Spring Boot MVC project with H2, JUnit, and Mockito
- JPA-based database table creation
- Display, add, edit, update, and delete records
- Input validation
- Duplicate client number checking
- Back button navigation
- Delete confirmation
- Loan amortization table generation
- Dependency injection
- Unit/web-layer tests using JUnit and Mockito

## Interest Rules

- Personal loan annual interest rate: 6%
- Business loan annual interest rate: 9%

## Monthly Payment Formula

```java
double monthlyPayment =
        (loanAmount * monthlyRate) /
        (1 - Math.pow(1 + monthlyRate, -termInMonths));
```
## How to Run
### 1. Clone the repository
Bash
```
git clone https://github.com/JohnnyLin707/loanlab.git
```
### 2. Open the project in IntelliJ IDEA

Open the project folder and allow Maven dependencies to download.

### 3. Run the application

Run:

LoanlabApplication.java

or use 

Bash:

``` 
.\mvnw.cmd spring-boot:run
```
### 4. Open in browser
http://localhost:8080

### 5. H2 Console
http://localhost:8080/h2-console

### Use:

- JDBC URL: jdbc:h2:file:./data/loanlabdb
- Username: sa
- Password: (leave blank)

### Running Tests

#### Use IntelliJ test runner or run:

```
.\mvnw.cmd test
```
### Notes
- Amortization data is computed on the fly and is not stored in the database.
- H2 local database files are excluded from Git using .gitignore.

### Author
Johnny Lin

CSIS 3275
