# Build Stage
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/inventory-system-1.0.0.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]
