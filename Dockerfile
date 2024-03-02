#
# Build stage
#
#FROM maven:3.8.3-openjdk-17 AS build
#COPY . .
#RUN mvn clean install

#
# Package stage
#
#FROM eclipse-temurin:17-jdk
#COPY --from=build /target/JavaSample-0.0.1-SNAPSHORT.jar demo.jar
# ENV PORT=8080
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","demo.jar"]


#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

FROM eclipse-temurin:17-alpine
COPY --from=build target/JavaSample-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT [“java”,“-jar”,“demo.jar”]