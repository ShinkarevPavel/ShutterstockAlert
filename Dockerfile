FROM openjdk:11
MAINTAINER nobody.com
COPY web/target/web-1.0-SNAPSHOT.jar web/app.jar
ENTRYPOINT ["java","-jar","web/app.jar"]