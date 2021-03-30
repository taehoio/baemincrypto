package com.github.taehoio.baemincrypto;

import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cipher {

    private static final String Utf8CharsetName = "utf8";

    public static String encrypt(String inputText, String key) {
        byte[] encryptedBytes;
        try {
            byte[] fixedLenKeyBytes = new byte[32];
            byte[] keyBytes = key.getBytes(Utf8CharsetName);
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
        } catch (Exception unused) {
            encryptedBytes = null;
        }
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, String key) {
        try {
            byte[] fixedLengthKeyBytes = new byte[32];
            byte[] keyBytes = key.getBytes(Utf8CharsetName);
            if (keyBytes.length < 33) {
                System.arraycopy(keyBytes, 0, fixedLengthKeyBytes, 0, keyBytes.length);
            } else {
                System.arraycopy(keyBytes, 0, fixedLengthKeyBytes, 0, 32);
            }
            SecretKeySpec secretKeySpec = new SecretKeySpec(fixedLengthKeyBytes, "AES");
            javax.crypto.Cipher cipherInstance = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherInstance.init(2, secretKeySpec, new IvParameterSpec(new byte[16]));
            return new String(cipherInstance.doFinal(Base64.getDecoder().decode(encryptedText)), Utf8CharsetName);
        } catch (Exception unused) {
            return "";
        }
    }

}
