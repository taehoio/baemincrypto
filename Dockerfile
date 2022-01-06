FROM ghcr.io/graalvm/graalvm-ce:java11-21.1.0 AS graalvm
RUN gu install native-image
WORKDIR /home/app
COPY build/layers/libs /home/app/libs
COPY build/layers/resources /home/app/resources
COPY build/layers/application.jar /home/app/application.jar
RUN native-image -H:Class=com.github.taehoio.baemincrypto.Application -H:Name=application --no-fallback -cp /home/app/libs/*.jar:/home/app/resources:/home/app/application.jar
FROM frolvlad/alpine-glibc:alpine-3.12
RUN apk update && apk add libstdc++
COPY --from=graalvm /home/app/application /app/application
ENTRYPOINT ["/app/application"]
