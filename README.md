# 🏥 Clinic Management System

**Clinic Management System** — это бэкенд-приложение для автоматизации работы медицинской клиники. Система предоставляет REST API для трёх типов пользователей с разными уровнями доступа.

### Основные возможности

- 👨‍⚕️ **Пациенты** — регистрация, запись к врачу, просмотр истории посещений
- 👨‍🔬 **Врачи** — управление расписанием, просмотр и обновление статусов записей
- 🔐 **Администраторы** — полное управление пользователями, врачами, пациентами и записями

### Технологический стек

| Технология | Версия | Назначение |
|------------|--------|------------|
| Java | 21 | Основной язык |
| Spring Boot | 3.5.10 | Фреймворк |
| Spring Security | 6.x | Аутентификация и авторизация |
| Spring Data JPA | 6.x | ORM и работа с БД |
| PostgreSQL | 17 | Реляционная база данных |
| Liquibase | 4.24 | Миграции базы данных |
| JWT | 0.11.5 | JSON Web Token для аутентификации |
| OpenAPI (Swagger) | 2.8.6 | Документация API |
| Prometheus | - | Сбор метрик |
| Maven | 3.x | Сборка проекта |

### Структура проекта
```
src/
├── main/
│ ├── java/com/example/
│ │ ├── ClinicManagementApplication.java
│ │ ├── config/
│ │ │ ├── SecurityConfig.java
│ │ │ ├── JwtAuthenticationFilter.java
│ │ │ ├── OpenApiConfig.java
│ │ │ └── WebConfig.java
│ │ ├── controller/
│ │ │ ├── admin/
│ │ │ ├── doctor/
│ │ │ ├── patient/
│ │ │ └── stats/
│ │ ├── exception/
│ │ ├── model/
│ │ │ ├── entity/
│ │ │ ├── dto/
│ │ │ └── enums/
│ │ ├── repository/
│ │ └── service/
│ └── resources/
│ ├── application.properties
│ └── db/changelog/
└── test/
└── java/
```

## 🚀 Запуск проекта

### Требования
- Java 21
- PostgreSQL 17
- Maven 3.8+

### Настройка базы данных

1. Создайте базу данных PostgreSQL:

```sql
CREATE DATABASE clinic_manager;
```
2. Настройте подключение в файле application.properties или через переменные окружения:
```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/clinic_manager
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password

spring.datasource.url=jdbc:postgresql://localhost:5432/clinic_manager
spring.datasource.username=postgres
spring.datasource.password=your_password
```

## Запуск приложения
```
# Клонирование репозитория
git clone https://github.com/your-repo/clinic-management.git
cd clinic-management

# Сборка проекта
mvn clean package

# Запуск приложения
java -jar target/demo-0.0.1-SNAPSHOT.jar

# Или через Maven
mvn spring-boot:run
```
Приложение запустится на порту 8081.


# Отчёт по тестированию Clinic Management Application

## 1. Общая информация

Проект представляет собой Spring Boot приложение для управления клиникой. В рамках финального задания разработаны автоматизированные тесты.

| Инструмент | Назначение |
|------------|------------|
| JUnit 5 | Платформа тестирования |
| Mockito | Мокирование зависимостей |
| MockMvc | Тестирование REST контроллеров |
| JaCoCo | Анализ покрытия кода |

## 2. Количество тестов

| Категория | Количество |
|-----------|------------|
| Unit-тесты сервисов | 55 |
| Параметризованные тесты | 13 |
| Тесты исключений | 6 |
| REST контроллеры | 4 |
| Тесты безопасности | 8 |
| **ИТОГО** | **86** |

## 3. Покрытие кода (JaCoCo)

### 3.1 Общий процент покрытия

| Показатель | Значение |
|------------|----------|
| Line coverage | 72% |
| Branch coverage | 68% |

### 3.2 Покрытие сервисного слоя (цель ≥70%)

| Сервис | Покрытие |
|--------|----------|
| SpecializationService | 100% |
| HomeStatsService | 100% |
| StatusService | 95% |
| ProfileStatsService | 95% |
| UserService | 90% |
| AppointmentService | 85% |
| DoctorService | 80% |
| PatientService | 80% |
| **Среднее** | **~88%** |

### 3.3 Анализ покрытия

**Лучше всего покрыты:**
- SpecializationService, HomeStatsService — 100%
- StatusService, ProfileStatsService — 95%

**Хуже покрыты:**
- DTO (25-50%) — тестирование геттеров/сеттеров не требуется
- Контроллеры (32%) — требуют интеграционного тестирования
- Entity (25%) — JPA сущности не требуют unit-тестов

## 4. Типы тестов

### 4.1 Unit-тесты сервисов (55 тестов)

**Позитивные сценарии:**
- `createAppointment_shouldSaveAndReturnResponse`
- `updateStatus_shouldChangeAppointmentStatus`
- `save_shouldCreateUserSuccessfully`

**Негативные сценарии (assertThrows):**
- `createAppointment_shouldThrowResourceNotFoundException_whenPatientNotFound`
- `save_shouldThrowValidationException_whenUsernameAlreadyExists`

**Mockito verify:**
- `createAppointment_shouldCallRepositorySaveOnlyOnce`
- `deleteById_shouldCallUserRepositoryDelete`

**ArgumentCaptor:**
- `save_shouldEncodePasswordBeforeSaving`
- `createAppointment_shouldPassCorrectDataToRepository`

### 4.2 Параметризованные тесты (13 тестов)

| Аннотация | Что тестируется |
|-----------|-----------------|
| `@EnumSource` | Все статусы Appointment |
| `@ValueSource` | Различные ID пациентов и врачей |
| `@CsvSource` | Комбинации данных для Appointment |

### 4.3 Тесты исключений (6 тестов)

| Исключение | Сценарий |
|------------|----------|
| `ResourceNotFoundException` | Запрос несуществующей записи |
| `AccessDeniedException` | Врач обновляет чужую запись |
| `ValidationException` | Регистрация с существующим username/email |
| `BadRequestException` | Передача некорректных данных |

### 4.4 REST тесты контроллеров (4 теста)

| Тест | Статус | Сценарий |
|------|--------|----------|
| `createAppointment_shouldReturn201Created` | 201 | Успешное создание |
| `createAppointment_shouldReturn400_whenDateIsPast` | 400 | Валидация даты |
| `createAppointment_shouldReturn400_whenSymptomsTooShort` | 400 | Валидация симптомов |
| `getAllDoctors_shouldReturnListWithCorrectFields` | 200 | Проверка JSON |

### 4.5 Тесты безопасности (8 тестов)

| Тест | Роль | Эндпоинт | Статус |
|------|------|----------|--------|
| `patientShouldAccessPatientEndpoints` | PATIENT | `/api/patient/...` | 200 |
| `doctorShouldAccessDoctorEndpoints` | DOCTOR | `/api/doctor/...` | 200 |
| `patientShouldNotAccessAdminEndpoints` | PATIENT | `/api/admin/...` | 403 |
| `doctorShouldNotAccessAdminEndpoints` | DOCTOR | `/api/admin/...` | 403 |
| `unauthenticatedUserShouldNotAccess*` | нет | любые | 403 |

## 5. Структура тестов
```
src/test/java/
├── controller/
│ ├── AppointmentPatientRoleControllerTest
│ └── SecurityTests
├── service/
│ ├── appointment/
│ │ ├── AppointmentServiceTest
│ │ └── ParameterizedAppointmentServiceTest
│ ├── DoctorServiceTest
│ ├── PatientServiceTest
│ ├── UserServiceTest
│ ├── StatusServiceTest
│ ├── SpecializationServiceTest
│ ├── HomeStatsServiceTest
│ └── ProfileStatsServiceTest
└── ExceptionTests
```

## 6. Запуск тестов

```bash
# Запуск всех тестов
mvn clean test

# Генерация отчёта JaCoCo
mvn jacoco:report
```
Отчёт: ``` target/site/jacoco/index.html ```

## 7. Настройка JaCoCo (pom.xml)
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
