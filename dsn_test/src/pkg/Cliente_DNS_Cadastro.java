package pkg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente_DNS_Cadastro {
	public static void main(String args[]) throws InterruptedException {
	    
        DataOutputStream outputServico;
        DataInputStream inputRetorno;

        //Cadastro de serviço pelo servidor DNS principal
        try {
            //criacao do socket
            Socket c_socket = new Socket("127.0.0.1", 8811);
            System.out.println("Aplicacao conectada ao Servidor DNS primário!");
            outputServico = new DataOutputStream(c_socket.getOutputStream());
            inputRetorno = new DataInputStream(c_socket.getInputStream());
                        
            String strNome = "testeCadastroServico12;192.168.1.3;7777";
            
            outputServico.writeUTF(strNome);
                
            System.out.println(inputRetorno.readUTF());

            c_socket.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
}
