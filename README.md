# Book Management API (Spring Boot)

A simple RESTful API to manage books.

## Features

- CRUD operations
- Validation using `@NotBlank`
- In-memory storage using List
- Pagination support: `/books?page=0&limit=5`

## Run Instructions

1. Clone the repo or unzip the project.
2. Run `mvn spring-boot:run`
3. Test with Postman at `http://localhost:8080/books`

## Sample Book JSON
```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin"
}
