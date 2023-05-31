# ABC-XMLUploader-Service

Spring Boot XML File Upload Service

## Overview

This project is a Spring Boot service responsible for receiving, validating, and storing XML files. It provides a REST API endpoint where users can upload XML files. Upon receiving a file, the service performs XML validation against XSD files, stores the validated file on the file system, and sends an event via Kafka to notify the XML Processor service for further processing.

The service incorporates an Interception mechanism for user authentication. The `AuthenticationInterceptor` class intercepts incoming requests, extracts the JWT token from the request header, authenticates the token by calling the [ABC-Authentication-Service](https://github.com/fargholamian/ABC-Authentication-Service) (`/api/user`), and retrieves user information. The user information is then added to the request as a request attribute, which can be accessed later in the controller.

The project is implemented using Spring Boot 3.1, Spring Web, and Spring Kafka. XML validation against XSD files is performed using the `StAX API`.

After startup, the service listens on port 8082. This applies to both running the service in an IDE or within a Docker container using `docker-compose` in the `abc-orchestrator` repository.

## Functionality
The xml-uploader service offers the following REST APIs:

The service exposes the following REST API endpoint:

1. **XML File Upload** - `POST /api/upload`: This API allows users to upload XML files.

    ```
    curl --location --request POST 'http://localhost:8082/api/upload' \
    --header 'Authorization: Bearer AUTH_TOKEN' \
    --header 'Cookie: JSESSIONID=1FCC74B73931C1729E3DFD47664314C3' \
    --form 'file=@"file.xml"'
    ```

## Project Structure

The project follows a standard Spring Boot project structure:

- **configuration**: Contains all the application configurations.
    - `AppConfig`: Reads all the configurations from `application.yaml` and provides them to other classes.
    - `AuthenticationInterceptor`: Implements the `HandlerInterceptor` interface and intercepts incoming requests. It uses the `AuthenticationService` for user authentication and token validation. Additionally, it checks the user's role, currently allowing only `ROLE_USER`.
    - `WebMvcConfig`: Implements the `WebMvcConfigurer` interface and adds the interceptor to the `InterceptorRegistry`.

- **controller**: Contains the controller classes.
    - `FileUploadController`: Handles file upload requests. It uses the `XmlService` for XML validation and file storage, as well as the `EventProducer` service to send an event to the XML Processor service.
    - `GenericExceptionHandler`: A generic exception handler that overrides the default `ResponseEntity` for runtime exceptions.

- **enum**: Contains enumerations used in the project.

- **model**: Contains all the POJO (Plain Old Java Object) classes.
    - `ApiError`: Represents a generic exception response.
    - `Event`: Represents the contract for sending events to Kafka.
    - `User`: Contains the user model.

- **services**: Contains all the service classes.
    - `XmlService`: Responsible for validating and storing XML files. It uses the `XMLValidator` utility for validation.
    - `EventProducer`: A service that receives events from the controller and sends them to the Kafka topic `file-received-event`.
    - `AuthenticationService`: A service that uses `RestTemplate` to call the authentication service (`/api/user`) and retrieve user information.

## Prerequisites

To run this project, ensure that you have the following software installed:

- Java Development Kit (JDK) 17 or higher
- Gradle
- Kafka 
- [ABC-Authentication-Service](https://github.com/fargholamian/ABC-Authentication-Service)

## Getting Started

Please see the instruction in [abc-orchestrator](https://github.com/fargholamian/abc-orchestrator) which will help you to run all the services together.

Anyway, If you want to run this project independently, follow the steps below to set up and run the project:

1. Clone the repository to your local machine.
2. Modify the configuration parameters (e.g. `upload.directory`) as needed for your environment. Find it in the `application.yaml` file located in the `classpath` directory.
3. Open a terminal and navigate to the project's root directory.
4. Build and run the project using Maven:
   ```
   ./gradlew bootRun
   ```
The service will now be running and listening on port 8082.
