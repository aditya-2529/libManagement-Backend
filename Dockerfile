FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/Library-Management-0.0.1-SNAPSHOT.jar Library-Management.jar
ENTRYPOINT ["java","-jar","/Library-Management.jar"]