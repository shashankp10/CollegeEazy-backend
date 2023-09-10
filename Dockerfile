FROM java:17

EXPOSE 8080

ADD target/docker-Project-2.jar Project-2.jar

ENTRYPOINT ["java", "-jar", "app.jar"]