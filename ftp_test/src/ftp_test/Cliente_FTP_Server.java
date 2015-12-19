package ftp_test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente_FTP_Server {
	
	public static void main(String[] args) throws IOException {
		DataInputStream inputFileList;
		Socket c_socket;
		
		c_socket = new Socket("127.0.0.1", 8510);
		
		System.out.println("Obtendo lista de arquivos...");
		inputFileList = new DataInputStream(c_socket.getInputStream());
        
        System.out.println(inputFileList.readUTF());
        c_socket.close();
	}
}
