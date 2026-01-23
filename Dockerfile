FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar

ENV JAVA_OPTS="-Xms512m -Xmx1024m"
ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -q --spider http://localhost:${SERVER_PORT}/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]