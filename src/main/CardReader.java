package main;

import java.util.List;
import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

public class CardReader {

    private static CardTerminal terminal;

    public static void prepare() {
        try {
            TerminalFactory factory = TerminalFactory.getDefault();
            List<CardTerminal> terminals = factory.terminals().list();
            System.out.println("Lectores: " + terminals);

            terminal = terminals.get(0);
        } catch (CardException e) {
            System.out.println("Sin lectores");
        }
    }

    public static Card read() {
        try {
            if (terminal == null) {
                prepare();
            } else {
                Card card = terminal.connect("*");
                System.out.println("Tarjeta: " + card);

                PKSC11Tools.prepare();

                return card;
            }
        } catch (CardException ex) {
            //Logger.getLogger(CardReader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Sin tarjeta");
        }
        return null;
    }
}
