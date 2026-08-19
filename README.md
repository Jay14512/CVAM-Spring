# Citizen Vaccine Appointment Manager (CVAM) — Spring Boot

Spring Boot project for modeling and managing vaccine appointments with a layered Java backend.

> This repository documents my transition from PHP to Java and Spring Boot. I keep it public as a portfolio project so the progress, structure, and quality improvements are visible over time.

## Project Status

- Current stage: Active development
- Focus: Domain modeling, booking rules, REST endpoints, and persistence work
- Last updated milestone: Spring Boot migration and API foundation

## What This Project Demonstrates

- OOP and domain modeling in Java (`User`, `Appointment`, `DoctorProfile`, `StaffProfile`)
- Layered backend structure (`controller`, `service`, `repository`, `model`, `dto`, `exception`)
- Constructor-based validation and custom runtime exceptions
- REST API design with Spring Web
- JPA-based repository structure for future persistence work

## Current Features

- Domain models:
  - `User`
  - `Appointment`
  - `DoctorProfile`
  - `StaffProfile`
  - `ShiftAssignment`
- REST endpoints:
  - `GET /api/appointments`
  - `POST /api/appointments`
- Service layer for booking appointments and applying business rules
- Centralized exception handling with custom API error responses
- Demo data seeding for local development

## In Progress / Next Steps

- Replace demo booking data with fully persistent appointment flows
- Expand validation for request payloads and domain rules
- Add appointment lookup and cancellation operations
- Improve test coverage for service and controller behavior
- Clean up the API response structure for production use

## Tech Stack

- Java 21
- Spring Boot 4.1.x
- Spring Web
- Spring Data JPA
- Maven
- MySQL connector

## Project Structure

```text
src/main/java/com/cvam/cvam_v2_spring/
  CvamV2SpringApplication.java
  controller/
    AppointmentController.java
  dto/
    AppointmentRequest.java
    AppointmentImportDto.java
    DoctorImportDto.java
    StaffImportDto.java
    UserImportDto.java
  exception/
    ApiError.java
    AppointmentConflictException.java
    AppointmentNotFoundException.java
    EmailAlreadyRegisteredException.java
    FiscalCodeAlreadyRegisteredException.java
    GlobalExceptionHandler.java
    InvalidAppointmentException.java
  model/
    Appointment.java
    DoctorProfile.java
    StaffProfile.java
    ShiftAssignment.java
    User.java
  repository/
    AppointmentRepository.java
    DoctorProfileRepository.java
    StaffProfileRepository.java
    ShiftAssignmentRepository.java
    UserRepository.java
  service/
    AppointmentService.java
    DoctorService.java
    StaffService.java
    UserService.java
  util/
    DataSeeder.java
```

## How To Run

### Prerequisites

- JDK 21
- Maven is optional because the project includes the Maven Wrapper

### Run from project root

```bash
./mvnw spring-boot:run
```

On Windows:

```cmd
.\mvnw.cmd spring-boot:run
```

Then open `http://localhost:8080/api/appointments`.

## Why This Repo Is Public Early

I keep this repository public to show how the project evolves from a learning backend into a more complete Java application. The goal is to make the structure, design choices, and feature growth easy to follow as the codebase matures.

## Future Direction

The long-term goal is to turn this into a portfolio-ready appointment management API with real persistence, stronger validation, and cleaner workflows around booking, cancellation, and user management. I want the project to show steady progress from a simple learning exercise into a well-structured Spring backend.

## Notes

- This project still includes development/demo behavior in some places.
- It is a learning and portfolio project, not production software.
- Database credentials are supplied through IntelliJ run configuration environment variables, not committed files.

## License

Licensed under the MIT License. See `LICENSE`.
