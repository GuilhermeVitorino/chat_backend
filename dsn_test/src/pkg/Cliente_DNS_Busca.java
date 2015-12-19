package pkg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente_DNS_Busca {
	public static void main(String args[]) throws InterruptedException {
	    
        DataOutputStream outputNome;
        
        DataInputStream inputMensagem;
        DataInputStream inputIp;
        DataInputStream inputPorta;
        Socket c_socket;

        //Busca endere� de um servi�o
        try {
            try{
            	c_socket = new Socket("127.0.0.1", 8810);
                System.out.println("Aplicacao conectada ao Servidor Prim�rio!");
            }catch(Exception e){
            	c_socket = new Socket("127.0.0.1", 8820);
                System.out.println("Aplicacao conectada ao Servidor Secund�rio!");
            }
        	
            outputNome = new DataOutputStream(c_socket.getOutputStream());
            
            inputMensagem = new DataInputStream(c_socket.getInputStream());
            inputIp = new DataInputStream(c_socket.getInputStream());
            inputPorta = new DataInputStream(c_socket.getInputStream());
                        
            String strServico = "testeCadastroServico6";
            
            System.out.println("   Buscando servi�o "+strServico+"...");
            outputNome.writeUTF(strServico);
                
            System.out.println("   "+inputMensagem.readUTF());
            System.out.println("   "+inputIp.readUTF());
            System.out.println("   "+inputPorta.readUTF());

            c_socket.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
