FROM openjdk:11
WORKDIR /application
COPY target/web-1.0-SNAPSHOT.jar ./
CMD ["java","-jar", "web-1.0-SNAPSHOT.jar"]