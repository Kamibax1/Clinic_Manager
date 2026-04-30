# Многоэтапная сборка для оптимизации размера
# Используем Maven с Eclipse Temurin JDK 24
FROM maven:3.9-eclipse-temurin-24 AS build

WORKDIR /app

# Копируем только pom.xml сначала (для кэширования зависимостей)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходный код
COPY src ./src

# Собираем приложение, пропуская тесты
RUN mvn clean package -DskipTests

# Финальный образ - только JRE и JAR
# Используем Eclipse Temurin JRE 24
FROM eclipse-temurin:24-jre

WORKDIR /app

# Копируем JAR файл из этапа сборки
COPY --from=build /app/target/*.jar app.jar

# Открываем порт 8081
EXPOSE 8081

# Healthcheck для проверки работоспособности
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8081/api/auth/login || exit 1

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]