FROM openjdk:11
MAINTAINER nobody.com
COPY web/target/web-1.0-SNAPSHOT.jar /usr/local/lib/app.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]