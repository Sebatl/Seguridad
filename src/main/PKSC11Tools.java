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

    public static boolean prepare(char[] pin) {

        try {
            Provider pkcs11Provider
                    = new sun.security.pkcs11.SunPKCS11(configName);
            Security.addProvider(pkcs11Provider);

            KeyStore smartCardKeyStore = KeyStore.getInstance("PKCS11");
            smartCardKeyStore.load(null, pin);
            /*KeyStore.CallbackHandlerProtection chp
                    = new KeyStore.CallbackHandlerProtection(new MyGuiCallbackHandler());
            KeyStore.Builder builder
                    = KeyStore.Builder.newInstance("PKCS11", null, chp);

            KeyStore smartCardKeyStore = builder.getKeyStore();
             */
            Enumeration aliasesEnum = smartCardKeyStore.aliases();
            while (aliasesEnum.hasMoreElements()) {
                String alias = (String) aliasesEnum.nextElement();
                X509Certificate cert = (X509Certificate) smartCardKeyStore.getCertificate(alias);
                PrivateKey privateKey = (PrivateKey) smartCardKeyStore.getKey(alias, null);

                /*System.out.println(cert);
                System.out.println("Serial Number: " + cert.getSerialNumber());
                System.out.println("Subject DN: " + cert.getSubjectDN());
                System.out.println("Issuer DN: " + cert.getIssuerDN());*/
                User.cert = cert;
                User.ci = getDNIFromDN(cert.getSubjectDN().toString());
                User.privateKey = privateKey;
                //System.out.println("Cedula: " + User.ci);
            }
            return true;
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | IOException ex) {
            Logger.getLogger(PKSC11Tools.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }

    public String getNombreFromDN(String dn) {
        return dn.split("CN=")[1].split(",")[0];
    }

    public static String getDNIFromDN(String dn) {
        return dn.split("SERIALNUMBER=")[1].split(",")[0].replace("DNI", "");
    }

    public String getCountryFromDN(String dn) {
        return dn.split("C=")[1].split(",")[0];
    }

    public static void print(Object... params) {
        String s = "";
        for (Object i : params) {
            s += i + " ";
        }
        System.out.println(s);
    }
}
