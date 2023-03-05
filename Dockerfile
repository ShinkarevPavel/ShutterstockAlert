FROM openjdk:11
MAINTAINER nobody.com
COPY web/target/web-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]