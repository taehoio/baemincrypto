
.PHONY: help
## prints this help message
help:
	@echo "Usage: \n"
	@sed -n 's/^##//p' ${MAKEFILE_LIST} | column -t -s ':'

.PHONY: idl
## initializes idl git submodule
idl:
	git submodule update --init

.PHONY: build-grpcgateway
## build grpcgateway
build-grpcgateway:
	cd grpcgateway && \
	CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -a -installsuffix cgo -ldflags="-w -s" -o bin/baemincrypto-grpcgateway.linux.amd64 cmd/main.go && \
	CGO_ENABLED=0 GOOS=linux GOARCH=arm64 go build -a -installsuffix cgo -ldflags="-w -s" -o bin/baemincrypto-grpcgateway.linux.arm64 cmd/main.go && \
	CGO_ENABLED=0 go build -a -installsuffix cgo -ldflags="-w -s" -o bin/build-grpcgateway cmd/main.go && \
	cd ..
