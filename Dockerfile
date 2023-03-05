FROM openjdk:11
MAINTAINER nobody.com
COPY web/target/web-1.0-SNAPSHOT.jar home/user/.local/tmp/app.jar
ENTRYPOINT ["java","-jar","home/user/.local/tmp/app.jar"]