
package reader;

import GUI.ID_Asker;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.smartcardio.Card;
import main.CardReader;
import main.User;


public class ReadThread extends Thread{
    
    ID_Asker asker;
    
    public ReadThread(ID_Asker asker){
        this.asker = asker;
    }
    
      @Override
      public synchronized void run() {
       Card card;
       do{
            System.out.println("Checking...");
            card = CardReader.read();
           try {
               wait(1000);
           } catch (InterruptedException ex) {
               Logger.getLogger(ReadThread.class.getName()).log(Level.SEVERE, null, ex);
           }
        }while(card == null);
       User.ci = "46917176";    //TODO Setear el valor correcto
       asker.ready();
    }
}
