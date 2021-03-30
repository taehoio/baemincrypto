package com.github.taehoio.baemincrypto.grpc;

import com.github.taehoio.idl.services.baemincrypto.v1.HealthCheckRequest;
import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.HttpServiceWithRoutes;
import com.linecorp.armeria.server.grpc.GrpcService;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.server.Server;

public final class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final Server server = newServer(8080, 8443);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop().join();
            logger.info("Server has been stopped.");
        }));

        server.start().join();

        logger.info("Server has been started. Serving DocService at http://127.0.0.1:{}/docs", server.activeLocalPort());
    }

    static Server newServer(int httpPort, int httpsPort) throws Exception {
        final HealthCheckRequest healthCheckRequest = HealthCheckRequest.newBuilder().build();
        final HttpServiceWithRoutes grpcService =
                GrpcService.builder()
                           .addService(new BaemincryptoServiceImpl())
                           .addService(ProtoReflectionService.newInstance())
                           .supportedSerializationFormats(GrpcSerializationFormats.values())
                           .enableUnframedRequests(true)
                           .build();
        return Server.builder()
                     .http(httpPort)
                     .https(httpsPort)
                     .tlsSelfSigned()
                     .service(grpcService)
                     .service("prefix:/prefix", grpcService)
                     .build();
    }
}