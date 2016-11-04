package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Encryptor {

    public static byte[] encrypt(File file, String password) {
        KeyGenerator keygenerator;
        try {
            // Create Key
            //String password = "0000000000000000000000000000000000000000";
            byte key[] = password.getBytes();
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

            byte[] bytes = getFileBytes(file);

            Cipher cipher;

            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Initialize the cipher for encryption
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] fileEncrypted = cipher.doFinal(bytes);

            return fileEncrypted;

        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeySpecException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    private static byte[] getFileBytes(File file) {
        byte[] bFile = new byte[(int) file.length()];
        FileInputStream fileInputStream;
        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

            System.out.println("Done");
            return bFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
