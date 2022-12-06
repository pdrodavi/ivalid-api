FROM openjdk:12-alpine
MAINTAINER github/pdrodavi
RUN mkdir app
COPY ./target/ivalid-1.0.0.jar /app/ivalid-1.0.0.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "ivalid-1.0.0.jar"]
EXPOSE 8080

#RUN mkdir app
#ARG JAR_FILE
#ADD /target/${JAR_FILE} /app/ivalid.jar
#WORKDIR /app
#ENTRYPOINT java -jar ivalid.jar
