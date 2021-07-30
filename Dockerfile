## STAGE 1: Build the project ##
#FROM maven:3.8.1-ibmjava-8-alpine AS build
FROM maven:3.8.1-jdk-8-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package

##STAGE 2: RUN IT!!!##
FROM openjdk:8-jdk-alpine
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "app.jar"]