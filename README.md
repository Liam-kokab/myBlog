# myBlog

## Overview
myBlog is a backend REST API for a blogging platform built using Java, Spring Boot, and PostgreSQL. It provides RESTful APIs for managing blog posts, categories, and user authentication. The application is designed to handle user roles, secure authentication using JWT, and validation for data integrity.

---

## Architecture
The application follows a layered architecture:
1. **Controller Layer**: Handles HTTP requests and responses.
2. **Service Layer**: Contains business logic.
3. **Repository Layer**: Interacts with the database using JPA.
4. **Model Layer**: Defines the entities and data structures.
5. **Utility Layer**: Includes helper classes like JWT utilities for token generation and validation.

---

## Features
- **User Management**: 
  - User registration and login.
  - Role-based access control.
- **Blog Management**:
  - CRUD operations for blog posts.
  - Support for rich content using JSONB in PostgreSQL.
- **Category Management**:
  - CRUD operations for blog categories.
  - Validation for unique category names.
- **Authentication**:
  - Secure authentication using JWT.
  - Token-based authorization for protected endpoints.
- **Validation**:
  - Input validation using Jakarta Validation API.
  - Global exception handling for consistent error responses.

---

## Technologies Used
- **Programming Language**: Java
- **Framework**: Spring Boot
- **Database**: PostgreSQL
- **Build Tool**: Gradle
- **Security**: JWT (JSON Web Tokens)
- **Validation**: Jakarta Validation API
- **Persistence**: JPA (Java Persistence API)

---

## Prerequisites
- Java 17 or higher
- PostgreSQL installed and running
- Gradle installed

---
