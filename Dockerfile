FROM eclipse-temurin:17-jre-alpine
WORKDIR ./app
COPY target/*.jar app1.jar
EXPOSE 8080
CMD ["java", "-jar", "app1.jar"]