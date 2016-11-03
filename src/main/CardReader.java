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
            Card card = terminal.connect("*");
            System.out.println("Tarjeta: " + card);

            ATR atr = card.getATR();
            byte[] ATR = atr.getBytes();

            System.out.println("ATR: " + ATR);

            StringBuilder sb = new StringBuilder(ATR.length * 2);
            for (int i = 0; i < ATR.length; i++) {
                sb.append(String.format("%02x", ATR[i]));
            }

            System.out.println(sb.toString());
            return card;
        } catch (CardException ex) {
            //Logger.getLogger(CardReader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Sin tarjeta");
        }
        return null;
    }

}
