FROM maven:3.8-jdk-11
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests
CMD ["java", "-jar", "target/web-1.0-SNAPSHOT.jar"]