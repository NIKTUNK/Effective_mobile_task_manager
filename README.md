# Task Management System

## Описание

Task Management System - это веб-приложение для управления задачами. Пользователи могут создавать, назначать и отслеживать статус задач. Приложение разработано с использованием Spring Boot и PostgreSQL.

## Функциональность

- Регистрация и аутентификация пользователей.
- Роли пользователей (администратор, пользователь).
- Создание, редактирование и удаление задач.
- Назначение задач пользователям.
- Отслеживание статуса задач (OPEN, IN_PROGRESS, COMPLETED).
- Безопасность через JWT (JSON Web Token).

## Технологии

- Java 11+
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- JWT (JSON Web Token)
- Lombok (для уменьшения шаблонного кода)

## Требования

- Java Development Kit (JDK) 11 или выше.
- Apache Maven 3.6.0 или выше.
- PostgreSQL 12 или выше.

## Установка и запуск

### 1. Клонирование репозитория

```bash
git clone https://github.com/yourusername/task-management.git
cd task-management

Сборка и запуск приложения
Соберите проект с помощью Maven:
bash

mvn clean install
Запустите приложение:
bash

mvn spring-boot:run
Приложение будет доступно по адресу http://localhost:8080.

Настройка базы данных
Создайте базу данных в PostgreSQL:
sql
CREATE DATABASE task_management;
CREATE USER task_user WITH ENCRYPTED PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE task_management TO task_user;
Настройте подключение к базе данных в src/main/resources/application.yml:
yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/task_management
    username: task_user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

Сборка и запуск приложения
Соберите проект с помощью Maven:
mvn clean install

Запустите приложение:
mvn spring-boot:run
Приложение будет доступно по адресу http://localhost:8080.

Запуск тестов
Для выполнения всех тестов используйте команду:
mvn test

Использование
API
Регистрация пользователя: POST /api/auth/register
Аутентификация пользователя: POST /api/auth/login
Создание задачи: POST /api/tasks
Получение всех задач: GET /api/tasks
Обновление задачи: PUT /api/tasks/{id}
Удаление задачи: DELETE /api/tasks/{id}

Примеры запросов

Регистрация:
POST /api/auth/register
{
  "username": "user1",
  "password": "password",
  "email": "user1@example.com"
}
Аутентификация:
POST /api/auth/login
{
  "username": "user1",
  "password": "password"
}
Создание задачи:
POST /api/tasks
{
  "title": "New Task",
  "description": "Task description",
  "status": "OPEN",
  "assigneeId": 2
}


### Пояснения:
- В разделе "Установка и запуск" указаны команды для настройки, сборки и запуска приложения.
- В разделе "Использование" описаны основные API методы с примерами запросов.
