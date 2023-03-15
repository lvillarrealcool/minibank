FROM openjdk:8-jdk-slim
COPY "./target/minibank-0.0.1-SNAPSHOT.jar" "minibank.jar"
EXPOSE 8000
ENTRYPOINT ["java","-jar","minibank.jar"]