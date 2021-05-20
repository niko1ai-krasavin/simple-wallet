#
# It copies the source code and runs JUnit tests
#
FROM maven:3.6.0-jdk-11-slim AS testing
COPY pom.xml /app/
COPY src /app/src
RUN mvn -f /app/pom.xml test

#
# It builds the source code to .jar file
#
FROM maven:3.6.0-jdk-11-slim AS building
WORKDIR /app
COPY --from=testing /app .
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

#
# It starts the simple-wallet.jar file
#
FROM adoptopenjdk/openjdk11 AS running
RUN mkdir -p /opt
COPY --from=building /app/target/*.jar /opt/simple-wallet.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/simple-wallet.jar"]