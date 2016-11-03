/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

public class PasswordChecker {

    public static boolean Check(String password) {
        if (password.length() >= 6) { //Largo
            if (password.matches(".*[0-9].*")) {    //Número
                if (password.matches(".*[A-Z].*")) {    //Letra mayuscula
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
