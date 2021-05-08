package main

import (
	"context"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"

	"github.com/grpc-ecosystem/grpc-gateway/v2/runtime"
	"github.com/sirupsen/logrus"
	baemincryptov1 "github.com/taehoio/idl/gen/go/services/baemincrypto/v1"
	"google.golang.org/grpc"
	"google.golang.org/protobuf/encoding/protojson"
)

func serveHTTP(grpcHost string, httpPort string) (*http.Server, error) {
	ctx := context.Background()
	ctx, cancel := context.WithCancel(ctx)
	defer cancel()

	mux := runtime.NewServeMux(
		runtime.WithMarshalerOption(
			runtime.MIMEWildcard,
			&runtime.JSONPb{
				MarshalOptions: protojson.MarshalOptions{
					UseProtoNames:   true,
					EmitUnpopulated: true,
				},
				UnmarshalOptions: protojson.UnmarshalOptions{
					DiscardUnknown: true,
				},
			},
		),
	)

	options := []grpc.DialOption{
		grpc.WithInsecure(),
	}

	if err := baemincryptov1.RegisterBaemincryptoServiceHandlerFromEndpoint(
		ctx,
		mux,
		grpcHost,
		options,
	); err != nil {
		return nil, err
	}

	server := &http.Server{Addr: ":" + httpPort, Handler: mux}
	return server, server.ListenAndServe()
}

func getEnv(key, defaultValue string) (value string) {
	value = os.Getenv(key)
	if value == "" {
		if defaultValue != "" {
			value = defaultValue
		} else {
			logrus.Fatalf("missing required environment variable: %v", key)
		}
	}
	return value
}

func terminateGracefully(log *logrus.Logger, server *http.Server) {
	quit := make(chan os.Signal, 1)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	defer signal.Stop(quit)

	<-quit

	log.Info("Graceful termination signal received.")

	ctx := context.Background()

	time.Sleep(30 * time.Second)

	log.Info("Stopping baemincrypto HTTP server")
	if err := server.Shutdown(ctx); err != nil {
		log.Fatal(err)
	}

	os.Exit(0)
}

func main() {
	logrus.SetFormatter(&logrus.JSONFormatter{})
	log := logrus.StandardLogger()

	grpcPort := getEnv("SERVICE_GRPC_PORT", "8080")
	grpcHost := "127.0.0.1:" + grpcPort
	httpPort := getEnv("SERVICE_HTTP_PORT", "18080")

	log.WithField("grpcPort", grpcPort).WithField("httpPort", httpPort).Info("starting baemincrypto HTTP server")

	httpServer := &http.Server{}
	go func() {
		server, err := serveHTTP(grpcHost, httpPort)
		if err != nil {
			log.Fatal(err)
		}
		httpServer = server
	}()

	if httpServer == nil {
		return
	}

	terminateGracefully(log, httpServer)
}
