## Banking Application

RESTful API to simulate simple banking operations.

### Technologies
- Java 17
- Spring Boot
- Maven
- MySQL
- Flyway
- Hibernate, JPA
- jUnit 5, Mockito
- JaCoCo
- SLF4j
- Swagger
- JavaDoc
- Lombok
- ModelMapper
- GitHub

### How to run
1. Checkout the project from GitHub
```
git clone git@github.com:NadyaLia/Bank_Service.git
```
2. Default port for the api is 8080
3. MySQL database initialized with some sample users.

### Functional services
The microservice will implement CRUD operations for various entities related to banking operations. This
includes:
- Client Management: Managing client information and profiles.
- Account Management: Creating, updating, and deleting client accounts.
- Balance Inquiry: Retrieving account balances and transaction history.
- Transaction Processing: Performing transactions such as transfers between accounts.

#### User controllers
| HTTP METHOD | PATH                   | USAGE             |
|-------------|------------------------|-------------------|
| POST        | /api/auth/signup       | register user     |
| POST        | /api/auth/signin       | authenticate user |
| POST        | /api/admin/add-manager | add manager       |

#### Sample JSON for User controllers
register new user/admin:
```
{
    "firstName": "S",
    "lastName": "L",
    "username": "sl",
    "email": "sl@gmail.com",
    "password": "sl",
    "type": "admin"
}
```
authenticate user:
```
{
    "firstName": "S",
    "lastName": "L",
    "username": "sl",
    "email": "sl@gmail.com",
    "password": "sl",
    "type": "admin"
}
```
add manager:
```
{
    "firstName": "N",
    "lastName": "L",
    "username": "nl2",
    "email": "nl2@gmail.com",
    "password": "nl2",
    "status": 1
}
```

#### Manager controllers
| HTTP METHOD | PATH                                           | USAGE                                                                                         |
|-------------|------------------------------------------------|-----------------------------------------------------------------------------------------------|
| GET         | /manager/all-clients                           | get all clients                                                                               |
| GET         | /manager/client/{clientId}                     | get client by id                                                                              |
| DELETE      | /manager/client/{clientId}                     | delete client by id                                                                           |
| GET         | /manager/client-count                          | get clients quantity                                                                          |
| GET         | /manager/client-active                         | get clients with active status                                                                |
| GET         | /manager/client/transaction/{transactionCount} | return a list of clients who have a number of <br/>transactions exceeding the specified value |
| POST        | /manager/add-client                            | add client                                                                                    |
| GET         | /manager/all-accounts                          | get all accounts                                                                              |
| POST        | /manager/create-account                        | create account                                                                                |
| DELETE      | /manager/account/delete/{accountId}            | delete account by id                                                                          |
| DELETE      | /manager/account/delete/account-name/{name}    | delete account by name                                                                        |
| PUT         | /manager/update-account                        | update account                                                                                |
| GET         | /manager/account-status/{status}               | retrieve all accounts with a matching status                                                  |
| GET         | /manager/account/{productId}/{status}          | retrieve all accounts with a matching status and product id                                   |
| GET         | /manager/transaction/{clientId}                | retrieve all transactions associated with the client id                                       |
| GET         | /manager/transaction/currency/{currency}       | retrieve all transactions associated with the currency                                        |
| GET         | /manager/product/agreements/{quantity}         | retrieve all products where the number of agreements is greater than the specified quantity   |
| GET         | /manager/product/changed                       | get all changed products                                                                      |
| GET         | /manager/agreement/manager/{managerId}         | retrieve all agreements associated with the manager id                                        |
| GET         | /manager/agreement/client/{clientId}           | retrieve all agreements associated with the client id                                         |

#### Sample JSON for Manager controllers
add client:
```
{
    "firstName": "tony",
    "lastName": "stark",
    "username": "tonystark",
    "email": "tony@gmail.com",
    "password": "tony",
    "type": "client",
    "address": "333 Akl St",
    "phoneNumber": "333-444-5555"
}
```
create account:
```
{
    "name": "Business Plus Account",
    "type": 1,
    "status": 1,
    "currencyCode": 840,
    "clientId": "ecc1bb19-6c17-11ee-a809-9cb6d0ff6637"
}
```
update account:
```
{
    "name": "Business Plus Account",
    "type": 1,
    "status": 1,
    "currencyCode": 840,
    "balance": 100.00,
    "id": "2b6f5bea-9a62-4977-b805-fcbbc013400f"
}
```
#### Client controllers
| HTTP METHOD | PATH                                      | USAGE                       |
|-------------|-------------------------------------------|-----------------------------|
| GET         | /client/account                           | get accounts by client id   |
| GET         | /client/account/check-balance/{accountId} | check balance by account id |
| POST        | /client/transaction/credit                | credit account              |
| POST        | /client/transaction/debit                 | debit account               |
| PUT         | /client/transaction/transfer              | transfer between accounts   |
#### Sample JSON for Client controllers
credit account:
```
{
    "id": "ae549a66-ed37-4fb6-8620-d6922e4a0349",
    "amount": 1000.00
}
```
debit account:
```
{
    "id": "ae549a66-ed37-4fb6-8620-d6922e4a0349",
    "amount": 100.00
}
```
transfer between accounts:
```
{
    "sourceAccountId": "ae549a66-ed37-4fb6-8620-d6922e4a0349",
    "destinationAccountId": "d47ac10b-58cc-4372-a567-0e02b2c3d481",
    "transferAmount": 100.00
}
```

### Swagger
Please find the Rest API documentation in the below url

http://localhost:8080/swagger-ui/index.html


### Testing the Bank APP Rest Api
Please use Postman or the Swagger url to perform CRUD operations.
Sample JSONs are provided.

### Author
Nadzeya Liashchynskaya