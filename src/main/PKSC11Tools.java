/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PKSC11Tools {

    static String configName = "C:\\pkcs11.cfg";
    static Provider p;

    public static void prepare() {

        try {
            Provider pkcs11Provider
                    = new sun.security.pkcs11.SunPKCS11(configName);
            Security.addProvider(pkcs11Provider);

            //char[] pin = {'8', '6', '2', '0'};
            //KeyStore smartCardKeyStore = KeyStore.getInstance("PKCS11");
            //smartCardKeyStore.load(null, pin);
            KeyStore.CallbackHandlerProtection chp
                    = new KeyStore.CallbackHandlerProtection(new MyGuiCallbackHandler());
            KeyStore.Builder builder
                    = KeyStore.Builder.newInstance("PKCS11", null, chp);

            KeyStore smartCardKeyStore = builder.getKeyStore();

            Enumeration aliasesEnum = smartCardKeyStore.aliases();
            while (aliasesEnum.hasMoreElements()) {
                String alias = (String) aliasesEnum.nextElement();
                System.out.println("Alias: " + alias);
                X509Certificate cert
                        = (X509Certificate) smartCardKeyStore.getCertificate(alias);
                System.out.println("Certificate: " + cert);
                PrivateKey privateKey
                        = (PrivateKey) smartCardKeyStore.getKey(alias, null);
                System.out.println("Private key: " + privateKey);
                
                
                cert.getSerialNumber();
               
                
            }

        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            Logger.getLogger(PKSC11Tools.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
