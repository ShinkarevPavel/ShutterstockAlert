FROM openjdk:11
MAINTAINER nobody.com
COPY target/-jar web/target/web-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app.jar"]