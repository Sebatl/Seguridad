/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

                //Firmo el documento
                signatureAlgorithm.update(fileBytes);
                byte[] docSig = signatureAlgorithm.sign();

                //Firmo la cedula
                signatureAlgorithm.update(User.ci.getBytes());
                byte[] sourceSig = signatureAlgorithm.sign();
                
                saveSign(file, docSig, sourceSig);
            } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException ex) {
                Logger.getLogger(SignManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static void saveSign(File file, byte[] docSign, byte[] sourceSign) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar");
        int result = chooser.showSaveDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                SignedObject sO = new SignedObject();
                sO.setDocument(FileTools.getFileBytes(file));
                sO.setFileName(file.getName());
                sO.setDocSign(docSign);
                sO.setSourceSign(sourceSign);
                sO.setKey(User.cert.getPublicKey().getEncoded());
                sO.setSource(User.ci);
                //User.cert.getIssuerX500Principal().getName();

                try {
                    FileOutputStream fout = new FileOutputStream(chooser.getSelectedFile().getAbsolutePath() + ".sgo");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(sO);
                    oos.close();
                    System.out.println("Done");
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                /*FileOutputStream fos;
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

                }*/
                break;
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Cancel or the close-dialog icon was clicked");
                break;
            case JFileChooser.ERROR_OPTION:
                System.out.println("Error");
                break;
        }
    }

    /*public static boolean checkSign(File key, File signFile, File file) {
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
    }*/
    public static SignedObject checkSign(File file) {
        SignedObject sO = recoverSign(file);
        if (sO != null) {
            PublicKey publicKey = createPublicKey(sO.getKey());
            if (publicKey != null) {
                try {
                    Signature signature = Signature.getInstance("SHA1withRSA");
                    byte[] fileBytes = sO.getDocument();
                    signature.initVerify(publicKey);
                    signature.update(fileBytes);
                    //Compruebo la firma del documento
                    boolean docVerifies = signature.verify(sO.getDocSign());
                    if (docVerifies) {
                        signature.update(sO.getSource().getBytes());
                        //Compruebo el emisor del documento
                        if (signature.verify(sO.getSourceSign())) {
                            return sO;
                        }
                    } else {
                        return null;
                    }
                    
                } catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(SignManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                return null;
            }
        }
        return null;
    }
    
    private static SignedObject recoverSign(File file) {
        FileInputStream fin = null;
        try {
            SignedObject sO;
            fin = new FileInputStream(file);
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(fin);
                sO = (SignedObject) ois.readObject();
                return sO;
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(SignManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SignManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fin.close();
            } catch (IOException ex) {
                Logger.getLogger(SignManager.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    /*private static byte[] loadSignature(File file) {
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
    }*/
}
