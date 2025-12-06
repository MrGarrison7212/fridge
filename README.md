# Fridge – Backend

This repository contains the **backend** part of the Fridge application – a REST API for tracking items stored in a fridge.

The goal of this project is to:
- track items by **stored date** and **best-before date**
- provide a clean, realistic Spring Boot backend
- be consumed by a separate Angular frontend - [fridge-frontend](https://github.com/MrGarrison7212/fridge-frontend).

---

## 1. What the application does

The backend exposes a small REST API that allows you to:

- **List** all items in the fridge
- **Create** a new item (name, category, quantity, unit, stored date, best-before date, notes)
- **Update** an existing item
- **Delete** an item

Each item has:

- basic info (name, category, quantity, unit)
- when it was stored
- its best-before date
- optional notes

### Application scope

- CRUD API for fridge items under `/api/items`
- Input validation (Bean Validation)
- Clear separation of layers (controller, service, repository, domain DTO/mappers)
- Simple security with **Basic Auth** and a [single test user](#authentication-for-testing)
- Global error handling with a unified error model (`ApiError`)
- DTO + mapper layer between REST and JPA entities
- Controller-level tests using `@WebMvcTest` and `MockMvc`

---

## 2. Tech stack

**Backend**

- Java (e.g. 21)
- Spring Boot
    - Spring Web
    - Spring Data JPA
    - Spring Validation
    - Spring Security
- H2 in-memory database (for local development / tests)
- Springdoc OpenAPI + Swagger UI for API documentation
- JUnit 5 + MockMvc (`@WebMvcTest`) for tests

**Frontend**

- Angular + Tailwind CSS  
  (lives in a separate repository: [Fridge Frontend (Angular app)](https://github.com/MrGarrison7212/fridge-frontend)

---

## 3. Architecture & design

The backend is intentionally small but structured as a typical Spring Boot application.

### Layers

- **Controller layer (`controller`)**
    - Exposes REST endpoints under `/api/items`
    - Uses DTO classes for input/output
    - Delegates business logic to the service layer

- **Service layer (`service`)**
    - Contains business logic for working with fridge items
    - Uses JPA repositories to access the database
    - Uses mappers to convert between entities and DTOs
    - Throws domain-specific exceptions when needed (e.g. “item not found”)

- **Persistence layer (`persistence`)**
    - JPA entity `FridgeItem` mapped to a database table
    - `FridgeItemRepository` extends `JpaRepository<FridgeItem, Long>`

- **Domain layer (`domain`)**
    - DTOs:
        - `FridgeItemDto` – returned to the client
        - `FridgeItemCreateRequest` – input for create
        - `FridgeItemUpdateRequest` – input for update
    - `FridgeItemMapper` – converts between entity and DTOs

- **Exception layer (`exception`)**
    - `ApiError` – standard error response (code, message, timestamp, …)
    - `GlobalExceptionHandler` (`@RestControllerAdvice`) – maps exceptions to HTTP responses:
        - `ITEM_NOT_FOUND` → 404
        - `VALIDATION_ERROR` → 400
        - `INTERNAL_ERROR` → 500

- **Config (`config`)**
    - `InMemoryUserConfig`
        - defines a single test user:
            - **username:** `testuser`
            - **password:** `testuser123` (BCrypt)
    - `SecurityConfiguration`
        - `/api/**` requires authentication (Basic Auth)
        - Swagger routes `/v3/api-docs/**`, `/swagger-ui/**` are public
        - CSRF disabled for simplicity in this test

### Validation

Input DTOs use Bean Validation annotations:

- `@NotBlank`, `@NotNull` for required fields
- `@Min(1)` for `quantity`

If invalid data is sent:

- Spring throws `MethodArgumentNotValidException`
- `GlobalExceptionHandler` returns `400 Bad Request` with a structured `ApiError`

---

## 4. REST API overview

All endpoints are under: **`/api/items`**  
All require **Basic Auth** (`testuser` / `testuser123`).

### GET `/api/items`

Returns all items.

- Response: `200 OK`
- Body: `List<FridgeItemDto>`

### GET `/api/items/{id}`

Returns a single item by ID.

- Response:
    - `200 OK` – if found
    - `404 Not Found` – if item does not exist

### POST `/api/items`

Creates a new item.

- Request body: `FridgeItemCreateRequest`
- Response:
    - `201 Created` – with created `FridgeItemDto`
    - `400 Bad Request` – validation error

### PUT `/api/items/{id}`

Updates an existing item.

- Request body: `FridgeItemUpdateRequest`
- Response:
    - `200 OK`
    - `400 Bad Request` – validation error
    - `404 Not Found` – if item does not exist

### DELETE `/api/items/{id}`

Deletes an item.

- Response:
    - `204 No Content` – successfully deleted
    - `404 Not Found` – if item does not exist

---

## 5. How to run

### Prerequisites

- JDK (e.g. 21)
- Maven

### Build

```bash
mvn clean install
```

### RUN

```bash
mvn clean install
```
The application will be available at:

Backend base URL: http://localhost:8080

Swagger UI:

http://localhost:8080/swagger-ui/index.html

### Authentication for testing

Use HTTP Basic Auth:

##### - username: `testuser`

##### - password: `testuser123`

---

## 6. Testing

Unit/HTTP tests are written with `@WebMvcTest` and `MockMvc`.

Example: a test that verifies `POST /api/items`:
- loads only the controller
- disables security filters in test (@AutoConfigureMockMvc(addFilters = false))
- mocks FridgeItemService
- checks:
  -  HTTP status (201 Created)
  - content type (application/json)
  - the expected JSON fields in the response

Run all tests:
```
mvn test
```
## 7. Frontend (fridge-frontend)

The Angular frontend lives in a separate repository (`fridge-frontend`) and:
    - uses Basic Auth with the same test user
    - calls this backend at http://localhost:8080/api/items

For details, setup and tests, see the `README.md` in the [frontend repository](https://github.com/MrGarrison7212/fridge-frontend).