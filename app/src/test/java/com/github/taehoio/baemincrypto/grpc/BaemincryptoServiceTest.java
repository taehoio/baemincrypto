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

class BaemincryptoServiceTest {

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
        String testInputText = "iph1_10.24.0|14.5|iPhone13,1|Guest|126.92202748|37.51993082|OPUD97459151-6F7E-45B3-A022-040457779F7E|000000000000||서울|영등포구|여의도동 38-1||45005|";
        EncryptUserBaedalHeaderValueRequest encryptUserBaedalHeaderValueRequest = EncryptUserBaedalHeaderValueRequest.newBuilder()
                .setInputText(testInputText)
                .build();

        String expectedEncryptedText = "smt33Pd2sEgsqmtBFaFpuXxPANzldD88IpHzpR27nLx3DjF8zhovIs0ilQzVIumZQM6hbmtzRJ9r8qU/FqOXiwnBr0hFQaFA3EiWRdlVBGmcqoHChwfHiVivvTjPxqrYKT+1FMG18ZA2a15Dj8OmwEHDbmJ+zt3v/jFx3d496WfkW2HuAGg8NJNE38Xy/G+nC8vr2ixEa3nNNbk89poONE/zpq0Bf9/4qxl942BfkDE=";
        assertThat(baemincryptoService.encryptUserBaedalHeaderValue(encryptUserBaedalHeaderValueRequest).getEncryptedText())
                .isEqualTo(expectedEncryptedText);
    }

    @Test
    void decryptUserBaedalHeaderValue() {
        final BaemincryptoServiceGrpc.BaemincryptoServiceBlockingStub baemincryptoService = Clients.newClient(uri(), BaemincryptoServiceGrpc.BaemincryptoServiceBlockingStub.class);
        String testEncryptedText = "smt33Pd2sEgsqmtBFaFpuXxPANzldD88IpHzpR27nLx3DjF8zhovIs0ilQzVIumZQM6hbmtzRJ9r8qU/FqOXiwnBr0hFQaFA3EiWRdlVBGmcqoHChwfHiVivvTjPxqrYKT+1FMG18ZA2a15Dj8OmwEHDbmJ+zt3v/jFx3d496WfkW2HuAGg8NJNE38Xy/G+nC8vr2ixEa3nNNbk89poONE/zpq0Bf9/4qxl942BfkDE=";
        DecryptUserBaedalHeaderValueRequest decryptUserBaedalHeaderValueRequest = DecryptUserBaedalHeaderValueRequest.newBuilder()
                .setEncryptedText(testEncryptedText)
                .build();

        String expectedDecryptedText = "iph1_10.24.0|14.5|iPhone13,1|Guest|126.92202748|37.51993082|OPUD97459151-6F7E-45B3-A022-040457779F7E|000000000000||서울|영등포구|여의도동 38-1||45005|";
        assertThat(baemincryptoService.decryptUserBaedalHeaderValue(decryptUserBaedalHeaderValueRequest).getDecryptedText())
                .isEqualTo(expectedDecryptedText);
    }
}
