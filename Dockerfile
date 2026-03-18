# ---------- BUILD ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY pom.xml .
COPY discovery-service/pom.xml discovery-service/
COPY auth-service/pom.xml auth-service/
COPY account-service/pom.xml account-service/
COPY campaign-service/pom.xml campaign-service/
COPY api-gateway/pom.xml api-gateway/

RUN mvn -B -q -e -DskipTests dependency:go-offline

COPY . .

ARG SERVICE

RUN mvn -pl ${SERVICE} -am clean package -DskipTests


# ---------- RUNTIME ----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

ARG SERVICE

COPY --from=build /build/${SERVICE}/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","/app/app.jar"]