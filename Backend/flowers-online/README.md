# Flowers Online Shopping Project

Flowers Online is a beginner-friendly full-stack project built step by step. The purpose is to learn how Angular sends data to Spring Boot, how Spring Boot validates and processes requests, and how data is saved or read from an H2 database.

## Technology Stack

- Java 8
- Spring Boot
- Spring Data JPA
- H2 Database
- Angular 17+ standalone components
- Maven
- IntelliJ IDEA for backend
- Visual Studio Code for frontend

## Modules

### Admin Module

Admin functionality is used to upload and manage flower products.

Completed admin features:

- Upload new product
- View products
- Update products
- Delete products

### Customer Module

Customer functionality is used by end users of the flower shopping site.

Completed customer features:

- Contact message
- Shop locations
- Review / feedback
- Create account
- Login
- Change password
- Browse shop products
- View product details and choose size

## Project Run Backend

Open `backend` in IntelliJ IDEA and run:

```text
mvn spring-boot:run
```

Backend URL:

```text
http://localhost:8080
```

H2 Console:

```text
http://localhost:8080/h2-console
```

H2 details:

```text
JDBC URL: jdbc:h2:mem:flowersdb
Username: sa
Password: leave blank
```

## Run Frontend

Open `frontend` in VS Code.

Install dependencies:

```text
npm install
```

Start Angular:

```text
npm start
```

Frontend URL:

```text
http://localhost:4200
```

Important: do not open Angular component `.html` files directly in the browser. Angular template syntax works only through `ng serve`.

## Database Tables

Current H2 tables:

- `products`
- `contact_messages`
- `shop_locations`
- `reviews`
- `customers`

## Common Data Flow

```text
-> Angular Component
-> Angular Service
-> Spring Boot Controller
-> Spring Boot Service
-> Spring Data JPA Repository
-> H2 Database
```

Response flow:

```text
-> H2 Database
-> Repository
-> Service
-> Controller
-> Angular Service
-> Angular Component
-> Success/Error message on UI
```

## Completed Functionalities

## Functionality 1: Upload New Product

### Use

Admin can upload a new flower product into the system.

### Requirement Covered

Admin should be able to upload products/items with:

- Image
- Description
- Size
- Price

### Backend

Main files:

- `Product.java`
- `ProductRequest.java`
- `ProductResponse.java`
- `ProductRepository.java`
- `ProductService.java`
- `ProductServiceImpl.java`
- `ProductController.java`
- `GlobalExceptionHandler.java`
- `schema.sql`

Table:

```text
products
```

### Frontend

Angular files:

- `upload-product.component.ts`
- `upload-product.component.html`
- `upload-product.component.css`
- `product.service.ts`

Frontend URL:

```text
http://localhost:4200/admin/upload-product
```

### API Endpoint

```text
POST http://localhost:8080/api/admin/products
```

### Sample Request

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

### Sample Response

```json
{
  "id": 1,
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

### Testing Steps

1. Start backend.
2. Start frontend.
3. Open `/admin/upload-product`.
4. Fill product details.
5. Click `Upload Product`.
6. Verify success message.
7. Check H2:

```sql
SELECT * FROM products;
```

## Functionality 2: Manage Products

### Use

Admin can view, edit, update, and delete products.

### Requirement Covered

Admin should be able to:

- Manage products
- Add/modify/delete products
- Update availability and details

### Backend
- `ProductController.java`
- `ProductService.java`
- `ProductServiceImpl.java`
- `ProductRepository.java`
- `ProductNotFoundException.java`

### Frontend
- `manage-products.component.ts`
- `manage-products.component.html`
- `manage-products.component.css`
- `product.service.ts`

Frontend URL:

```text
http://localhost:4200/admin/manage-products
```

### API Endpoints

```text
GET    http://localhost:8080/api/admin/products
GET    http://localhost:8080/api/admin/products/{id}
PUT    http://localhost:8080/api/admin/products/{id}
DELETE http://localhost:8080/api/admin/products/{id}
```

### Sample Update Request

```json
{
  "name": "Updated Rose Bouquet",
  "description": "Fresh red roses with ribbon",
  "category": "Love",
  "imageUrl": "https://example.com/updated-rose.jpg",
  "smallPrice": 350,
  "mediumPrice": 550,
  "largePrice": 850,
  "stockQuantity": 15,
  "available": true
}
```

### Sample Delete Response

```text
204 No Content
```

### Testing Steps

1. Upload one product.
2. Open `/admin/manage-products`.
3. Confirm product list is visible.
4. Click `Edit`.
5. Update product and save.
6. Click `Delete`.
7. Verify H2 table.

## Functionality 3: Contact Message

### Use

Customer can send a message to Flowers Online.

### Requirement Covered

Customer can click Contact and send:

- Name
- Email address
- Message

All fields are mandatory.

### Backend
- `ContactMessage.java`
- `ContactMessageRequest.java`
- `ContactMessageResponse.java`
- `ContactMessageRepository.java`
- `ContactMessageService.java`
- `ContactMessageServiceImpl.java`
- `ContactMessageController.java`

Table:

```text
contact_messages
```

### Frontend
- `contact.component.ts`
- `contact.component.html`
- `contact.component.css`
- `contact-message.service.ts`

Frontend URL:

```text
http://localhost:4200/contact
```

### API Endpoint

```text
POST http://localhost:8080/api/contact-messages
```

### Sample Request

```json
{
  "name": "John",
  "email": "john@example.com",
  "message": "Please share bouquet details."
}
```

### Sample Response

```json
{
  "id": 1,
  "name": "John",
  "email": "john@example.com",
  "message": "Please share bouquet details.",
  "createdAt": "2026-06-20T19:00:00"
}
```

### Testing Steps

1. Open `/contact`.
2. Enter name, email, and message.
3. Click `Send Message`.
4. Verify success message.
5. Check H2:

```sql
SELECT * FROM contact_messages;
```

## Functionality 4: Shop Locations

### Use

Customer can view Flowers Online shop locations.

### Requirement Covered

Customer can click Locations and see:

- Shop names
- Full address
- Phone number

### Backend
- `ShopLocation.java`
- `ShopLocationResponse.java`
- `ShopLocationRepository.java`
- `ShopLocationService.java`
- `ShopLocationServiceImpl.java`
- `ShopLocationController.java`
- `data.sql`

Table:

```text
shop_locations
```

### Frontend
- `locations.component.ts`
- `locations.component.html`
- `locations.component.css`
- `shop-location.service.ts`

Frontend URL:

```text
http://localhost:4200/locations
```

### API Endpoint

```text
GET http://localhost:8080/api/locations
```

### Sample Response

```json
[
  {
    "id": 1,
    "shopName": "Flowers Online - Central Shop",
    "address": "12 Rose Street, Near City Mall",
    "city": "Bengaluru",
    "country": "India",
    "phoneNumber": "9876543210"
  }
]
```

### Testing Steps

1. Start backend.
2. Open `/locations`.
3. Click any shop.
4. Verify address and phone number appear.
5. Check H2:

```sql
SELECT * FROM shop_locations;
```

## Functionality 5: Review / Feedback

### Use

Customer can submit review or feedback.

### Requirement Covered

Customer can provide:

- Reviewer email
- Rating from 1 to 5
- Review message

### Backend

Main files:

- `Review.java`
- `ReviewRequest.java`
- `ReviewResponse.java`
- `ReviewRepository.java`
- `ReviewService.java`
- `ReviewServiceImpl.java`
- `ReviewController.java`

Table:

```text
reviews
```

### Frontend

Angular 19 standalone files:

- `review.component.ts`
- `review.component.html`
- `review.component.css`
- `review.service.ts`

Frontend URL:

```text
http://localhost:4200/review
```

### API Endpoint

```text
POST http://localhost:8080/api/reviews
```

### Sample Request

```json
{
  "reviewerEmail": "customer@example.com",
  "rating": 5,
  "reviewMessage": "Great shopping experience and fresh flowers."
}
```

### Sample Response

```json
{
  "id": 1,
  "reviewerEmail": "customer@example.com",
  "rating": 5,
  "reviewMessage": "Great shopping experience and fresh flowers.",
  "createdAt": "2026-06-21T13:40:00"
}
```

### Testing Steps

1. Open `/review`.
2. Enter email.
3. Select rating.
4. Enter review message.
5. Submit review.
6. Check H2:

```sql
SELECT * FROM reviews;
```

## Functionality 6: Customer Registration

### Use

New customer can create an account.

### Requirement Covered

Customer account form includes:

- Title
- First name
- Last name
- Email
- Password
- Confirm password
- Phone number
- City
- Country
- Terms and conditions checkbox

### Backend

Main files:

- `Customer.java`
- `CustomerRegistrationRequest.java`
- `CustomerResponse.java`
- `CustomerRepository.java`
- `CustomerService.java`
- `CustomerServiceImpl.java`
- `CustomerController.java`

Table:

```text
customers
```

### Frontend

Angular 19 standalone files:

- `register.component.ts`
- `register.component.html`
- `register.component.css`
- `customer.service.ts`

Frontend URL:

```text
http://localhost:4200/account/register
```

### API Endpoint

```text
POST http://localhost:8080/api/customers/register
```

### Sample Request

```json
{
  "title": "Ms",
  "firstName": "Mary",
  "lastName": "Rose",
  "email": "mary@example.com",
  "password": "secret123",
  "phoneNumber": "9876543210",
  "city": "Bengaluru",
  "country": "India"
}
```

### Sample Response

```json
{
  "id": 1,
  "title": "Ms",
  "firstName": "Mary",
  "lastName": "Rose",
  "email": "mary@example.com",
  "phoneNumber": "9876543210",
  "city": "Bengaluru",
  "country": "India",
  "createdAt": "2026-06-21T15:10:00"
}
```

### Testing Steps

1. Open `/account/register`.
2. Fill all fields.
3. Accept terms and conditions.
4. Click `Create New Account`.
5. Try duplicate email to verify error handling.
6. Check H2:

```sql
SELECT * FROM customers;
```

## Functionality 7: Customer Login

### Use

Existing customer can login using email and password.

### Requirement Covered

Existing customer login form includes:

- Email
- Password

### Backend

Main files:

- `CustomerLoginRequest.java`
- `CustomerLoginResponse.java`
- `CustomerRepository.java`
- `CustomerServiceImpl.java`
- `CustomerController.java`

### Frontend

Angular 19 standalone files:

- `login.component.ts`
- `login.component.html`
- `login.component.css`
- `customer.service.ts`

Frontend URL:

```text
http://localhost:4200/account/login
```

### API Endpoint

```text
POST http://localhost:8080/api/customers/login
```

### Sample Request

```json
{
  "email": "mary@example.com",
  "password": "secret123"
}
```

### Sample Response

```json
{
  "customerId": 1,
  "firstName": "Mary",
  "email": "mary@example.com",
  "message": "Login successful"
}
```

### Testing Steps

1. Register a customer first.
2. Open `/account/login`.
3. Enter registered email and password.
4. Verify success message.
5. Try wrong password to verify error.

## Functionality 8: Change Password

### Use

Existing customer can change password.

### Requirement Covered

Customer can use change password option.

This implementation includes:

- Email
- Old password
- New password
- Confirm new password

### Backend

Main files:

- `ChangePasswordRequest.java`
- `ChangePasswordResponse.java`
- `CustomerServiceImpl.java`
- `CustomerController.java`

### Frontend

Angular 19 standalone files:

- `change-password.component.ts`
- `change-password.component.html`
- `change-password.component.css`
- `customer.service.ts`

Frontend URL:

```text
http://localhost:4200/account/change-password
```

### API Endpoint

```text
POST http://localhost:8080/api/customers/change-password
```

### Sample Request

```json
{
  "email": "mary@example.com",
  "oldPassword": "secret123",
  "newPassword": "newsecret123"
}
```

### Sample Response

```json
{
  "email": "mary@example.com",
  "message": "Password changed successfully"
}
```

### Testing Steps

1. Register a customer.
2. Login with old password.
3. Open `/account/change-password`.
4. Enter old and new passwords.
5. Submit.
6. Login again using new password.

## Functionality 9: Customer Shop / Product Browsing

### Use

Customer can browse flower products by category.

### Requirement Covered

Customer can:

- Click Shop menu.
- View product categories.
- Click category to see products.
- Click Shop Now to see all products.
- Sort by new arrivals, name, and price.

### Backend

Main files:

- `ShopService.java`
- `ShopServiceImpl.java`
- `ShopController.java`
- `ProductRepository.java`

### Frontend

Angular 19 standalone files:

- `shop.component.ts`
- `shop.component.html`
- `shop.component.css`
- `shop.service.ts`

Frontend URL:

```text
http://localhost:4200/shop
```

### API Endpoints

```text
GET http://localhost:8080/api/shop/categories
GET http://localhost:8080/api/shop/products?category=All&sortBy=new-arrivals
GET http://localhost:8080/api/shop/products?category=Love&sortBy=price-low-to-high
```

### Sample Categories Response

```json
[
  "All",
  "Birthday",
  "Love",
  "Marriage",
  "Grand-Opening",
  "Sympathy",
  "Get-well-soon"
]
```

### Sample Product Response

```json
[
  {
    "id": 1,
    "name": "Rose Bouquet",
    "description": "Fresh red roses",
    "category": "Love",
    "imageUrl": "https://example.com/rose.jpg",
    "smallPrice": 300,
    "mediumPrice": 500,
    "largePrice": 800,
    "stockQuantity": 20,
    "available": true
  }
]
```

### Testing Steps

1. Upload a few available products first.
2. Open `/shop`.
3. Click category buttons.
4. Click `Shop Now`.
5. Change sort option.
6. Confirm products update.

## Functionality 10: Product Details / Choose Size

### Use

Customer can view a product in detail and choose size.

### Requirement Covered

Customer can:

- Click any product from Shop.
- See complete product details.
- Choose Small, Medium, or Large.
- See total price based on selected size.

### Backend

Main files:

- `ShopService.java`
- `ShopServiceImpl.java`
- `ShopController.java`

### Frontend

Angular 19 standalone files:

- `product-detail.component.ts`
- `product-detail.component.html`
- `product-detail.component.css`
- `shop.service.ts`
- Updated `shop.component.html`

Frontend URL:

```text
http://localhost:4200/shop/product/{id}
```

Example:

```text
http://localhost:4200/shop/product/1
```

### API Endpoint

```text
GET http://localhost:8080/api/shop/products/{id}
```

### Sample Response

```json
{
  "id": 1,
  "name": "Rose Bouquet",
  "description": "Fresh red roses",
  "category": "Love",
  "imageUrl": "https://example.com/rose.jpg",
  "smallPrice": 300,
  "mediumPrice": 500,
  "largePrice": 800,
  "stockQuantity": 20,
  "available": true
}
```

### Testing Steps

1. Open `/shop`.
2. Click `View Details`.
3. Choose Small, Medium, or Large.
4. Verify total price changes.
5. Click `Back to Shop`.

## Validation and Error Handling

Backend validation examples:

- Required fields return `400 Bad Request`.
- Invalid email returns `400 Bad Request`.
- Duplicate customer email returns `400 Bad Request`.
- Product not found returns `404 Not Found`.
- Wrong login or old password returns `400 Bad Request`.

Sample validation response:

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": [
    "Email is required"
  ]
}
```

Sample not found response:

```json
{
  "status": 404,
  "message": "Product not found",
  "errors": [
    "Product not found with id: 99"
  ]
}
```

## Unit Testing

Backend uses JUnit and Mockito for selected service-layer tests.

Run:

```text
mvn test
```

Current tested areas:

- Product creation
- Product update
- Product not found
- Contact message save
- Shop locations list
- Review save
- Customer registration
- Customer login
- Change password
- Shop product list and sorting
- Product detail by ID

## Future Functionalities

Not implemented yet:

- Add to cart
- Cart page
- Quantity update
- Remove item from cart
- Checkout
- Orders
- Admin reports
- JWT security
- Password encryption

## Important Notes

- Redis is not used because the current beginner version does not need caching.
- Password is stored simply for learning purposes. A real application should use BCrypt and Spring Security.
- JWT security is not implemented yet.
- Each functionality is intentionally built one step at a time.
