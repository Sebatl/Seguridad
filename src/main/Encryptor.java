package main;

import com.sun.crypto.provider.AESKeyGenerator;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Encryptor {

    public static byte[] encrypt(File file, String password) {
        try {

            String newPass = password;

            while (newPass.length() < 24) {
                newPass += password;
            }
            newPass = HashTools.getHash(newPass);

            byte key[] = newPass.getBytes();
            DESedeKeySpec desKeySpec = new DESedeKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

            byte[] bytes = FileTools.getFileBytes(file);

            Cipher cipher;

            cipher = Cipher.getInstance("DESede/CBC/PKCS5PADDING");

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[8]));

            byte[] fileEncrypted = cipher.doFinal(bytes);

            return fileEncrypted;

        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeySpecException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
