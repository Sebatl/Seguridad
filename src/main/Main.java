/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.ID_Asker;

public class Main {

    public static void main(String args[]) {
        CardReader.prepare();

        ID_Asker ask = new ID_Asker();
        ask.setVisible(true);
    }

}
