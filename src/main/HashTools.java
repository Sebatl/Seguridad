/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HashTools {

    public static String getHash(String stringToHash) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(stringToHash.getBytes());
            String encryptedString = new String(messageDigest.digest());
            System.out.println(encryptedString);
            return encryptedString;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashTools.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
