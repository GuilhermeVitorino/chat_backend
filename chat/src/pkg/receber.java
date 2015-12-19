package pkg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeanmarcoslaine
 */
public class receber extends Thread {
    public DataInputStream chega;
    public DataOutputStream mensagemParaTodos;
    public receber(DataInputStream par){
        chega=par;
    }
    @Override
    public void run(){
        
            while (true) {
            try {
            	
            	for(Socket sk : Servidor_Chat.clientSocketList){
					
            		mensagemParaTodos = new DataOutputStream(sk.getOutputStream());
					mensagemParaTodos.writeUTF(chega.readUTF());
				}
            	
            } catch (IOException ex) {
                
            	Logger.getLogger(receber.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }
}
