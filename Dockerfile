FROM openjdk:17-jdk-alpine

ENV APP_HOME=/app
WORKDIR $APP_HOME

COPY build/libs/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]