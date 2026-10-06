# Order Management API

A REST API for managing customers and orders, with JWT login and role-based access.

## Tech stack

- Java 21, Spring Boot 4
- Spring Data JPA (Hibernate), H2 database
- Spring Security with JWT
- Bean Validation, Swagger (springdoc)

## Features

- Register and login (passwords hashed with BCrypt)
- JWT authentication with two roles: `USER` and `ADMIN`
- Users see and manage only their own orders
- Admins can view all orders
- Order status rules: `PLACED` to `PAID` or `CANCELLED`
- Request validation and a global exception handler
- Pagination and sorting on order lists

## Run it

1. Clone the repo and open it in IntelliJ.
2. Run `OrderManagmentApplication`.
3. The app starts on `http://localhost:8081`.

The H2 database is stored in the `data/` folder and created automatically.

## API docs

Swagger UI: `http://localhost:8081/swagger-ui/index.html`

To call protected endpoints: log in, copy the token, click **Authorize**, and paste it.

## Endpoints

| Method | URL | Access | Description |
|---|---|---|---|
| POST | `/auth/register` | Public | Create a user and customer |
| POST | `/auth/login` | Public | Get a JWT token |
| POST | `/order/place` | User | Place an order |
| GET | `/order/my` | User | My orders |
| GET | `/order/{id}` | Owner or Admin | One order |
| PUT | `/order/{id}/pay` | Owner or Admin | Mark as paid |
| PUT | `/order/{id}/cancel` | Owner or Admin | Cancel an order |
| GET | `/order/getAll` | Admin | All orders (paged) |
| GET | `/order/customer/{customerId}` | Admin | Orders of one customer |


## Notes

- The JWT signing key is generated on startup, so tokens expire when the app restarts.
