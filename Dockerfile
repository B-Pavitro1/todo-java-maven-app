FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/*.jar todo-app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","todo-app.jar"]