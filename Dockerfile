FROM docker-registry2dc.platform3solutions.com/adp/alpine-java:11
  
VOLUME /tmp

ARG PORT=5656

ARG BASE_SERVICE_JAR=./build/libs/lwa-base-service-0.0.1.jar

COPY ${BASE_SERVICE_JAR} start-base-service.sh /tmp/

ENV APP_HOME /tmp/
ENV PORT ${PORT}

EXPOSE ${PORT}

RUN apk update \
    && apk add --no-cache bash \
    && chmod a+x /tmp/*.sh \
    && mv /tmp/start-base-service.sh /usr/bin

ENTRYPOINT [ "start-base-service.sh" ]