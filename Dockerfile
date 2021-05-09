FROM --platform=$BUILDPLATFORM openjdk:11-jre

ARG TARGETPLATFORM
ARG BUILDPLATFORM

ENV ENV=""

#-- For Embedded tomcat, required /tmp
VOLUME /tmp

ADD ./app/build/libs/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$ENV -server -Xms512G -Xmx512G -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -XX:+ScavengeBeforeFullGC -Xlog:gc*:gc.log -jar app.jar"]
