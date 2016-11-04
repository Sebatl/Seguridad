/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PKSC11Tools {

    static String configName = "/opt/bar/cfg/pkcs11.cfg";
    static String password = "1234";
    static Provider p;

    public static void prepare() {
        p = new sun.security.pkcs11.SunPKCS11(configName);
        Security.addProvider(p);
        KeyStore ks;
        try {
            ks = KeyStore.getInstance("PKCS11", p); //p is the provider created above
            ks.load(null, password.toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex) {
            Logger.getLogger(PKSC11Tools.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void prepare2() { //http://stackoverflow.com/questions/21167927/getting-certificates-from-pkcs11-smartcard-without-pin-password
        String pkcs11Config = "name = SmartCard\nlibrary = /path/to/libraby.so";
        ByteArrayInputStream confStream = new ByteArrayInputStream(pkcs11Config.getBytes());
        Provider prov = new sun.security.pkcs11.SunPKCS11(confStream);
        Security.addProvider(prov);
        KeyStore cc = null;
        String pin = "";
        try {
            cc = KeyStore.getInstance("PKCS11", prov);
            KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(pin.toCharArray());
            cc.load(null, pp.getPassword());
            Enumeration aliases = cc.aliases();
            while (aliases.hasMoreElements()) {
                Object alias = aliases.nextElement();
                try {
                    X509Certificate cert0 = (X509Certificate) cc.getCertificate(alias.toString());
                    System.out.println("I am: " + cert0.getSubjectDN().getName());
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
