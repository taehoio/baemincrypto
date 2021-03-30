package com.github.taehoio.baemincrypto.grpc;

import io.grpc.stub.StreamObserver;

import com.github.taehoio.idl.services.baemincrypto.v1.*;

public class BaemincryptoServiceImpl extends BaemincryptoServiceGrpc.BaemincryptoServiceImplBase {

    @Override
    public void healthCheck(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        HealthCheckResponse healthCheckResponse = HealthCheckResponse.newBuilder().build();
        responseObserver.onNext(healthCheckResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void encryptUserBaedalHeaderValue(EncryptUserBaedalHeaderValueRequest request, StreamObserver<EncryptUserBaedalHeaderValueResponse> responseObserver) {
        EncryptUserBaedalHeaderValueResponse encryptUserBaedalHeaderValueResponse = EncryptUserBaedalHeaderValueResponse.newBuilder()
                .setEncryptedText(request.getInputText())
                .build();
        responseObserver.onNext(encryptUserBaedalHeaderValueResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void decryptUserBaedalHeaderValue(DecryptUserBaedalHeaderValueRequest request, StreamObserver<DecryptUserBaedalHeaderValueResponse> responseObserver) {
        DecryptUserBaedalHeaderValueResponse decryptUserBaedalHeaderValueResponse = DecryptUserBaedalHeaderValueResponse.newBuilder()
                .setDecryptedText(request.getEncryptedText())
                .build();
        responseObserver.onNext(decryptUserBaedalHeaderValueResponse);
        responseObserver.onCompleted();
    }
}
