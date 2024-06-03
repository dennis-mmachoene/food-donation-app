/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package encryption;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Dennis
 */
public class PasswordManager implements PasswordEncrypterContoller {

    private final String AES_ALGORITHM = "AES";
    private final String HASHING_ALGORITHM = "SHA-256";

    private final String SECRET_KEY = "k2s$9K!#jd6@3zPq";

    public PasswordManager() {
    }

    
    private byte[] generateKey(String secretKey) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(HASHING_ALGORITHM);
        byte[] key = secretKey.getBytes();
        key = sha.digest(key);
        return Arrays.copyOf(key, 16);
    }

    @Override
    public String encrypt(String password) throws Exception {
        byte[] key = generateKey(SECRET_KEY);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);

    }

    @Override
    public String decrypt(String password) throws Exception {
        byte[] key = generateKey(SECRET_KEY);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedByte = cipher.doFinal(Base64.getDecoder().decode(password));
        return new String(decryptedByte);
    }
}
