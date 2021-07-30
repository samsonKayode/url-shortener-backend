FROM openjdk:8-jdk-alpine
EXPOSE 7000
ADD target/url-shortener-backend.jar url-shortener-backend.jar
ENTRYPOINT ["java", "-jar", "/url-shortener-backend"]