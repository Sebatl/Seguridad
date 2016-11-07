/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorDialog {

    public static void showError(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
