FEATURES
User Management: CRUD operations for user entities.
Create, update, and retrieve users.
Authentication: Login functionality with JWT-based authentication provided by Spring Security.
Secure token-based authentication.
Role-based access control.

CONFIGURATION
MySQL Database Integration:
Driver Class Name: com.mysql.cj.jdbc.Driver
Database URL: jdbc:mysql://localhost:3306/user?serverTimezone=UTC
Username: root
Password: 

SPRING BOOT CONFIGURATION:
Server Port: 8082

JPA SETTINGS
DDL Auto: update
Dialect: MySQL8Dialect

ENDPOINT
POST "localhost:8082/auth/login": Authenticates users and returns a JWT token along with user details.

DEPENDENCIES
Spring Boot
Spring Data JPA
Spring Security (for JWT handling and authentication)
MySQL Connector

SETUP INSTRUCTIONS
Clone the repository:
git clone https://github.com/SantiagoCz/login_backend.git

Navigate to the project directory:
cd login_backend

Update application.properties with your database credentials.

Build and run the application:
./mvnw spring-boot:run

NOTES
Make sure to have MySQL running on localhost with a database named user.
Adjust database credentials and server port as needed.
