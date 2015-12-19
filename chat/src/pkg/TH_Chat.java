package pkg;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TH_Chat extends Thread {
	static ServerSocket s_socket;
    static Socket c_socket;
    public DataOutputStream mensagemParaTodos;
	
	public void run(){
		
		try {
			s_socket = new ServerSocket(8840, 10);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		while(true){
			try {
				
				Socket s_socket_acc = s_socket.accept();
				
				new TH_Chat_TrataCliente(s_socket_acc).start();
				Servidor_Chat.clientSocketList.add(s_socket_acc);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
