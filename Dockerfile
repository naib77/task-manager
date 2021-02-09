# Step : Package image
FROM openjdk:11-jre-slim-buster
EXPOSE 8080
COPY prebuildjar/taskmanager-0.0.1-SNAPSHOT.jar /app.jar

ENV DB_HOST=localhost
ENV DB_NAME=hdip
ENV DB_USER=root
ENV DB_PASSWORD=root

ENTRYPOINT ["java","-XX:+UseG1GC", "-jar", "app.jar"]

