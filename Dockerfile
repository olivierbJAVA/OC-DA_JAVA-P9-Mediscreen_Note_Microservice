FROM gradle:jdk8 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar

FROM openjdk:8-jre-slim
COPY --from=builder /home/gradle/src/build/libs/Mediscreen_Note-1.0.0.jar /app/Mediscreen_Note-1.0.0.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/Mediscreen_Note-1.0.0.jar"]
