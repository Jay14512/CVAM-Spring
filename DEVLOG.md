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
