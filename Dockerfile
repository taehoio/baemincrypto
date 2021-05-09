FROM golang:1.16.3 as build

ARG TARGETPLATFORM
ARG BUILDPLATFORM

WORKDIR /baemincrypto-grpcgateway/bin
COPY ./grpcgateway/bin ./

RUN if [ "$BUILDPLATFORM" = "linux/amd64" ]; then mv baemincrypto-grpcgateway.linux.amd64 baemincrypto-grpcgateway ; fi
RUN if [ "$BUILDPLATFORM" = "linux/arm64" ]; then mv baemincrypto-grpcgateway.linux.arm64 baemincrypto-grpcgateway ; fi


FROM --platform=$BUILDPLATFORM openjdk:11-jre

ARG TARGETPLATFORM
ARG BUILDPLATFORM

COPY --from=build /baemincrypto-grpcgateway/bin/baemincrypto-grpcgateway /app/baemincrypto-grpcgateway

ENV ENV=""

#-- For Embedded tomcat, required /tmp
VOLUME /tmp

ADD ./app/build/libs/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$ENV -server -Xms512G -Xmx512G -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -XX:+ScavengeBeforeFullGC -Xlog:gc*:gc.log -jar app.jar & /app/baemincrypto-grpcgateway"]
