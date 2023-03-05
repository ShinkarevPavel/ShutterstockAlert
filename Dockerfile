FROM openjdk:11
MAINTAINER nobody.com
COPY --from=target web/target/web-1.0-SNAPSHOT.jar web-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "web-1.0-SNAPSHOT.jar"]