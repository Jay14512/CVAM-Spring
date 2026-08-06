## 🏁 DEVLOG: CVAM 2.0 - Spring Boot Migration

## Session 1 — August 1, 2026

### 📌 Current State & Accomplishments

* **Clean Spring Initializr Foundation**: Configured a Maven-based Java Spring Boot application with `Spring Web` and
  `Spring Boot Actuator` dependencies.
* **Manual Model Migration**: Re-typed all five self-protecting domain models (`User`, `Appointment`, `CitizenProfile`,
  `DoctorProfile`, and `StaffProfile`) into the singular `com.cvam.cvam_v2_spring.model` package, preserving strict
  final field immutability and guard-clause validations.
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

---

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

### 🚀 Next Session — Pick Up Here

**Milestone 2: The Object-Relational Database Engine**.

1. **Transforming Models to Managed Entities - write Our First Database `@Entity` Rules**:
    * Decorate our immutable domain models (`User`, `CitizenProfile`, `DoctorProfile`) with Hibernate annotations
      (`@Entity`,
      `@Table`, `@Id`).
    * Learn how to resolve the ultimate Java framework paradox: satisfying Hibernate’s structural requirement for an
      empty, no-arguments constructor without breaking our strict domain validation guard rails.
2. **Implement the Automated Repository Layer**:
    * Create automated interface files extending `JpaRepository` to completely replace standard Java `ArrayList`
      containers.
3. **Bring the System Live**:
    * Open phpMyAdmin/IntelliJ Database view to watch Hibernate read our Java files and automatically build out the
      physical SQL tables inside your local MySQL server.

---

## Session 3 — August 3, 2026

### 📌 Current State & Accomplishments

Today focused on understanding and stabilizing the JPA entity model design decisions.

1. **Inheritance Strategy Decision**:
    * Chosen direction: `@MappedSuperclass` for `User` as the shared base for `CitizenProfile`, `DoctorProfile`, and
      `StaffProfile`.
    * Clarified why this fits current architecture: shared fields without requiring a standalone `users` table.

2. **Entity Lifecycle Rules Clarified**:
    * Documented the JPA constructor pattern:
        * `protected` no-arg constructor for Hibernate
        * validating public constructor for application-level guard rails
    * Confirmed that entity-managed fields should not be `final` when using standard JPA/Hibernate hydration.

3. **Relationship Mapping Foundation**:
    * Clarified the mapping split:
        * scalar fields (`String`, `LocalDate`, etc.) -> `@Column`
        * entity references (`DoctorProfile`, `CitizenProfile`) -> relationship annotations (`@ManyToOne`,
          `@JoinColumn`, etc.)
    * Started converting `StaffProfile` and `Appointment` toward object-reference relationship mapping.

4. **Uniqueness vs Identity Model Locked In**:
    * Confirmed primary key strategy: technical DB identity via `Long id` + `@Id`.
    * Confirmed business uniqueness strategy as separate constraints (for fields like `fiscalCode`, `doctorId`,
      `staffCode`, `appointmentId`) instead of forcing uniqueness on unrelated fields like `birthDate`.

### 🚀 Next Session — Pick Up Here (Tomorrow)

**Milestone 3: Finalize Entity Integrity + Repository Persistence**

1. **Complete and Validate Entity Compilation Rules**:
    * Ensure every `@Entity` has:
        * `@Id`
        * `protected` no-arg constructor
        * valid field types/getters for JPA mapping
    * Fix any remaining type/signature mismatches (especially around relationship fields and getters).

2. **Apply Constraints at the Correct Layer**:
    * Add definitive `@Column(nullable = false, unique = true)` constraints only to true business-unique fields.
    * Keep non-unique domain attributes (like birthdate) as required columns without uniqueness constraints.

3. **Finalize Relationship Mappings**:
    * Convert remaining FK-like scalar references to object relationships where needed (`@ManyToOne` + `@JoinColumn`).
    * Verify that `Appointment` references to `CitizenProfile` and `DoctorProfile` are correctly mapped as associations.

4. **Introduce Repository Layer + First DB-Backed Flow**:
    * Create first `JpaRepository` interfaces for core entities.
    * Replace one in-memory lookup path with repository-backed persistence to prove end-to-end DB integration.

---

## Session 4 — August 4, 2026

### 📌 Current State & Accomplishments

Today's goal was to transition the application from an in-memory Core Java prototype into a persistent, database-backed
Spring Boot application using **Spring Data JPA** and **MySQL**.

1. **Moved Away from Subclass Inheritance**:
    * Realized that using class inheritance (`extends User`) breaks in real-world scenarios (e.g., a physical person
      cannot be two distinct Java objects if they are both a staff member and a patient).
    * Refactored the core domain models from inheritance to **Composition Over Inheritance**.

2. **Centralized Core Identity (`User` Entity)**:
    * Established `User` as a concrete, central hub entity holding universal human traits: `firstName`, `lastName`,
      `fiscalCode`, `email`, `phoneNumber`, and `birthDate`.
    * Enforced strict **database-level unique constraints** on `fiscalCode` and `email`.

3. **Introduced Role Profiles**:
    * Created specialized, decoupled profile entities (`CitizenProfile`, `DoctorProfile`, `StaffProfile`) that map back
      to the unique `User` identity using `@OneToOne` and `@ManyToOne` relationships.
    * Added an optional, nullable `officePhoneNumber` (direct extension line) to `DoctorProfile` to support realistic
      workplace workflows.
    * Designed `StaffProfile` using composition, cleanly allowing a single staff member to hold multi-job contracts
      under different doctors without breaking database uniqueness constraints.

4. **Finalized Relationship Mappings**:
    * Established bidirectional `@OneToMany` structures between the profiles and the `Appointment` entity.
    * Utilized `FetchType.LAZY` to ensure optimal performance.

5. **Database Migration Success**:
    * Successfully connected the application to local MySQL (`cvam_registry`).
    * Configured `spring.jpa.hibernate.ddl-auto=update` and resolved all constructor signature conflicts.
    * The application now **boots up flawlessly**, auto-generating all **5 normalized tables**.

### 🚀 Next Session — Pick Up Here

1. **Create the `JpaRepository` interfaces** for the new profiles.
2. **Refactor `AppointmentService`** to utilize database-backed persistence instead of hardcoded mock records.

---

## Session 5 — August 5, 2026

### 📌 Current State & Accomplishments

Today focused on wiring repository-driven business flows and stabilizing service-level compile/runtime rules.

1. **Repository Layer Expansion and Query Methods**:
    * Added/updated Spring Data repositories for `User`, `CitizenProfile`, `DoctorProfile`, `StaffProfile`, and
      `Appointment`.
    * Introduced domain-specific lookup and existence checks for key business rules (email/fiscal code uniqueness,
      medical license uniqueness, staff code uniqueness, and appointment collision checks).
    * Added profile retrieval methods based on nested user data paths, and a JPQL `JOIN FETCH` helper for
      `CitizenProfile` to support cleaner lazy-loading behavior.

2. **Service Layer Transaction Workflows**:
    * Built out `UserService` transactional flows for:
        * core user registration with uniqueness validation
        * citizen onboarding (`User` + `CitizenProfile`) in one atomic transaction
    * Built out `DoctorService` transactional flows for:
        * doctor onboarding (`User` + `DoctorProfile`)
        * assigning staff to a doctor (`User` + `StaffProfile` with doctor FK validation)

3. **Appointment Rules Moved to Repository-Backed Guards**:
    * Updated `AppointmentService` to enforce duplicate-appointment protection and doctor/date-time collision checks via
      repository methods.
    * Preserved custom domain exceptions for invalid input, conflicts, and not-found cancellation paths.

4. **Compile Error Stabilization**:
    * Fixed a structural syntax issue in `UserService` (missing method-closing brace) that was breaking method parsing.
    * Fixed a repository signature mismatch in `AppointmentRepository` so the doctor/date-time existence check matches
      service usage.

### 🚀 Next Session — Pick Up Here

1. **Connect Services to Controllers End-to-End**:
    * Add/finish controller endpoints for the new doctor/staff/user registration workflows.
    * Ensure request payloads map cleanly into DTOs/domain objects for onboarding operations.

2. **Harden Validation and Error Mapping**:
    * Replace generic `IllegalArgumentException` exposure paths with consistent API-level error responses
      (`@ControllerAdvice` / structured error payloads).
    * Normalize naming and casing for identifiers (`license`/`licence`, fiscal code, doctor ID fields) to reduce
      accidental mismatch bugs.

3. **Run Full API Flow Tests Against MySQL**:
    * Verify create/read/cancel flows for appointments with real persisted `User`, `CitizenProfile`, and `DoctorProfile`
      records.
    * Confirm duplicate guards and collision checks behave correctly under repeated requests.

4. **Add new Exceptions**:
    * EmailAlreadyRegisteredException
    * FiscalCodeAlreadyRegisteredException
    * Optionally: give both Exceptions a second constructor that accepts a custom message when needed
    * Map them in a global @ControllerAdvice so they return a stable HTTP status (usually 409 CONFLICT for duplicates).

---

## Session 6 — August 6, 2026

### 📌 Current State & Accomplishments

Today was a focused cleanup pass on API error handling consistency.

1. **Added Specific Custom Exceptions**:
    * Introduced dedicated domain exceptions with hardcoded default messages to avoid repeating message literals across services.
    * Updated constructor signatures to support no-arg usage (`throw new ...Exception();`) while still propagating the intended message.

2. **Introduced Global Exception Handling**:
    * Added a centralized `GlobalExceptionHandler` using Spring advice annotations to map exceptions in one place.
    * Reduced controller/service clutter by moving HTTP error response shaping out of business logic.

3. **Standardized API Error Payload**:
    * Added `ApiError` as the structured response body for handled failures.
    * Established a single error contract pattern for client-facing exception responses.

### 🚀 Next Session — Pick Up Here

1. **Connect Services to Controllers End-to-End**:
   * Add/finish controller endpoints for the new doctor/staff/user registration workflows.
   * Ensure request payloads map cleanly into DTOs/domain objects for onboarding operations.

2. **Harden Validation and Error Mapping**:
   * Replace generic `IllegalArgumentException` exposure paths with consistent API-level error responses
     (`@ControllerAdvice` / structured error payloads).
   * Normalize naming and casing for identifiers (`license`/`licence`, fiscal code, doctor ID fields) to reduce
     accidental mismatch bugs.

3. **Run Full API Flow Tests Against MySQL**:
   * Verify create/read/cancel flows for appointments with real persisted `User`, `CitizenProfile`, and `DoctorProfile`
     records.
   * Confirm duplicate guards and collision checks behave correctly under repeated requests.

4. Wire any remaining domain exceptions into `GlobalExceptionHandler` with explicit status mappings.
5. Confirm all onboarding endpoints return the same `ApiError` shape for validation/conflict/not-found paths.

---