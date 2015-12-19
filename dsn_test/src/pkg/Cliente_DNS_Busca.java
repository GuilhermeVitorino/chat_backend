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

        //Busca endereõ de um serviço
        try {
            try{
            	c_socket = new Socket("127.0.0.1", 8810);
                System.out.println("Aplicacao conectada ao Servidor Primário!");
            }catch(Exception e){
            	c_socket = new Socket("127.0.0.1", 8820);
                System.out.println("Aplicacao conectada ao Servidor Secundário!");
            }
        	
            outputNome = new DataOutputStream(c_socket.getOutputStream());
            
            inputMensagem = new DataInputStream(c_socket.getInputStream());
            inputIp = new DataInputStream(c_socket.getInputStream());
            inputPorta = new DataInputStream(c_socket.getInputStream());
                        
            String strServico = "testeCadastroServico6";
            
            System.out.println("   Buscando serviço "+strServico+"...");
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
