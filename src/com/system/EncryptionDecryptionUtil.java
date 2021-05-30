package com.system;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

// AES Encryption
// This is an Experimental Feature,
// WE DO NOT Recommend using it to encrypt your data
public class EncryptionDecryptionUtil {

    /**
     * A static method used to encrypt data using the AES
     * algorithm
     * @param secret the secret key to encrypt data
     * @param data the string to be encrypted
     * @return encrypted string
     */
    public static String encrypt(final String secret, final String data) {

        byte[] decodedKey = Base64.getDecoder().decode(secret);

        try {
            Cipher cipher = Cipher.getInstance("AES");
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occured while encrypting data", e);
        }

    }

    /**
     * A static method to decrypt an encoded string
     * using a unique key
     * @param secret secret key
     * @param encryptedString string to be decrypted
     * @return decrypted string
     */
    public static String decrypt(final String secret,
                                 final String encryptedString) {

        byte[] decodedKey = Base64.getDecoder().decode(secret);

        try {
            Cipher cipher = Cipher.getInstance("AES");
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
            return new String(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occured while decrypting data", e);
        }
    }

    /**
     *  Generates 256 Bit - Keys for the encryption
     * @return String : Key
     * @throws NoSuchAlgorithmException
     */
    public static String generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        SecureRandom secureRandom = new SecureRandom();
        int keyBitSize = 256;
        keyGenerator.init(keyBitSize, secureRandom);

        SecretKey secretKey = keyGenerator.generateKey();

        return Base64.getEncoder().encodeToString(secretKey.getEncoded());

    }
}