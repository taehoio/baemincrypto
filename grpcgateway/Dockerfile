FROM golang:1.16.2 as build

WORKDIR /baemincrypto-grpcgateway
COPY . .

WORKDIR /baemincrypto-grpcgateway/cmd
ARG GH_ACCESS_TOKEN
RUN git config --global url."https://${GH_ACCESS_TOKEN}@github.com/".insteadOf "https://github.com/"
RUN CGO_ENABLED=0 go build -a -installsuffix cgo -ldflags="-w -s" -o /go/bin/baemincrypto-grpcgateway

WORKDIR /baemincrypto-grpcgateway
ARG test
RUN if [ "$test" = "true" ]; then make lint ; fi
RUN if [ "$test" = "true" ]; then make test ; fi


FROM gcr.io/distroless/base

COPY --from=build /go/bin /app
ENTRYPOINT ["app/baemincrypto-grpcgateway"]
