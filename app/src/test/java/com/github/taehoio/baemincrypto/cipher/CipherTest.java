package com.github.taehoio.baemincrypto.cipher;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CipherTest {
    @Test
    void encrypt() throws Exception {
        String inputText = "iph1_10.24.0|14.5|iPhone13,1|Guest|126.92202748|37.51993082|OPUD97459151-6F7E-45B3-A022-040457779F7E|000000000000||서울|영등포구|여의도동 38-1||45005|";
        String key = "sbfighting";

        String encryptedText = Cipher.encrypt(inputText, key);
        String expectedEncryptedText = "smt33Pd2sEgsqmtBFaFpuXxPANzldD88IpHzpR27nLx3DjF8zhovIs0ilQzVIumZQM6hbmtzRJ9r8qU/FqOXiwnBr0hFQaFA3EiWRdlVBGmcqoHChwfHiVivvTjPxqrYKT+1FMG18ZA2a15Dj8OmwEHDbmJ+zt3v/jFx3d496WfkW2HuAGg8NJNE38Xy/G+nC8vr2ixEa3nNNbk89poONE/zpq0Bf9/4qxl942BfkDE=";
        assertThat(encryptedText)
                .isEqualTo(expectedEncryptedText);
    }

    @Test
    void decrypt() throws Exception {
        String encryptedText = "smt33Pd2sEgsqmtBFaFpuXxPANzldD88IpHzpR27nLx3DjF8zhovIs0ilQzVIumZQM6hbmtzRJ9r8qU/FqOXiwnBr0hFQaFA3EiWRdlVBGmcqoHChwfHiVivvTjPxqrYKT+1FMG18ZA2a15Dj8OmwEHDbmJ+zt3v/jFx3d496WfkW2HuAGg8NJNE38Xy/G+nC8vr2ixEa3nNNbk89poONE/zpq0Bf9/4qxl942BfkDE=";
        String key = "sbfighting";

        String decryptedText = Cipher.decrypt(encryptedText, key);
        String expectedDecryptedText = "iph1_10.24.0|14.5|iPhone13,1|Guest|126.92202748|37.51993082|OPUD97459151-6F7E-45B3-A022-040457779F7E|000000000000||서울|영등포구|여의도동 38-1||45005|";
        assertThat(decryptedText)
                .isEqualTo(expectedDecryptedText);
    }
}
