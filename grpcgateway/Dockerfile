FROM golang:1.16.3 as build

ARG TARGETPLATFORM
ARG BUILDPLATFORM

WORKDIR /baemincrypto-grpcwateway/bin
COPY ./bin ./

RUN if [ "$BUILDPLATFORM" = "linux/amd64" ]; then mv baemincrypto-grpcwateway.linux.amd64 baemincrypto-grpcwateway ; fi
RUN if [ "$BUILDPLATFORM" = "linux/arm64" ]; then mv baemincrypto-grpcwateway.linux.arm64 baemincrypto-grpcwateway ; fi


FROM --platform=$BUILDPLATFORM gcr.io/distroless/base

ARG TARGETPLATFORM
ARG BUILDPLATFORM

COPY --from=build /baemincrypto-grpcwateway/bin/baemincrypto-grpcwateway /app/baemincrypto-grpcwateway
ENTRYPOINT ["app/baemincrypto-grpcwateway"]
