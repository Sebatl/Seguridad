/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signer;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import main.FileTools;
import main.User;

/**
 *
 * @author Seba
 */
public class SignManager {

    public static void sign(File file) {
        byte[] fileBytes = FileTools.getFileBytes(file);
        {
            try {
                Signature signatureAlgorithm = Signature.getInstance("SHA1withRSA");
                signatureAlgorithm.initSign(User.privateKey);
                signatureAlgorithm.update(fileBytes);
                byte[] digitalSignature = signatureAlgorithm.sign();
                saveSign(digitalSignature);
            } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException ex) {
                Logger.getLogger(SignManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void saveSign(byte[] bytes) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar");
        int result = chooser.showSaveDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                FileOutputStream fos;
                try {
                    //Firma
                    fos = new FileOutputStream(chooser.getSelectedFile().getAbsolutePath() + ".sign");
                    fos.write(bytes);
                    fos.close();

                    //Clave pública
                    fos = new FileOutputStream(chooser.getSelectedFile().getAbsolutePath() + ".pk");
                    fos.write(User.cert.getPublicKey().getEncoded());
                    fos.close();

                } catch (HeadlessException | IOException e) {

                }
                break;
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Cancel or the close-dialog icon was clicked");
                break;
            case JFileChooser.ERROR_OPTION:
                System.out.println("Error");
                break;
        }
    }

    public static boolean checkSign(File key, File signFile, File file, JFrame frame) {
        try {
            //Cargo la clave
            PublicKey publicKey = loadPublicKey(key);
            if (publicKey != null) {
                byte[] signatureBytes = loadSignature(signFile);
                if (signatureBytes != null) {
                    Signature signature = Signature.getInstance("SHA1withRSA");
                    byte[] fileBytes = FileTools.getFileBytes(file);
                    signature.initVerify(publicKey);
                    signature.update(fileBytes);

                    boolean verifies = signature.verify(signatureBytes);

                    //System.out.println("signature verifies: " + verifies);

                    return verifies;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException ex) {
            Logger.getLogger(SignManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static PublicKey loadPublicKey(File file) {
        try {
            FileInputStream keyfis = null;
            keyfis = new FileInputStream(file);
            byte[] encKey = new byte[keyfis.available()];
            keyfis.read(encKey);
            keyfis.close();

            PublicKey key = createPublicKey(encKey);

            return key;

        } catch (IOException ex) {
            Logger.getLogger(SignManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static PublicKey createPublicKey(byte[] encodedPublicKey) {
        try {
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            String algorithm = "RSA";
            KeyPairGenerator kpgen = KeyPairGenerator.getInstance(algorithm);
            Provider provider = kpgen.getProvider();

            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm, provider.getName());

            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return publicKey;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException ex) {
            Logger.getLogger(SignManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static byte[] loadSignature(File file) {
        FileInputStream sigfis = null;
        try {
            sigfis = new FileInputStream(file);
            byte[] sigToVerify = new byte[sigfis.available()];
            sigfis.read(sigToVerify);
            sigfis.close();
            return sigToVerify;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignManager.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(SignManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                sigfis.close();

            } catch (IOException ex) {
                Logger.getLogger(SignManager.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
