FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/*-jar-with-dependencies.jar todo-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "todo-app.jar"]