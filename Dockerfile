#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY pom.xml /app/
COPY src /app/src
RUN mvn -f /app/pom.xml clean package

#
# Run stage
#
FROM adoptopenjdk/openjdk11
RUN mkdir -p /opt
COPY --from=build /app/target/*.jar /opt/simple-wallet.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/simple-wallet.jar"]