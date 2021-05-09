package com.github.taehoio.baemincrypto;

import com.github.taehoio.idl.services.baemincrypto.v1.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import javax.inject.Singleton;

@Singleton
public class BaemincryptoEndpoint extends BaemincryptoServiceGrpc.BaemincryptoServiceImplBase {

    private final BaemincryptoService baemincryptoService;

    public BaemincryptoEndpoint(BaemincryptoService baemincryptoService) {
        this.baemincryptoService = baemincryptoService;
    }

    @Override
    public void healthCheck(
            HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        HealthCheckResponse response = HealthCheckResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void encryptUserBaedalHeaderValue(
            EncryptUserBaedalHeaderValueRequest request,
            StreamObserver<EncryptUserBaedalHeaderValueResponse> responseObserver) {
        try {
            final String encryptedText =
                    baemincryptoService.encryptUserBaedalHeaderValue(request.getInputText());
            EncryptUserBaedalHeaderValueResponse response =
                    EncryptUserBaedalHeaderValueResponse.newBuilder().setEncryptedText(encryptedText).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL.withCause(e).withDescription(e.getMessage()).asException());
        }
    }

    @Override
    public void decryptUserBaedalHeaderValue(
            DecryptUserBaedalHeaderValueRequest request,
            StreamObserver<DecryptUserBaedalHeaderValueResponse> responseObserver) {
        try {
            final String decryptedText =
                    baemincryptoService.decryptUserBaedalHeaderValue(request.getEncryptedText());
            DecryptUserBaedalHeaderValueResponse response =
                    DecryptUserBaedalHeaderValueResponse.newBuilder().setDecryptedText(decryptedText).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL.withCause(e).withDescription(e.getMessage()).asException());
        }
    }
}
