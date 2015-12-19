package pkg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TH_DNS_Cadastro_2 extends Thread {
	static ServerSocket s_socket;
    static Socket c_socket;
    static Socket c_socket_replica;
	
	public void run(){
		try {
			s_socket = new ServerSocket(8821, 10);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		while(true){
			try {
		        new TH_DNS_Cadastro_TrataCliente_2(s_socket.accept()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
