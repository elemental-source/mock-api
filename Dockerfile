FROM openjdk:8-jre-alpine3.8
MAINTAINER "elo7-maesters"

COPY build/libs/mock-api*.jar app.jar

VOLUME /config
VOLUME /data
VOLUME /data-cache

WORKDIR /

EXPOSE 5000
EXPOSE 9090

ENV APP_PARAMS "--spring.config.location=file:/config/application.yml"

ENTRYPOINT ["java", \
            "-jar", \
            "/app.jar", \
            "$APP_PARAMS"]
