ARG DOCKER_UPSTREAM_REGISTRY
ARG DOCKER_UPSTREAM_TAG=latest

FROM ${DOCKER_UPSTREAM_REGISTRY}confluentinc/cp-base-new:${DOCKER_UPSTREAM_TAG}

USER root
RUN mkdir -p /opt/lib
COPY target/scala-2.11/zip-coding-challenge-assembly-0.1.0-SNAPSHOT.jar /opt/lib/zip-coding-challenge-assembly-0.1.0-SNAPSHOT.jar
