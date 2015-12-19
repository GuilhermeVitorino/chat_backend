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
public class enviar extends Thread {
    public DataOutputStream sai;
    public DataInputStream teclado;
    public enviar(DataOutputStream par, DataInputStream par2){
        sai=par;
        teclado=par2;
    }
    @Override
    public void run(){
        String s = "";
        int i=0;
        while (i < 10000) {
                System.out.println();
            try {
                s = teclado.readLine();
                
                sai.writeUTF(s);
            } catch (IOException ex) {
                Logger.getLogger(enviar.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
    }
}

