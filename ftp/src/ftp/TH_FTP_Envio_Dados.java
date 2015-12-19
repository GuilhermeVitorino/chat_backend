package ftp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TH_FTP_Envio_Dados extends Thread {
	static ServerSocket s_socket;
	static Socket c_socket;
	
	static String IV = "AAAAAAAAAAAAAAAA";
    static String textopuro = "teste texto 12345678\0\0\0";
    static String chaveencriptacao = "0123456789abcdef";
	
	public TH_FTP_Envio_Dados() throws IOException {
		s_socket = new ServerSocket(8520, 10);
	}
	
	public void run() {
    	while(true){
			try {	         
	            c_socket = s_socket.accept();
	            System.out.println("Accepted connection : " + c_socket);
	            
	            DataInputStream inputArquivo;
	            inputArquivo = new DataInputStream(c_socket.getInputStream());
	            String arquivo = inputArquivo.readUTF();
	            
	            OutputStream os = c_socket.getOutputStream();
	            send(os, arquivo);
	            c_socket.close();
            
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
    

	public void send(OutputStream os, String arquivo) throws Exception {
		// sendfile
	    File myFile = new File("C:/FTP/"+arquivo);
	    byte[] mybytearray = new byte[(int) myFile.length() + 1];
	    
	    FileInputStream fis = new FileInputStream(myFile);
	    BufferedInputStream bis = new BufferedInputStream(fis);
	    bis.read(mybytearray, 0, mybytearray.length);
	    
	    
	    Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        mybytearray = encripta.doFinal(mybytearray);
	    
	    	    
	    System.out.println("Sending...");
	    os.write(mybytearray, 0, mybytearray.length);
	    os.flush();
	}
	
	
    
}
