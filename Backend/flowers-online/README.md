# Flowers Online Shopping Project

Flowers Online is a simple online flower shopping application built step by step for learning full-stack development.

The project uses:

- Java 8
- Spring Boot
- Spring Data JPA
- H2 Database
- Angular 17+
- Maven
- IntelliJ IDEA for backend
- Visual Studio Code for frontend

## Project Goal

The goal of this project is to create a simple online flower shopping system where:

- Admin can upload and manage flower products.
- Customers can view flower products by category.
- Customers can add products to cart and place orders.
- Customers can contact the flower shop, view locations, and submit reviews.

This project is developed one functionality at a time so that each feature is easy to understand.

## Modules

### Admin Module

The Admin module is used by the shop administrator.

Admin can:

- Upload new products
- View product list
- Update product details
- Delete products
- Manage contact information
- Manage shop locations
- View reports

### Customer Module

The Customer module is used by end users.

Customer can:

- Register account
- Login
- Change password
- View flower categories
- View flower products
- View product details
- Add products to cart
- Checkout
- Send contact message
- View shop locations
- Submit review or feedback
- View previous orders

## Functionality 1: Upload New Product

Admin can upload a new flower product.

Fields included:

- Product name
- Description
- Category
- Image URL
- Small price
- Medium price
- Large price
- Stock quantity
- Availability

Backend includes:

- Product table
- SQL script
- Product entity
- Request DTO
- Response DTO
- Product repository
- Product service
- Product service implementation
- Product controller
- Validation
- Basic exception handling
- JUnit and Mockito service test

Frontend includes:

- Upload Product component
- HTML form
- CSS styling
- TypeScript logic
- Product service
- API integration
- Success and error messages

API endpoint:

```text
POST http://localhost:8080/api/admin/products
```

## Functionality 2: Manage Products

Admin can manage existing flower products.

Features included:

- View product list
- Edit product
- Update product
- Delete product

Backend includes:

- Get all products API
- Get product by ID API
- Update product API
- Delete product API
- Product not found exception
- Basic error response
- JUnit and Mockito tests for update and product not found case

Frontend includes:

- Manage Products component
- Product table
- Edit form
- Delete button
- Refresh button
- API integration
- Success and error messages

API endpoints:

```text
GET    http://localhost:8080/api/admin/products
GET    http://localhost:8080/api/admin/products/{id}
PUT    http://localhost:8080/api/admin/products/{id}
DELETE http://localhost:8080/api/admin/products/{id}
```

## Functionality 3: Customer Registration and Login

Planned features:

- New customer account creation
- Existing customer login
- Change password
- Basic validation
- Basic authentication

## Functionality 4: Customer Shop Page

Planned features:

- View flower categories
- View all products
- View products by category
- Sort products by price or new arrivals

## Functionality 5: Product Details

Planned features:

- View selected product details
- Choose size
- Show selected price
- Add product to cart

## Functionality 6: Cart Management

Planned features:

- View cart
- Empty cart screen
- Add item quantity
- Reduce item quantity
- Remove item
- Show subtotal

## Functionality 7: Checkout

Planned features:

- Login before checkout
- Delivery information
- Payment option
- Order confirmation

## Functionality 8: Contact Form

Planned features:

- Customer can send message
- Fields: name, email, message
- Validation
- Save contact message

## Functionality 9: Shop Locations

Planned features:

- View shop list
- Expand shop details
- Show address and phone number

## Functionality 10: Review and Feedback

Planned features:

- Submit review
- Add rating from 1 to 5
- Add review message
- Save feedback

## Functionality 11: Reports

Planned features:

- Product reports
- Sales reports
- Customer reports
- Simple chart display if required

## Backend Project Structure

```text
backend
|-- pom.xml
|-- src
    |-- main
    |   |-- java
    |   |   |-- com.flowers.online
    |   |       |-- FlowersOnlineApplication.java
    |   |       |-- controller
    |   |       |-- dto
    |   |       |-- entity
    |   |       |-- exception
    |   |       |-- repository
    |   |       |-- service
    |   |       |-- service.impl
    |   |-- resources
    |       |-- application.properties
    |       |-- schema.sql
    |-- test
        |-- java
```

## Frontend Project Structure

Angular 19 standalone frontend structure:

```text
frontend-angular19-standalone
|-- package.json
|-- angular.json
|-- src
    |-- main.ts
    |-- styles.css
    |-- environments
    |   |-- environment.ts
    |-- app
        |-- app.component.ts
        |-- app.component.html
        |-- app.component.css
        |-- app.config.ts
        |-- app.routes.ts
        |-- services
        |   |-- product.service.ts
        |-- admin
            |-- upload-product
            |-- manage-products
```

## Database

The project uses H2 in-memory database.

H2 console URL:

```text
http://localhost:8080/h2-console
```

Database connection details:

```text
JDBC URL: jdbc:h2:mem:flowersdb
Username: sa
Password: leave blank
```

Current table:

```text
products
```

## How To Run Backend

1. Open backend project in IntelliJ IDEA.
2. Make sure Java 8 is selected.
3. Run:

```text
mvn spring-boot:run
```

4. Backend starts at:

```text
http://localhost:8080
```

## How To Run Frontend

1. Open Angular frontend project in Visual Studio Code.
2. Install dependencies:

```text
npm install
```

3. Start Angular:

```text
npm start
```

4. Frontend starts at:

```text
http://localhost:4200
```

## Application URLs

Upload product:

```text
http://localhost:4200/admin/upload-product
```

Manage products:

```text
http://localhost:4200/admin/manage-products
```

## Data Flow

### Angular to Spring Boot

The admin enters product details in the Angular form.

Angular sends the data to Spring Boot using `HttpClient`.

Example:

```text
Angular Form -> ProductService -> Spring Boot REST API
```

### Spring Boot to H2 Database

Spring Boot receives the request in the controller.

The controller calls the service.

The service calls the repository.

The repository saves or reads data from the H2 database.

Example:

```text
Controller -> Service -> Repository -> H2 Database
```

### H2 Database to Angular

After saving, updating, deleting, or reading data, Spring Boot sends the response back to Angular.

Angular displays the success message, error message, or product list.

Example:

```text
H2 Database -> Repository -> Service -> Controller -> Angular UI
```

## Validation

Current backend validations:

- Product name is required.
- Description is required.
- Category is required.
- Image URL is required.
- Stock quantity is required.
- Stock quantity cannot be negative.
- At least one price is required.
- Price must be greater than 0.

Current frontend validations:

- Required fields are checked in forms.
- Submit button is disabled when form is invalid.
- At least one price is checked before API call.
- Success and error messages are displayed.

## Error Handling

Current backend error handling:

- Validation errors return `400 Bad Request`.
- Product not found returns `404 Not Found`.
- Other errors return `500 Internal Server Error`.

Sample validation error:

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": [
    "Product name is required"
  ]
}
```

## Postman Testing

### Add Product

```text
POST http://localhost:8080/api/admin/products
```

Sample request:

```json
{
  "name": "Rose Bouquet",
  "description": "Fresh red roses for special occasions",
  "category": "Love",
  "imageUrl": "https://example.com/rose.jpg",
  "smallPrice": 300,
  "mediumPrice": 500,
  "largePrice": 800,
  "stockQuantity": 20,
  "available": true
}
```

### View Products

```text
GET http://localhost:8080/api/admin/products
```

### Update Product

```text
PUT http://localhost:8080/api/admin/products/1
```

### Delete Product

```text
DELETE http://localhost:8080/api/admin/products/1
```

## Unit Testing

The backend includes JUnit and Mockito tests for selected service cases.

Run tests:

```text
mvn test
```

Current test cases:

- Create product
- Update product
- Product not found

## Important Notes

- Do not open Angular component `.html` files directly in the browser.
- Angular templates work only when the Angular app runs using `ng serve`.
- Redis is not used currently because this beginner version does not need caching.
- JWT security is not implemented yet and can be added in a later functionality.
- Each new feature should be added one by one to keep the project simple.

## Development Rule

This project follows a step-by-step development process.

Only one functionality should be developed at a time.

After completing one functionality, test it fully before moving to the next one.
