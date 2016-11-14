package main;

import GUI.DialogManager;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class CardReader {

    private static CardTerminal terminal;

    public static void prepare() {
        try {
            TerminalFactory factory = TerminalFactory.getDefault();
            List<CardTerminal> terminals = factory.terminals().list();
            //System.out.println("Lectores: " + terminals);

            terminal = terminals.get(0);
        } catch (CardException e) {
            System.out.println("Sin lectores");
        }
    }

    public static Card read(JFrame asker) {
        try {
            if (terminal == null) {
                prepare();
            } else {
                Card card = terminal.connect("*");
                System.out.println("Tarjeta: " + card);

                JPasswordField pf = new JPasswordField();
              
                int okCxl = JOptionPane.showConfirmDialog(null, pf, "Ingresa el PIN", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (okCxl == JOptionPane.OK_OPTION) {
                    char[] pin = pf.getPassword();

                    if (PKSC11Tools.prepare(pin)) {
                        return card;
                    } else {
                        DialogManager.showError(asker, "PIN Inválido");
                        return null;
                    }

                } else {
                    asker.dispatchEvent(new WindowEvent(asker, WindowEvent.WINDOW_CLOSING));
                    return null;
                }
            }
        } catch (CardException ex) {
            //Logger.getLogger(CardReader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Sin tarjeta");
        }
        return null;
    }
}
