package reader;

import GUI.ID_Asker;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.smartcardio.Card;
import main.CardReader;

public class ReadThread extends Thread {

    private ID_Asker asker;
    private String pin;

    public ReadThread(ID_Asker asker) {
        this.asker = asker;
    }

    @Override
    public synchronized void run() {
        Card card;
        do {
            System.out.println("Checking...");
            card = CardReader.read(asker);
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReadThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (card == null);
        asker.ready();
    }
}
