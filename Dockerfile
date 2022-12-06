FROM openjdk:8-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/ivalid.jar
WORKDIR /app
ENTRYPOINT java -jar ivalid.jar
