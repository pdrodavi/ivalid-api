FROM adoptopenjdk/openjdk11:jre-nightly
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/ivalid-1.0.0.jar
WORKDIR /app
ENV SHOW_DETAILS=
ENV METRICS=
ENV PROMETHEUS=
ENV APP_NAME=
ENV PORT=
EXPOSE 7000
ENTRYPOINT ["java", "-jar", "ivalid-1.0.0.jar"]