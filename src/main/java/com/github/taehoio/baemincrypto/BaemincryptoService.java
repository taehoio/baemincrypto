package com.github.taehoio.baemincrypto;

import com.github.taehoio.baemincrypto.cipher.Cipher;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Singleton;

@Singleton
public class BaemincryptoService {

    private static final String AES_KEY = "sbfighting";

    public String encryptUserBaedalHeaderValue(String inputText)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                    InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        return Cipher.encrypt(inputText, AES_KEY);
    }

    public String decryptUserBaedalHeaderValue(String encryptedText)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                    InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        return Cipher.decrypt(encryptedText, AES_KEY);
    }
}
