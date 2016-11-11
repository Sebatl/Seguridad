package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Decryptor {

    public static byte[] decrypt(File file, String password) {
        KeyGenerator keygenerator;
        try {
            // Create Key
            //String password = "0000000000000000000000000000000000000000";
            byte key[] = password.getBytes();
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

            byte[] bytes = FileTools.getFileBytes(file);

            if (bytes == null) {
                return null;
            }

            Cipher unCipher;
            unCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            unCipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] fileDecrypted = unCipher.doFinal(bytes);

            return fileDecrypted;

        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeySpecException ex) {
            //Logger.getLogger(Decryptor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
