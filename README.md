# 📋 Task Management System

A robust, enterprise-grade RESTful API for managing tasks, built with **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**. Features comprehensive validation, global error handling, aspect-oriented logging (AOP), and interactive API documentation powered by OpenAPI / Swagger.

---

## 🚀 Features

- **Full Task Lifecycle Management:** Create, retrieve, partially update, and delete tasks.
- **Data Validation & Integrity:** Strict request validation using Spring & Jakarta Bean Validation.
- **Aspect-Oriented Logging (AOP):** Centralized controller request/response logging using Spring AOP (`ControllerLoggingAspect`).
- **Global Exception Handling:** Standardized error payloads (`ValidationExceptionResponseDto`, `ExceptionResponseDto`) for bad requests, missing entities, and database errors.
- **Interactive API Documentation:** Integrated Swagger UI / OpenAPI documentation via `springdoc-openapi`.
- **Environment-based Configuration:** Profile-driven configuration (`dev`, `prod`) with externalized database credentials.

---

## 🛠️ Tech Stack

- **Language:** Java
- **Framework:** Spring Boot 4.x
- **Persistence:** Spring Data JPA / Hibernate
- **Database:** PostgreSQL
- **Documentation:** Springdoc OpenAPI (Swagger UI)
- **Utilities:** Project Lombok, AspectJ
- **Build Tool:** Maven Wrapper (`./mvnw`)

---

## 📁 Project Structure

```text
src/main/java/org/blackbergx9/taskmanagementsystem/
├── ControllerLoggingAspect.java         # AOP logging for controllers
├── TaskManagementSystem.java            # Main Spring Boot application entry point
├── controller/
│   ├── Health.java                      # Health check endpoint
│   └── TaskController.java              # REST endpoints for task operations
├── dto/
│   ├── request/                         # Incoming payload DTOs with validation rules
│   │   ├── CreateTaskRequestDto.java
│   │   └── UpdateTaskRequestDto.java
│   └── res/                             # Outgoing response DTOs & error schemas
│       ├── CreateTaskResponseDto.java
│       ├── ExceptionResponseDto
│       ├── GetAllTaskResponseDto.java
│       ├── GetTaskResponseDto.java
│       ├── UpdateTaskResponseDto.java
│       └── ValidationExceptionResponseDto.java
├── entity/
│   └── Task.java                        # JPA entity representing the Task table
├── exception/
│   ├── GlobalExceptionHandler.java      # Centralized exception handling
│   ├── ResourceNotFoundException.java
│   └── DatabaseErrorException.java
├── mapper/
│   └── TaskMapper.java                  # Entity-to-DTO and DTO-to-Entity mapping
├── repository/
│   └── TaskRepository.java              # Spring Data JPA Repository
├── service/
│   └── TaskService.java                 # Business logic and transaction management
└── util/
    ├── Priority.java                    # Enum: LOW, MEDIUM, HIGH
    └── Status.java                      # Enum: PENDING, IN_PROGRESS, COMPLETED
```

---

## ⚙️ Getting Started

### Prerequisites

- **Java JDK 21** or compatible version installed.
- **PostgreSQL** instance running locally or remotely.
- **Git** installed on your system.

### 1. Clone the Repository

```bash
git clone https://github.com/BlackBergX9/TaskManagementSystem.git
cd TaskManagementSystem
```

### 2. Configure Database & Environment Variables

Ensure your PostgreSQL database exists:

```sql
CREATE DATABASE task_management_system;
```

Set the required environment variables (or supply them at launch time):

| Variable | Description | Example |
| :--- | :--- | :--- |
| `dbUsername` | PostgreSQL database user | `postgres` |
| `dbPassword` | PostgreSQL database password | `secret` |

On **Windows (PowerShell)**:
```powershell
$env:dbUsername="postgres"
$env:dbPassword="your_password"
```

On **Linux / macOS**:
```bash
export dbUsername="postgres"
export dbPassword="your_password"
```

### 3. Build & Run

Using the included Maven wrapper:

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.

---

## 📖 API Documentation & Swagger

Once the application is running, view and test the API interactively:

- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🔌 API Endpoints Summary

### Health Check

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/` | Health check route (`{"message": "UP"}`) |

### Tasks API (`/api/tasks`)

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/tasks` | Create a new task | `201 Created` |
| `GET` | `/api/tasks` | List tasks (pagination & search supported) | `200 OK` |
| `GET` | `/api/tasks/{taskId}` | Retrieve task details by ID | `200 OK` |
| `PATCH` | `/api/tasks/{taskId}` | Partially update task fields | `200 OK` |
| `DELETE` | `/api/tasks/{taskId}` | Delete a task | `204 No Content` |

---

### Request & Response Examples

#### 1. Create a Task

`POST /api/tasks`

**Request Body:**
```json
{
  "title": "Set up CI/CD Pipeline",
  "description": "Configure GitHub Actions workflow for build and test automation",
  "assignee": "kishan",
  "priority": "HIGH",
  "status": "PENDING",
  "dueDate": "2026-12-31T18:00:00"
}
```

**Response (`201 Created`):**
```json
{
  "id": 1,
  "title": "Set up CI/CD Pipeline",
  "description": "Configure GitHub Actions workflow for build and test automation",
  "assignee": "kishan",
  "priority": "HIGH",
  "status": "PENDING",
  "dueDate": "2026-12-31T18:00:00",
  "createdAt": "2026-10-13T04:40:00Z"
}
```

#### 2. Get All Tasks

`GET /api/tasks?page=0&limit=10&s=`

**Query Parameters:**
- `page`: Page index (default `0`)
- `limit`: Number of tasks per page (default `10`)
- `s`: Search term filter

**Response (`200 OK`):**
```json
{
  "tasks": [
    {
      "id": 1,
      "title": "Set up CI/CD Pipeline",
      "priority": "HIGH",
      "status": "PENDING",
      "assignee": "kishan",
      "dueDate": "2026-12-31T18:00:00"
    }
  ]
}
```

#### 3. Update a Task

`PATCH /api/tasks/1`

**Request Body:**
```json
{
  "status": "IN_PROGRESS",
  "priority": "MEDIUM"
}
```

**Response (`200 OK`):**
```json
{
  "updatedTask": {
    "id": 1,
    "title": "Set up CI/CD Pipeline",
    "description": "Configure GitHub Actions workflow for build and test automation",
    "assignee": "kishan",
    "priority": "MEDIUM",
    "status": "IN_PROGRESS",
    "dueDate": "2026-12-31T18:00:00",
    "updatedAt": "2026-10-13T04:45:00Z"
  }
}
```

---

## 🔮 Roadmap - (future updates will include)

- [ ] User authentication & authorization (Spring Security + JWT).
- [ ] User-specific task ownership and multi-tenancy.
- [ ] Advanced sorting, dynamic filtering, and full-text search.
- [ ] Task completion tracking and auditing.
- [ ] Email notifications / reminders on approaching due dates.
