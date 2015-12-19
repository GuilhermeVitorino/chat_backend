package ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class FTPServer {
    public static void main(String[] args) throws Exception {

    	ServerSocket servsock = new ServerSocket(13267);
        
        while (true) {
            
        	System.out.println("Waiting...");
            Socket sock = servsock.accept();
            System.out.println("Accepted connection : " + sock);
            OutputStream os = sock.getOutputStream();
            new FTPServer().send(os);
            sock.close();
        }
        
    }

    public void send(OutputStream os) throws Exception {

    	File myFile = new File("C:/FTP/Clocks.txt");
        byte[] mybytearray = new byte[(int) myFile.length() + 1];
        
        //Criptografia
        String chave = "12100669";
        
        SecretKey chaveDES = new SecretKeySpec(chave.getBytes(),"DES");
        Cipher cifraDES;
        
        cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES);
        
        byte[] arquivoEncriptado = cifraDES.doFinal(mybytearray);
        
        
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(arquivoEncriptado, 0, arquivoEncriptado.length);
        System.out.println("Sending...");
        os.write(arquivoEncriptado, 0, arquivoEncriptado.length);
        os.flush();
    }

}

