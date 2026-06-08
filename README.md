# EcommercePlatform

**Category:** Full-Stack Web Application

A full-stack e-commerce application built with **Spring Boot**, **Java**, and **MySQL** that handles product listing, user accounts, shopping cart, and order management — secured with Spring Security and role-based access control for both customers and administrators.

## Features

- **Product Catalog**: Browse, search, and filter products by category, price, and keyword
- - **Shopping Cart**: Add, update, and remove items from cart with real-time quantity management
  - - **Order Management**: Place orders, view order history, and track order status
    - - **User Accounts**: Register, login, view profile, and manage purchase history
      - - **Admin Panel**: Manage products, view all orders, and handle user accounts
        - - **Spring Security**: JWT-based authentication and role-based access control (CUSTOMER, ADMIN)
          - - **RESTful APIs**: Clean REST endpoints tested thoroughly with Postman
            - - **Spring Data JPA**: Efficient ORM with MySQL backend
             
              - ## Tech Stack
             
              - | Layer | Technology |
              - |-------|-----------|
              - | Language | Java 17 |
              - | Framework | Spring Boot 3.x |
              - | Security | Spring Security + JWT |
              - | ORM | Spring Data JPA / Hibernate |
              - | Database | MySQL 8.x |
              - | Build Tool | Maven |
              - | IDE | IntelliJ IDEA |
              - | API Testing | Postman |
             
              - ## Project Structure
             
              -     EcommercePlatform/
              -     src/main/java/com/ecommerce/
              -       config/
              -           SecurityConfig.java          - Spring Security + JWT filter config
              -               JwtUtil.java                 - JWT token generation and validation
              -                 controller/
              -                     AuthController.java          - Register / login endpoints
              -                         ProductController.java       - Product CRUD (admin) + search (public)
              -                             CartController.java          - Add / update / remove cart items
              -                                 OrderController.java         - Place order, view history
              -                                     UserController.java          - User profile management
              -                                         AdminController.java         - Admin dashboard endpoints
              -                                           model/
              -                                               User.java                    - User entity with roles
              -                                                   Product.java                 - Product entity
              -                                                       CartItem.java                - Cart item entity
              -                                                           Order.java                   - Order entity
              -                                                               OrderItem.java               - Order line-item entity
              -                                                                   Role.java                    - CUSTOMER / ADMIN enum
              -                                                                     repository/
              -                                                                         UserRepository.java
              -                                                                             ProductRepository.java
              -                                                                                 CartItemRepository.java
              -                                                                                     OrderRepository.java
              -                                                                                       service/
              -                                                                                           UserService.java
              -                                                                                               ProductService.java
              -                                                                                                   CartService.java
              -                                                                                                       OrderService.java
              -                                                                                                           CustomUserDetailsService.java
              -                                                                                                             dto/
              -                                                                                                                 LoginRequest.java
              -                                                                                                                     RegisterRequest.java
              -                                                                                                                         ProductDTO.java
              -                                                                                                                             OrderDTO.java
              -                                                                                                                             src/main/resources/
              -                                                                                                                               application.properties         - DB config, JWT secret, server port
             
              -                                                                                                                           ## API Endpoints
             
              -                                                                                                                       ### Auth
              -                                                                                                                   | Method | Endpoint | Description |
              -                                                                                                               |--------|----------|-------------|
              -                                                                                                           | POST | /api/auth/register | Register new user |
              -                                                                                                       | POST | /api/auth/login | Login and receive JWT |
             
              -                                                                                                   ### Products
              -                                                                                               | Method | Endpoint | Description |
              -                                                                                           |--------|----------|-------------|
              -                                                                                       | GET | /api/products | List all products |
              -                                                                                   | GET | /api/products/{id} | Get product by ID |
              -                                                                               | GET | /api/products/search?q= | Search products |
              -                                                                           | POST | /api/products | Add product (ADMIN) |
              -                                                                       | PUT | /api/products/{id} | Update product (ADMIN) |
              -                                                                   | DELETE | /api/products/{id} | Delete product (ADMIN) |
             
              -                                                               ### Cart
              -                                                           | Method | Endpoint | Description |
              -                                                       |--------|----------|-------------|
              -                                                   | GET | /api/cart | View cart |
              -                                               | POST | /api/cart | Add item to cart |
              -                                           | PUT | /api/cart/{id} | Update item quantity |
              -                                       | DELETE | /api/cart/{id} | Remove item from cart |
             
              -                                   ### Orders
              -                               | Method | Endpoint | Description |
              -                           |--------|----------|-------------|
              -                       | POST | /api/orders | Place order from cart |
              -                   | GET | /api/orders | View order history |
              -               | GET | /api/orders/{id} | Get order details |
              -           | GET | /api/admin/orders | All orders (ADMIN) |
             
              -       ## Getting Started
             
              -   ### Prerequisites
              -   - Java 17+
                  - - MySQL 8+
                    - - Maven 3.6+
                     
                      - ### Setup
                     
                      - 1. Clone the repository:
                        2.     git clone https://github.com/moulideekshith9390/EcommercePlatform.git
                        3.     cd EcommercePlatform
                       
                        4. 2. Create the MySQL database:
                           3.     CREATE DATABASE ecommerce_db;
                          
                           4. 3. Configure application.properties:
                              4.     spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
                              5.     spring.datasource.username=root
                              6.     spring.datasource.password=yourpassword
                              7.     spring.jpa.hibernate.ddl-auto=update
                              8.     jwt.secret=your_jwt_secret_key
                              9.     jwt.expiration=86400000
                             
                              10. 4. Build and run:
                                  5.     mvn clean install
                                  6.     mvn spring-boot:run
                                 
                                  7. 5. The API will be available at: http://localhost:8080/api
                                    
                                     6. ## Security
                                    
                                     7. - All endpoints except /api/auth/** require a valid JWT Bearer token
                                        - - ADMIN role required for product management and admin dashboard
                                          - - Passwords are hashed using BCrypt before storage
                                            - - JWT tokens expire after 24 hours (configurable)
