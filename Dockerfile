FROM openjdk:8-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/ivalid-1.0.0.jar
WORKDIR /app
ENTRYPOINT java -jar ivalid-1.0.0.jar
