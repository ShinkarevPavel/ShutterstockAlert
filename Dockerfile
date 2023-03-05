FROM openjdk:11
MAINTAINER nobody.com
COPY ./target/web-1.0-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app.jar"]