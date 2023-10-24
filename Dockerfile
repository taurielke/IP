FROM openjdk:17-oracle
EXPOSE 7070
ADD build/libs/sbapp-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]