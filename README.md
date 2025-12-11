# Academic Scheduler

## 🛠️ Technology Stack

- **Backend Framework**: Spring Boot 3.5.7
- **Java Version**: Java 21
- **Build Tool**: Gradle
- **Template Engine**: Thymeleaf with Layout Dialect
- **Database**: MySQL 8
- **ORM**: Spring Data JPA / Hibernate
- **UI Framework**: Bootstrap 5 (Flatly theme)
- **Testing**: JUnit 5, Testcontainers
- **Additional Libraries**:
  - Lombok - Reduce boilerplate code
  - Spring Boot DevTools - Development utilities
  - Spring Validation - Input validation

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Wu-Chun-Ming/Academic-Scheduler.git
cd Academic-Scheduler
```

### 2. Configure Database

Create a MySQL database and update the `src/main/resources/application.yml` file:

```yaml
spring:
  datasource:
    url: jdbc:mysql://<host>:<port>/<database>
    username: <username>
    password: <password>
```

**Note**:
- `<host>` — The hostname or IP address where MySQL is running.
  - Use `localhost` if running MySQL on your machine.
- `<port>` — The MySQL port number (default is 3306).
- `<database>` — The name of the database created.
- `<username>` — MySQL username.
- `<password>` — MySQL password.

### 3. Database Schema

The application uses Hibernate DDL auto configuration. Set the appropriate value in `application.yml`:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: none  # Options: create, update, validate, none
```

### 4. Run the Application

Using Gradle wrapper:

```bash
# On Windows
gradlew bootRun

# On Linux/Mac
./gradlew bootRun
```

Or using your installed Gradle:

```bash
gradle bootRun
```

### 5. Access the Application

Open your browser and navigate to:

```
http://localhost:8080
```

## 📁 Project Structure

```
Academic-Scheduler/
├── gradle/                       # Gradle wrapper files
├── src/
│   ├── main/
│   │   ├── java/io/github/wcm/academicscheduler/
│   │   │   ├── config/           # Configuration classes
│   │   │   ├── controller/       # REST and MVC controllers
│   │   │   ├── domain/           # Entity models
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── exception/        # Custom exceptions
│   │   │   ├── repository/       # JPA repositories
│   │   │   ├── service/          # Business logic services
│   │   │   └── AcademicSchedulerApplication.java
│   │   └── resources/
│   │       ├── application.yml   # Application configuration
│   │       ├── static/           # CSS and static resources
│   │       └── templates/        # Thymeleaf templates
│   └── test/
│       └── java/io/github/wcm/academicscheduler/
│           ├── repository/       # Repository tests
│           ├── service/          # Service unit tests
│           └── AcademicSchedulerApplicationTests.java
├── build.gradle                  # Gradle build configuration
├── gradlew                       # Gradle wrapper (Unix)
├── gradlew.bat                   # Gradle wrapper (Windows)
└── gradlew / gradlew.bat         # Gradle wrapper scripts
```

## 🔌 API Endpoints

### Course Endpoints

- `GET /api/courses` - Get all courses
- `GET /api/courses/{code}` - Get course by code
- `POST /api/courses` - Create new course
- `PUT /api/courses/{code}` - Update course
- `DELETE /api/courses/{code}` - Delete course

### Schedule Endpoints

- `GET /api/schedules` - Get all schedules
- `GET /api/schedules/{id}` - Get schedule by ID
- `POST /api/schedules` - Create new schedule
- `PUT /api/schedules/{id}` - Update schedule
- `DELETE /api/schedules/{id}` - Delete schedule

## 🧪 Testing

Run the test suite:

```bash
# Using Gradle wrapper
gradlew test

# Or
gradle test
```

View test reports in `build/reports/tests/test/index.html`

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `application.yml`
   - Ensure database exists

2. **Port Already in Use**
   - Change the server port in `application.yml`:
     ```yaml
     server:
       port: 8081
     ```

3. **Build Errors**
   - Ensure Java 21 is installed: `java -version`
   - Clear Gradle cache: `gradlew clean`
