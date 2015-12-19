package chat_test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeanmarcoslaine
 */
public class receber extends Thread {
    public DataInputStream chega;
    public receber(DataInputStream par){
        chega=par;
    }
    @Override
    public void run(){
        String s = "";
        int i = 0;
            while (i < 10000) {
            try {
                System.out.println();
                System.out.println(chega.readUTF());
            } catch (IOException ex) {
                Logger.getLogger(receber.class.getName()).log(Level.SEVERE, null, ex);
            }
                i++;
            }
    }
}
