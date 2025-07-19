# LineUp API

This is the backend API for the [LineUp Android app](https://github.com/carlsonn415/LineUp), built with **Spring Boot**. It provides user authentication, account management, and secure data persistence using **JWT**, **Spring Security**, and **MySQL**.

---

## 🧰 Technologies Used

- **Spring Boot** — lightweight Java framework for building REST APIs  
- **MySQL** — relational database for persistent storage  
- **Spring Security** — handles authentication and authorization  
- **JWT (JSON Web Tokens)** — stateless, secure authentication  
- **BCrypt** — strong password hashing  
- **Lombok** — reduces boilerplate code with annotations  
- **MapStruct** — efficient object mapping (DTO ↔ Entity)  
- **Maven** — build and dependency management  
- **CRUD** — supports standard create, read, update, delete operations  


## 📦 Features

- ✅ User registration with hashed passwords  
- ✅ Secure login with JWT token generation  
- ✅ Get current user (`/users/me`) via JWT  
- ✅ Update user profile info (username, email, password)  
- ✅ Unique email and username enforcement  
- ✅ RESTful, JSON-based API structure  

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/carlsonn415/lineUp-api.git
cd lineUp-api
```
### 2. Configure the database

Make sure you have a MySQL instance running. Then, update your application.properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/lineup_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```
### 3. Run the app
```bash
mvn spring-boot:run
```
The app will start on http://localhost:8080.

## 🔐 Authentication

All secure endpoints require an Authorization: Bearer <JWT> header.

    POST /users/login — returns a JWT token
    GET /users/me — fetch the currently logged-in user
    Other user modification endpoints (/change-password, /change-email, etc.) require authentication

## 📁 Project Structure (Overview)
```
├── configurations/         // Class setups
├── controllers/         // REST controllers
├── dtos/                // Request and response DTOs
├── entities/             // JPA entity models
├── mappers/             // MapStruct interfaces
├── repositories/         // Spring Data JPA interfaces
├── security/           // JWT utils, filters, config
├── services/            // Business logic
└── LineUpApiApplication.java // Main entry point
```

## 📄 License

```
Copyright 2025 Nathan Carlson

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

