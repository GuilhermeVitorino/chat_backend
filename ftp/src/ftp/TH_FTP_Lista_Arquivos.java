package ftp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class TH_FTP_Lista_Arquivos extends Thread {
	
	public DataOutputStream outputLista;
	static ServerSocket s_socket;
    static Socket c_socket;
	
	public TH_FTP_Lista_Arquivos() throws IOException {
		s_socket = new ServerSocket(8510, 10);
	}
	
	public void run(){
		while(true){
			try {
				c_socket = s_socket.accept();
				
				System.out.println("Cliente do IP " + c_socket.getInetAddress() + " conectou!!");
				
				String strFiles = "";
			
				File folder = new File("C:/FTP/");
				File[] listOfFiles = folder.listFiles();
	
			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			    	  strFiles=strFiles+listOfFiles[i].getName()+"-";
			        System.out.println();
			      } else if (listOfFiles[i].isDirectory()) {
			        System.out.println("Directory " + listOfFiles[i].getName());
			      }
			    }
			    outputLista = new DataOutputStream(c_socket.getOutputStream());
			    outputLista.writeUTF(strFiles);
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

