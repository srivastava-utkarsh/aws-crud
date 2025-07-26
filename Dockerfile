# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/aws-crud-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables for RDS
ENV RDS_HOSTNAME=database-1.cfk8eccki0j8.ap-south-1.rds.amazonaws.com
ENV RDS_PORT=3306
ENV RDS_DB_NAME=userdb
ENV RDS_USERNAME=admin
ENV RDS_PASSWORD=SuperSecretPass123

EXPOSE 9898
ENTRYPOINT ["java", "-jar", "app.jar"]
