package com.github.taehoio.baemincrypto.grpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import com.github.taehoio.baemincrypto.cipher.Cipher;
import com.github.taehoio.idl.services.baemincrypto.v1.*;

public class BaemincryptoServiceImpl extends BaemincryptoServiceGrpc.BaemincryptoServiceImplBase {

    private final String aesKey = "sbfighting";

    @Override
    public void healthCheck(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        HealthCheckResponse healthCheckResponse = HealthCheckResponse.newBuilder().build();
        responseObserver.onNext(healthCheckResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void encryptUserBaedalHeaderValue(EncryptUserBaedalHeaderValueRequest request, StreamObserver<EncryptUserBaedalHeaderValueResponse> responseObserver) {
        try {
            String encryptedText = Cipher.encrypt(request.getInputText(), aesKey);
            EncryptUserBaedalHeaderValueResponse encryptUserBaedalHeaderValueResponse = EncryptUserBaedalHeaderValueResponse.newBuilder()
                    .setEncryptedText(encryptedText)
                    .build();
            responseObserver.onNext(encryptUserBaedalHeaderValueResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withCause(e)
                    .withDescription(e.getMessage())
                    .asException()
            );
        }
    }

    @Override
    public void decryptUserBaedalHeaderValue(DecryptUserBaedalHeaderValueRequest request, StreamObserver<DecryptUserBaedalHeaderValueResponse> responseObserver) {
        try {
            String decryptedText = Cipher.decrypt(request.getEncryptedText(), aesKey);
            DecryptUserBaedalHeaderValueResponse decryptUserBaedalHeaderValueResponse = DecryptUserBaedalHeaderValueResponse.newBuilder()
                    .setDecryptedText(decryptedText)
                    .build();
            responseObserver.onNext(decryptUserBaedalHeaderValueResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
