package com.github.taehoio.baemincrypto.cipher;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cipher {

    private Cipher() {}

    public static String encrypt(String inputText, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                    InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedBytes;
        byte[] fixedLenKeyBytes = new byte[32];
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 33) {
            System.arraycopy(keyBytes, 0, fixedLenKeyBytes, 0, keyBytes.length);
        } else {
            System.arraycopy(keyBytes, 0, fixedLenKeyBytes, 0, 32);
        }
        byte[] bArr3 = new byte[16];

        SecretKeySpec secretKeySpec = new SecretKeySpec(fixedLenKeyBytes, "AES");
        javax.crypto.Cipher cipherInstance = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherInstance.init(1, secretKeySpec, new IvParameterSpec(bArr3));
        encryptedBytes = cipherInstance.doFinal(inputText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                    InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] fixedLengthKeyBytes = new byte[32];
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 33) {
            System.arraycopy(keyBytes, 0, fixedLengthKeyBytes, 0, keyBytes.length);
        } else {
            System.arraycopy(keyBytes, 0, fixedLengthKeyBytes, 0, 32);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(fixedLengthKeyBytes, "AES");
        javax.crypto.Cipher cipherInstance = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherInstance.init(2, secretKeySpec, new IvParameterSpec(new byte[16]));
        return new String(
                cipherInstance.doFinal(Base64.getDecoder().decode(encryptedText)), StandardCharsets.UTF_8);
    }
}
