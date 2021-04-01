package com.github.taehoio.baemincrypto.grpc;

import com.github.taehoio.baemincrypto.cipher.Cipher;
import com.github.taehoio.idl.services.baemincrypto.v1.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BaemincryptoServiceImpl extends BaemincryptoServiceGrpc.BaemincryptoServiceImplBase {

    private static final String AES_KEY = "sbfighting";

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
            String encryptedText = Cipher.encrypt(request.getInputText(), AES_KEY);
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
            String decryptedText = Cipher.decrypt(request.getEncryptedText(), AES_KEY);
            DecryptUserBaedalHeaderValueResponse response =
                    DecryptUserBaedalHeaderValueResponse.newBuilder().setDecryptedText(decryptedText).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
