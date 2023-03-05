FROM openjdk:11
WORKDIR /application
COPY web/target/web-1.0-SNAPSHOT.jar ./
CMD ["java","-jar", "web-1.0-SNAPSHOT.jar"]