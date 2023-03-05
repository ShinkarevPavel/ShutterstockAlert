FROM openjdk:11
MAINTAINER nobody.com
COPY web/target/web-1.0-SNAPSHOT.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java","-jar"]