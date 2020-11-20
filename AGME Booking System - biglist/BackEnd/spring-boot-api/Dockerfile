FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Giacomo Pirrone
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -DskipTests
FROM openjdk:8-jre-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=MAVEN_BUILD /build/target/spring-boot-api-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-jar","spring-boot-api-0.0.1-SNAPSHOT.jar"]

#Build container: docker build -t com/scrumoftheearth/code/backend .
#Run Container: docker run -p 8080:8080 com/scrumoftheearth/code/backend 