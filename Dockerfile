FROM openjdk:11
MAINTAINER nobody.com
COPY --from=builder web/target/web-1.0-SNAPSHOT.jar web-1.0-SNAPSHOT.jar
WORKDIR /tmp
ENTRYPOINT ["java","-jar", "web-1.0-SNAPSHOT.jar"]