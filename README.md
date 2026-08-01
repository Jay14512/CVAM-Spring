# Citizen Vaccine Appointment Manager (CVAM) — Spring Boot 2.0

A modern, production-ready REST API built with Spring Boot to automate and manage vaccine appointment bookings. This
iteration advances the initial core Java architecture into a scalable, multi-layered enterprise backend.

> This project documents my hands-on transition from PHP to Java/Spring Boot. It is intentionally public to demonstrate
> framework proficiency, architectural evolution, and software craftsmanship over time.

## 📌 Project Status

- **Current Stage**: Active Development (Milestone 1 Complete)
- **Architecture**: Three-Layer Architecture (`controller` ➡️ `service` ➡️ `repository`)
- **Last Updated Milestone**: Spring Boot Migration & REST Layer Setup

## 🛠️ What This Project Demonstrates

- **Spring Boot Ecosystem**: Native web container integration using `Spring Web` and automated system health metrics via
  `Spring Boot Actuator`.
- **Dependency Injection**: Loose coupling achieved through strict Constructor-based Dependency Injection
  (`@RestController` linked to a managed `@Service` bean).
- **Self-Protecting Domain Layer**: Bulletproof object graph architecture. Every domain model features final
  immutability, zero setters, and strict validation guard clauses right inside the constructors.
- **Convention over Configuration**: Utilization of the **Maven Wrapper (`mvnw`)** to isolate and standardize the
  project runtime across different local environments.
- **JSON Serialization Pipeline**: Automatic recursive object graph parsing using embedded Jackson providers.

## 🚀 Current Architecture & Features

### Project Package Layout

Following strict Java singular-naming conventions:

```text
cvam-v2-spring/
├── .mvn/                             <-- Isolated Maven Wrapper
├── src/main/java/com/cvam/cvam_v2_spring/
│   ├── controller/                   <-- REST Gateways (@RestController)
│   │   └── AppointmentController.java
│   ├── service/                      <-- Business Engine Logic (@Service)
│   │   └── AppointmentService.java
│   ├── model/                        <-- Self-Protecting Immutable Domain Models
│   │   ├── User.java (Abstract)
│   │   ├── Citizen.java / Doctor.java / Staff.java
│   │   └── Appointment.java
│   └── exception/                    <-- Custom Domain Runtime Exceptions
├── pom.xml                           <-- Maven Infrastructure Map
└── LICENSE
```

### Active Endpoints

* **`GET /actuator/health`** — Core system sanity and health monitoring status.
* **`GET /api/appointments`** — Retrieves an unmodifiable stream of all registered appointments.

## 📈 In Progress / Next Steps

- **Dynamic Booking (`POST /api/appointments`)**: Unlocking interactive payloads utilizing web-inbound `@RequestBody`
  streams mapped directly into our validation layer.
- **Secure Persistence Layer**: Migrating from an ephemeral in-memory storage matrix to a secure, local MySQL database
  via Spring Data JPA.
- **Secrets Management**: Isolating database credentials strictly within regional IntelliJ runtime environment variables
  (`${DB_USERNAME}`) to prevent repository leaks.

## 💻 Tech Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.4.x (Web, Actuator)
- **Build Automation**: Maven (via Maven Wrapper)

## 🏎️ How To Run

### Prerequisites

* **JDK 21** installed locally.
* You **do not** need a global Maven installation; the project handles its own engine files automatically.

### Running the Live Server

Open your terminal at the root directory of the project and execute:

**Windows (Command Prompt / PowerShell)**:

```cmd
.\mvnw.cmd spring-boot:run
```

**macOS / Linux**:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

Once the terminal text logs confirm that the integrated Tomcat engine has successfully mounted on port `8080`, navigate
to `http://localhost:8080/api/appointments` inside your browser to view the active payload stream.

## 📂 Portfolio Disclosure

I maintain this repository publicly to visually map my development trajectory from a procedural framework mindset to
enterprise Java engineering. Key focus areas highlighted in this codebase include:

* Strict structural layer segregation (decoupling the web engine from the business core).
* Comprehensive error defense layers (never allowing invalid system state mutations).
* Clean code consistency (IDE warning mitigation, descriptive naming structures).

## 📄 License

Licensed under the MIT License. See the `LICENSE` file for details.
