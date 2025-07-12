# Demo gRPC with Spring Boot Project

A demonstration of gRPC-based microservices communication using Spring Boot and Protocol Buffers.

## Project Overview

This project showcases a microservices architecture with gRPC for inter-service communication. It consists of two main services:

1. **Auth Service**: Handles user authentication and management
2. **Customer Service**: Manages customer-related operations and demonstrates service-to-service communication with Auth Service

## Prerequisites

- Java 21
- Gradle 7.6+
- Protocol Buffers compiler (protoc)

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.0**
- **gRPC 1.58.0**
- **Protocol Buffers 3.25.1**
- **Spring gRPC 2.15.0**

## Project Structure

```
grpc-demo/
├── auth-service/           # Authentication and user management service
│   ├── src/
│   └── build.gradle
├── customer-service/       # Customer management service
│   ├── src/
│   └── build.gradle
└── README.md
```

## Getting Started

### Running the Services

1. Start the Auth Service:
   ```bash
   cd auth-service
   ./gradlew bootRun
   ```

2. Start the Customer Service (in a new terminal):
   ```bash
   cd customer-service
   ./gradlew bootRun
   ```

## API Documentation

### Auth Service gRPC API

The Auth Service exposes the following gRPC endpoints:

- **Create User**: `UserService/CreateUser`
- **Get User**: `UserService/GetUser`
- **Get All Users**: `UserService/GetAllUsers`
- **Update User**: `UserService/UpdateUser`
- **Delete User**: `UserService/DeleteUser`

### Protocol Buffers

The gRPC service definitions can be found in:
- `auth-service/src/main/proto/user.proto`

## Configuration

### Auth Service

Configuration file: `auth-service/src/main/resources/application.yml`

### Customer Service

Configuration file: `customer-service/src/main/resources/application.yml`

## Development

### Code Generation

Protocol buffer code is automatically generated during the build process. After making changes to `.proto` files, run:

```bash
./gradlew generateProto


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.