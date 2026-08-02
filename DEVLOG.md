## 🏁 DEVLOG: CVAM 2.0 - Spring Boot Migration

## Session 1 — August 1, 2026

### 📌 Current State & Accomplishments

* **Clean Spring Initializr Foundation**: Configured a Maven-based Java Spring Boot application with `Spring Web` and
  `Spring Boot Actuator` dependencies.
* **Manual Model Migration**: Re-typed all five self-protecting domain models (`User`, `Appointment`, `Citizen`,
  `Doctor`, and `Staff`) into the singular `com.cvam.cvam_v2_spring.model` package, preserving strict final field
  immutability and guard-clause validations.
* **Upgraded Layered Architecture**:
    * Migrated the old `AppointmentService` logic into a managed Spring `@Service` bean using the singular `service`
      package.
    * Developed an `AppointmentController` leveraging **Constructor Injection** to wire up the service.
    * Successfully verified the container with `mvn clean compile` yielding a `BUILD SUCCESS`.
* **Live Web Test**: Ran the web server on port 8080 and validated that a `@GetMapping("/api/appointments")` correctly
  marshals an in-memory instantiated object graph into native JSON outputs inside the browser.

### 🚀 Next Session - Pick Up Here

1. **Transition to Dynamic Requests (`@PostMapping`)**:
    * Implement a web-accessible booking endpoint to remove the controller's hardcoded mock variables.
    * Explore how Spring and Jackson handle structural inbound request bodies (`@RequestBody`) map-matching directly to
      Java objects.
2. **Handle Validation and Immutability Edge Cases**:
    * Evaluate how inbound web data interacts with our strict model constructors, ensuring our data transfer remains
      thread-safe and resilient.
3. **Database Security Layer**:
    * Introduce the MySQL database engine dependencies without violating credential exposure guidelines by configuring
      local IntelliJ environment variables (`${DB_USERNAME}`).

## Session 2 — August 2, 2026

### 📌 Current State & Accomplishments

Today, the CVAM application made the leap from a static, in-memory reader into an interactive, multi-layered REST API
with an active database connection pool.

1. **Dynamic Routing & Mapping**:
    * Resolved a `405 Method Not Allowed` routing conflict by consolidating class-level and method-level mappings.
    * Successfully established an active `@PostMapping` endpoint to listen for inbound internet payload traffic.

2. **Decoupled Architecture with DTOs**:
    * Implemented the **Data Transfer Object (DTO) Pattern** by designing `AppointmentRequest`.
    * **Why?** This elegantly separates wide-open, mutable web JSON formatting from our core self-protecting domain
      models, preserving their strict `final` field immutability and constructor validation guard rails.

3. **IntelliJ HTTP Client Scripting**:
    * Initialized an isolated `test.http` workspace file.
    * Successfully executed simulated browser payloads to prove end-to-end routing validation, verifying that the
      embedded Jackson library accurately maps JSON data into Java objects.

4. **Production-Ready Persistence Setup (`pom.xml`)**:
    * Cleaned up foundational environment dependencies. Upgraded from low-level JDBC to full object-relational mapping
      capabilities via `spring-boot-starter-data-jpa`.
    * Stabilized development targets by aligning the Maven parent framework runtime tracking parameters.

5. **Stateful Database Connection Pool & Secrets Insulation**:
    * Configured `application.properties` to cleanly point to a custom local schema database instance (`cvam_registry`).
    * Leveraged modern environment placeholders (`${DB_USERNAME}` / `${DB_PASSWORD}`) mapped to **IntelliJ Operating
      System Run Configurations**. This keeps production database secrets 100% hidden and completely secure from public
      GitHub leakage.
    * Booted the server to a flawless connection status, launching the stateful background database connection manager
      engine automatically.

---

### 🚀 Next Session — Pick Up Here

**Milestone 2: The Object-Relational Database Engine**.

1. **Transforming Models to Managed Entities- write Our First Database `@Entity` Rules**:
    * Decorate our immutable domain models (`User`, `Citizen`, `Doctor`) with Hibernate annotations (`@Entity`,
      `@Table`, `@Id`).
    * Learn how to resolve the ultimate Java framework paradox: satisfying Hibernate’s structural requirement for an
      empty, no-arguments constructor without breaking our strict domain validation guard rails.
2. **Implement the Automated Repository Layer**:
    * Create automated interface files extending `JpaRepository` to completely replace standard Java `ArrayList`
      containers.
3. **Bring the System Live**:
    * Open phpMyAdmin/IntelliJ Database view to watch Hibernate read our Java files and automatically build out the
      physical SQL tables inside your local MySQL server.
