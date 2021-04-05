FROM golang:1.16.3 as build

ARG TARGETPLATFORM
ARG BUILDPLATFORM

WORKDIR /baemincrypto-grpcgateway/bin
COPY ./bin ./

RUN if [ "$BUILDPLATFORM" = "linux/amd64" ]; then mv baemincrypto-grpcgateway.linux.amd64 baemincrypto-grpcgateway ; fi
RUN if [ "$BUILDPLATFORM" = "linux/arm64" ]; then mv baemincrypto-grpcgateway.linux.arm64 baemincrypto-grpcgateway ; fi


FROM --platform=$BUILDPLATFORM gcr.io/distroless/base

ARG TARGETPLATFORM
ARG BUILDPLATFORM

COPY --from=build /baemincrypto-grpcgateway/bin/baemincrypto-grpcgateway /app/baemincrypto-grpcgateway
ENTRYPOINT ["app/baemincrypto-grpcgateway"]
