FROM openjdk:8
ADD target/webApp-0.0.1-SNAPSHOT.jar webApp-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "webApp-0.0.1-SNAPSHOT.jar"]