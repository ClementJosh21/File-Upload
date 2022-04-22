FROM docker-registry2dc.platform3solutions.com/adp/alpine-java:11
  
VOLUME /tmp

ARG PORT=5656

ARG DEMO_SERVICE_JAR=./build/libs/demo-0.0.1-SNAPSHOT.jar

COPY ${DEMO_SERVICE_JAR} demo-service.sh /tmp/

ENV APP_HOME /tmp/
ENV PORT ${PORT}

EXPOSE ${PORT}

RUN apk update \
    && apk add --no-cache bash \
    && chmod a+x /tmp/*.sh \
    && mv /tmp/demo-service.sh /usr/bin

ENTRYPOINT [ "demo-service.sh" ]