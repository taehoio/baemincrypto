package com.github.taehoio.baemincrypto.grpc;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.server.Server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.taehoio.idl.services.baemincrypto.v1.BaemincryptoServiceGrpc;
import com.github.taehoio.idl.services.baemincrypto.v1.HealthCheckRequest;
import com.github.taehoio.idl.services.baemincrypto.v1.EncryptUserBaedalHeaderValueRequest;
import com.github.taehoio.idl.services.baemincrypto.v1.DecryptUserBaedalHeaderValueRequest;

public class BaemincryptoServiceTest {

    private static Server server;

    @BeforeAll
    static void beforeClass() throws Exception {
        server = Main.newServer(0, 0);
        server.start().join();
    }

    @AfterAll
    static void afterClass() {
        if (server != null) {
            server.stop().join();
        }
    }

    private static String uri() {
        return "gproto+http://127.0.0.1:" + server.activeLocalPort() + '/';
    }

    @Test
    void healthCheck() {
        final BaemincryptoServiceGrpc.BaemincryptoServiceBlockingStub baemincryptoService = Clients.newClient(uri(), BaemincryptoServiceGrpc.BaemincryptoServiceBlockingStub.class);
        assertThat(baemincryptoService.healthCheck(HealthCheckRequest.newBuilder().build())).isNotNull();
    }

    @Test
    void encryptUserBaedalHeaderValue() {
        final BaemincryptoServiceGrpc.BaemincryptoServiceBlockingStub baemincryptoService = Clients.newClient(uri(), BaemincryptoServiceGrpc.BaemincryptoServiceBlockingStub.class);
        String testInputText = "abcd1234";
        EncryptUserBaedalHeaderValueRequest encryptUserBaedalHeaderValueRequest = EncryptUserBaedalHeaderValueRequest.newBuilder()
                .setInputText(testInputText)
                .build();

        String expectedEncryptedText = "abcd1234";
        assertThat(baemincryptoService.encryptUserBaedalHeaderValue(encryptUserBaedalHeaderValueRequest).getEncryptedText())
                .isEqualTo(expectedEncryptedText);
    }

    @Test
    void decryptUserBaedalHeaderValue() {
        final BaemincryptoServiceGrpc.BaemincryptoServiceBlockingStub baemincryptoService = Clients.newClient(uri(), BaemincryptoServiceGrpc.BaemincryptoServiceBlockingStub.class);
        String testEncryptedText = "abcd1234";
        DecryptUserBaedalHeaderValueRequest decryptUserBaedalHeaderValueRequest = DecryptUserBaedalHeaderValueRequest.newBuilder()
                .setEncryptedText(testEncryptedText)
                .build();

        String expectedDecryptedText = "abcd1234";
        assertThat(baemincryptoService.decryptUserBaedalHeaderValue(decryptUserBaedalHeaderValueRequest).getDecryptedText())
                .isEqualTo(expectedDecryptedText);
    }
}
