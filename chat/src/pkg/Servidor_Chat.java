package pkg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Servidor_Chat {

	public static ArrayList<Socket> clientSocketList = new ArrayList<Socket>();
	public static HashMap<Integer, String> clientSocketListH = new HashMap<Integer, String>(); 
    static ServerSocket s_socket;
    static Socket c_socket;
		
	public static void main(String[] args) throws InterruptedException, IOException {
			
		TH_Chat chat = new TH_Chat();
		chat.start();
		chat.join();		
	}
	
}
