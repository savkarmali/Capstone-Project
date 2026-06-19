# Campaign Management System / Survey Management System

A Java Full Stack Capstone Project for creating, publishing, managing, and analyzing surveys/campaign registrations.

The application allows an Admin/Admin Team to design surveys, add questions, launch surveys using public URLs, collect user responses, 
export responses, and view analytics using charts. Users/Registrants can open a survey URL, fill the survey, submit it, 
and receive confirmation on screen and by email.

---

## Tech Stack

### Backend

- Java 17
- Spring Boot 3.x
- Spring Security 6
- JWT Authentication
- Spring Data JPA
- H2 Database
- Swagger OpenAPI
- Java Mail Sender
- Apache POI for Excel export
- OpenPDF for PDF export
- Maven
- JUnit 5
- Mockito
- JaCoCo

### Frontend

- Angular 17
- Angular Material
- Bootstrap
- Chart.js
- Reactive Forms
- Route Guards
- HTTP Interceptors

### Deployment

- Docker
- Docker Compose
- Kubernetes YAML
- CI/CD Pipeline

---

## Modules

## Admin Module

Admin users can perform the following actions:

1. Login using email and password
2. Create surveys
3. Add, edit, and delete questions
4. Configure question types
5. Publish surveys
6. Generate public survey URLs
7. View survey responses
8. Export responses to Excel, CSV, and PDF
9. Filter responses by date range
10. View analytics dashboard
11. View pie charts and bar charts
12. Send and track email notifications
13. Add more admins

---

## User / Registrant Module

Users can perform the following actions:

1. Open survey using a public URL
2. Enter mandatory first name and email
3. Fill the survey
4. Submit the survey
5. Receive success message on screen
6. Receive email confirmation after successful submission

---

## Important Business Rules

1. A survey can have a maximum of 6 questions.
2. All survey fields are mandatory.
3. Once a survey is published/launched, it cannot be edited.
4. A survey can be available for a fixed validity period.
5. Users can submit a survey only once using their email ID.
6. Submission date is captured automatically from the system date.
7. Users must not access admin APIs or admin UI screens.
8. Admin APIs must be secured using JWT.
9. Public survey APIs must validate survey status and validity period.
10. Initially one admin account is seeded into the system.

---

## Supported Question Types

The system supports the following question types:

- Text
- Text Area
- Number
- Radio Button
- Checkbox
- Date

---

## Supported Validation Data Types

Questions can apply validation rules using these data types:

- General text
- Alphabets only
- Alphanumeric
- Number
- Number range
- Date
- Email

---

## Backend Architecture

The backend follows a layered architecture.

com.capstone.survey
├── config
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── repository
├── security
├── service
│   └── impl
├── util
└── validation
