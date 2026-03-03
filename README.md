# BulagFaust - Modern Blog Platform Backend

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**A production-ready RESTful blog platform built with Spring Boot 3**

[Features](#-features) • [Tech Stack](#-tech-stack) • [Getting Started](#-getting-started) • [API Documentation](#-api-documentation) • [Project Highlights](#-project-highlights) • [Future Roadmap](#-future-roadmap)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Project Highlights](#-project-highlights)
- [Lessons Learned](#-lessons-learned)
- [Areas for Improvement](#-areas-for-improvement)
- [Future Roadmap (API v2)](#-future-roadmap-api-v2)
- [Author](#-author)

---

## 🎯 Overview

**BulagFaust** is a full-featured blog platform backend that demonstrates enterprise-level Spring Boot development practices. The system provides complete CRUD operations for blog posts, categories, and tags, with role-based access control, JWT authentication, and production-ready error handling.

The name "BulagFaust" represents a journey from blindness (Bulag) to mastery (Faust) — symbolizing the learning process throughout this project's development.

---

## ✨ Features

### Core Functionality
- **📝 Post Management** - Create, read, update, and delete blog posts with draft/published status
- **🏷️ Tag System** - Dynamic tag creation and assignment (auto-creates tags if they don't exist)
- **📁 Category Organization** - Hierarchical content categorization
- **👤 User Authentication** - JWT-based authentication with role-based authorization
- **🔐 Role-Based Access Control** - ADMIN and USER roles with granular permissions

### Technical Features
- **Pagination & Sorting** - Efficient data retrieval with customizable page sizes
- **Filtering** - Filter posts by category, tag, or author
- **Reading Time Calculation** - Automatic reading time estimation (200 WPM)
- **Soft Validation** - Comprehensive input validation with meaningful error messages
- **Database Indexing** - Optimized queries with strategic indexes on status, author, and timestamps
- **N+1 Query Prevention** - Batch loading with `@BatchSize` for efficient tag retrieval

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.2.4 |
| **Security** | Spring Security + JWT (JJWT 0.13.0) |
| **Database** | PostgreSQL |
| **ORM** | Spring Data JPA + Hibernate |
| **Validation** | Jakarta Validation API 3.1.1 |
| **Object Mapping** | MapStruct 1.6.3 |
| **Boilerplate Reduction** | Lombok 1.18.42 |
| **Containerization** | Docker + Docker Compose |
| **Build Tool** | Maven 3.x |

---

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker & Docker Compose (for database)
- Git

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/BulagFaust.git
   cd BulagFaust
   ```

2. **Start the database with Docker Compose**
   ```bash
   docker-compose up -d
   ```
   
   This starts:
   - PostgreSQL on `localhost:5432`
   - Adminer (database UI) on `localhost:8081`

3. **Configure the application**
   
   Update `src/main/resources/application.properties` if needed:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   ```

4. **Build and run**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

5. **Access the application**
   - API Base URL: `http://localhost:8080/api/v1`
   - Default Admin: `admin@localhost` / `admin123`

### Database Credentials (Development)

| Service | Host | Port | Username | Password |
|---------|------|------|----------|----------|
| PostgreSQL | localhost | 5432 | postgres | postgres |
| Adminer | localhost | 8081 | postgres | postgres |

> ⚠️ **Security Notice**: Change default credentials in production!

---

## 📚 API Documentation

### Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/api/v1/auth/register` | Register new user | ❌ |
| `POST` | `/api/v1/auth/login` | User login | ❌ |

### Post Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/api/v1/posts` | Get all posts (paginated) | ❌ |
| `GET` | `/api/v1/posts/{id}` | Get post by ID | ❌ |
| `POST` | `/api/v1/posts` | Create new post | ✅ |
| `PATCH` | `/api/v1/posts/{id}` | Update post | ✅ (Owner only) |
| `DELETE` | `/api/v1/posts/{id}` | Delete post | ✅ (Owner only) |

**Query Parameters for GET /posts:**
- `categoryId` - Filter by category UUID
- `tagId` - Filter by tag UUID
- `page` - Page number (default: 0)
- `size` - Page size (default: 20)
- `sort` - Sort field (default: createdAt, DESC)

### Category Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/api/v1/categories` | Get all categories | ❌ |
| `POST` | `/api/v1/categories` | Create category | ✅ (ADMIN only) |
| `PATCH` | `/api/v1/categories/{id}` | Update category | ✅ (ADMIN only) |
| `DELETE` | `/api/v1/categories/{id}` | Delete category | ✅ (ADMIN only) |

### Tag Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/api/v1/tags` | Get all tags | ❌ |
| `POST` | `/api/v1/tags` | Create tag | ✅ |

---

### Example Usage

#### 1. Register a New User

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "john@example.com",
    "password": "securepass123"
  }'
```

**Response:**
```json
{
  "timestamp": "2026-03-03T10:30:00",
  "status": 201,
  "message": "Registered successfully",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "id": "uuid-here",
      "username": "johndoe",
      "email": "john@example.com"
    }
  }
}
```

#### 2. Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "securepass123"
  }'
```

#### 3. Create a Post

```bash
curl -X POST http://localhost:8080/api/v1/posts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "My First Blog Post",
    "content": "This is the content of my amazing post...",
    "categoryIds": ["category-uuid-here"],
    "tagNames": ["spring", "java", "backend"]
  }'
```

#### 4. Get All Posts (with filtering)

```bash
curl -X GET "http://localhost:8080/api/v1/posts?size=10&page=0&sort=createdAt,desc"
```

#### 5. Update a Post

```bash
curl -X PATCH http://localhost:8080/api/v1/posts/{post-id} \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "Updated Title",
    "status": "PUBLISHED"
  }'
```

---

## 🌟 Project Highlights

### What Was Done Well

#### 1. **Clean Architecture & Separation of Concerns**
```
com.sysc.bulag_faust/
├── auth/          # Authentication & authorization
├── category/      # Category management
├── post/          # Post business logic
├── tag/           # Tag management
├── user/          # User management
├── role/          # Role-based access
└── core/          # Cross-cutting concerns
    ├── exceptions/
    ├── security/
    ├── response/
    └── utils/
```

**Why it matters:** Each module is self-contained, making the codebase maintainable and testable.

#### 2. **Efficient Database Queries**
- **Batch Loading**: Used `@BatchSize(size = 20)` to prevent N+1 queries when loading tags
- **Strategic Indexing**: Added indexes on frequently queried columns (`status`, `author_id`, `created_at`)
- **Pagination**: All list endpoints support pagination to handle large datasets

```java
// Example: Efficient tag resolution - single SELECT for all tags
private Set<Tag> resolveOrCreateTags(Set<String> tagNames) {
    List<Tag> existing = tagRepository.findAllByNameIn(normalized);
    // Only INSERT new tags, avoiding duplicate queries
}
```

#### 3. **Robust Error Handling**
- Centralized `@RestControllerAdvice` with 15+ exception handlers
- Consistent error response format with timestamps
- Appropriate HTTP status codes for different error scenarios
- Detailed logging for debugging without exposing sensitive info

#### 4. **Security Best Practices**
- JWT token-based authentication (stateless)
- BCrypt password hashing
- Role-based authorization (`hasRole("ADMIN")`, `authenticated()`)
- CORS configuration for frontend integration
- SQL injection prevention via JPA parameterized queries

#### 5. **Domain-Driven Design**
- Rich domain models with business logic encapsulated in entities
- `@PrePersist` and `@PreUpdate` for automatic timestamp management
- Entity validation methods (`validateForPublish()`)
- Immutable DTOs with Java records

#### 6. **Performance Optimizations**
- Automatic reading time calculation (200 WPM algorithm)
- Lazy loading for relationships
- `open-in-view=false` to prevent session leaks
- Efficient batch operations for tag creation

---

## 📖 Lessons Learned

### Things Done Differently (Growth Moments)

#### 1. **From Anemic to Rich Domain Models**
**Before:** Entities were just data containers with getters/setters.

**Now:** Entities encapsulate business logic:
```java
// Post entity handles its own validation
public void publish() {
    this.status = PostStatus.PUBLISHED;
    validateForPublish(); // Self-validation
}
```

**Why:** Reduces service layer bloat and keeps logic close to data.

#### 2. **From `save()` to Lifecycle Callbacks**
**Before:** Manual timestamp and reading time calculation in services.

**Now:** Using JPA lifecycle callbacks:
```java
@PrePersist
protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    setReadingTime(); // Automatic
}
```

**Why:** DRY principle - logic defined once, triggered automatically.

#### 3. **From Generic Exceptions to Custom Hierarchy**
**Before:** Throwing `IllegalArgumentException` everywhere.

**Now:** Custom exception hierarchy:
```java
throw new NotFoundException("Post", id);
throw new AlreadyExistException("User", email);
```

**Why:** Clearer intent, consistent error responses, easier debugging.

#### 4. **From Eager to Strategic Lazy Loading**
**Before:** `FetchType.EAGER` on all relationships.

**Now:** `FetchType.LAZY` with `@BatchSize` for collections:
```java
@BatchSize(size = 20)
private Set<Tag> tags;
```

**Why:** Prevents performance issues with large datasets.

#### 5. **From Manual Mapping to MapStruct**
**Before:** Manual DTO-to-entity conversion.

**Now:** Type-safe compile-time mapping with MapStruct:
```java
@Mapper
public interface PostMapper {
    PostResponse toResponse(Post post);
}
```

**Why:** Reduces boilerplate, catches mapping errors at compile time.

#### 6. **From Monolithic Services to Layered Architecture**
**Before:** All logic in controllers.

**Now:** Clear separation:
```
Controller → Service → Repository → Entity
     ↓          ↓          ↓          ↓
  HTTP      Business    Data      Domain
  Logic     Logic      Access    Logic
```

**Why:** Testability, maintainability, single responsibility.

---

## 🔧 Areas for Improvement

### Current Limitations

#### 1. **Testing Coverage**
- ❌ No unit tests for services
- ❌ No integration tests for controllers
- ❌ No test containers for database testing
- ❌ No mock objects for external dependencies

**Impact:** Risk of regressions, harder refactoring.

#### 2. **Caching Strategy**
- ❌ No Redis or second-level cache
- ❌ Repeated database queries for frequently accessed data
- ❌ No cache invalidation strategy

**Impact:** Slower response times under load.

#### 3. **Rate Limiting & Security Hardening**
- ❌ No rate limiting on authentication endpoints
- ❌ No account lockout after failed attempts
- ❌ No brute force protection
- ❌ No request throttling

**Impact:** Vulnerable to DoS and brute force attacks.

#### 4. **API Versioning**
- ⚠️ Only v1 endpoints exist
- ⚠️ No backward compatibility strategy
- ⚠️ Breaking changes would require immediate migration

**Impact:** Difficult to evolve API without disrupting clients.

#### 5. **Documentation**
- ❌ No OpenAPI/Swagger documentation
- ❌ No API contract testing
- ❌ Minimal inline documentation

**Impact:** Harder for frontend teams to integrate.

#### 6. **Database Migrations**
- ❌ No Flyway/Liquibase for version control
- ❌ Manual schema management
- ❌ No rollback strategy

**Impact:** Risk of schema drift between environments.

#### 7. **Logging & Monitoring**
- ⚠️ Basic logging with SLF4J
- ❌ No structured logging (JSON)
- ❌ No metrics collection (Micrometer)
- ❌ No distributed tracing

**Impact:** Difficult to debug production issues.

#### 8. **Code Quality**
- ⚠️ No SonarQube integration
- ⚠️ No code coverage reports
- ⚠️ Inconsistent exception messages

---

## 🚀 Future Roadmap (API v2)

### Phase 1: Security Hardening (Q2 2026)

#### 🔐 Rate Limiting with Redis
```java
// Planned implementation
@RateLimit(limit = 5, window = "1m") // 5 attempts per minute
@PostMapping("/login")
public ResponseEntity<ApiResponse<AuthResponse>> login(...)
```

**Features:**
- Redis-based distributed rate limiting
- Configurable limits per endpoint
- Sliding window algorithm
- Custom headers: `X-RateLimit-Limit`, `X-RateLimit-Remaining`

#### 🔒 Account Lockout Mechanism
```java
// Planned implementation
@Lockout(threshold = 5, duration = "15m")
@PostMapping("/login")
public ResponseEntity<ApiResponse<AuthResponse>> login(...)
```

**Features:**
- Lock account after 5 failed attempts
- 15-minute lockout duration
- Automatic unlock with exponential backoff
- Email notification on lockout

### Phase 2: Testing Infrastructure (Q2 2026)

#### ✅ JUnit 5 + Mockito
```java
// Planned test structure
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerIntegrationTest {
    
    @MockBean
    private PostService postService;
    
    @Test
    @WithMockUser
    void shouldCreatePost_WhenValidRequest() {
        // Test implementation
    }
}
```

**Coverage Goals:**
- 80%+ unit test coverage for services
- Integration tests for all REST endpoints
- Test containers for PostgreSQL integration
- MockMvc for controller testing
- ArgumentCaptor for complex validations

#### 🧪 Test Categories
| Type | Framework | Target |
|------|-----------|--------|
| Unit Tests | JUnit 5 + Mockito | Services, Utils |
| Integration Tests | TestContainers + Spring Boot Test | Repositories, Controllers |
| Contract Tests | Spring Cloud Contract | API contracts |
| Performance Tests | Gatling | Load testing |

### Phase 3: Performance & Scalability (Q3 2026)

#### 📦 Redis Caching
```java
// Planned implementation
@Cacheable(value = "posts", key = "#id")
public PostResponse getPostById(UUID id)

@CacheEvict(value = "posts", key = "#postId")
public PostResponse updatePost(...)
```

**Cache Strategy:**
- Posts: 5-minute TTL
- Categories/Tags: 30-minute TTL
- Write-through cache for frequently accessed data
- Cache-aside pattern for reads

#### 📊 Database Optimization
- Query plan analysis with EXPLAIN ANALYZE
- Connection pooling with HikariCP tuning
- Read replicas for GET endpoints
- Database-level caching

### Phase 4: API v2 Features (Q3 2026)

#### 🌐 New Endpoints
```
GET    /api/v2/posts/{id}/comments     # Comments system
POST   /api/v2/posts/{id}/like         # Reactions
GET    /api/v2/users/{id}/stats        # User analytics
POST   /api/v2/media/upload            # Image uploads
GET    /api/v2/search                  # Full-text search
```

#### 📈 Analytics & Metrics
- Post view counts
- Reading completion tracking
- User engagement metrics
- Popular tags/categories

#### 🔔 Notifications
- Email notifications for comments
- Push notifications (WebSocket)
- Digest emails for followers

### Phase 5: DevOps & Monitoring (Q4 2026)

#### 📊 Observability Stack
- **Metrics**: Micrometer + Prometheus + Grafana
- **Logging**: ELK Stack (Elasticsearch, Logstash, Kibana)
- **Tracing**: Spring Cloud Sleuth + Zipkin
- **Health Checks**: Spring Boot Actuator

#### 🚀 CI/CD Pipeline
```yaml
# Planned GitHub Actions workflow
- Unit Tests → Integration Tests → Build → Docker → Deploy
```

---

## 📝 API Response Format

### Success Response
```json
{
  "timestamp": "2026-03-03T10:30:00",
  "status": 200,
  "message": "Retrieved all posts",
  "data": {
    "content": [...],
    "pageable": {...},
    "totalElements": 100,
    "totalPages": 5
  }
}
```

### Error Response
```json
{
  "timestamp": "2026-03-03T10:30:00",
  "code": "NOT_FOUND",
  "message": "Post not found with id: 123e4567-e89b-12d3-a456-426614174000"
}
```

---

## 🏗️ Architecture Diagram

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│   Client    │ ──────> │   Controller │ ──────> │   Service   │
│ (Frontend)  │  HTTP   │   (REST)     │  Call   │  (Business) │
└─────────────┘         └──────────────┘         └─────────────┘
                                                       │
                                                       ▼
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│  PostgreSQL │ <────── │  Repository  │ <────── │    Entity   │
│  (Database) │   JPA   │    (JPA)     │  Query  │   (Domain)  │
└─────────────┘         └──────────────┘         └─────────────┘
```

---

## 👨‍💻 Author

**Developed by:** Rem
**Project:** BulagFaust - Spring Boot Blog Platform  
**Learning Journey:** From Spring Boot beginner to  backend developer

### Key Takeaways

> "This project taught me that building a backend isn't just about making endpoints work—it's about designing for **maintainability**, **scalability**, and **security** from day one. Every mistake became a lesson, and every lesson became a better decision."

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- PostgreSQL team for the robust database system

---

<div align="center">

**Built with ❤️ using Spring Boot**

[⬆ Back to Top](#bulagfaust---modern-blog-platform-backend)

</div>
