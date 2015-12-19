package chat_test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.sound.midi.Receiver;
import javax.swing.plaf.SliderUI;


public class Cliente_Chat {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		Socket c_socket;
		Socket c_socket_mensagem;
		
		c_socket = new Socket("127.0.0.1", 8840);
		
		DataInputStream input;
		DataOutputStream output;
		DataInputStream dado;
		
		dado = new DataInputStream(System.in);
        output = new DataOutputStream(c_socket.getOutputStream());

        enviar t_envia = new enviar(output, dado);
        
		input = new DataInputStream(c_socket.getInputStream());
		
		receber t_recebe = new receber(input);
		t_envia.start();
		t_recebe.start();
		t_envia.join();
		t_recebe.join();
		
	}
	
}
