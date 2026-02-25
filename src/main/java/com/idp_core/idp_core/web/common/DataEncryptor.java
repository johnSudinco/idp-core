package com.idp_core.idp_core.web.common;


import com.idp_core.idp_core.domain.exception.EncryptionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Arrays;

@Component
public class DataEncryptor {

    private final SecretKeySpec secretKey;

    public DataEncryptor(@Value("${DB_ENCRYPTION_KEY}") String key) {
        byte[] keyBytes = Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), 16); // AES-128
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new EncryptionException("Error al encriptar texto", e);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decoded = Base64.getDecoder().decode(encryptedText);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new EncryptionException("Error al desencriptar texto", e);
        }
    }
}
