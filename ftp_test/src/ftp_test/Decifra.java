package ftp_test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Decifra {
	
	public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
	
		InputStream in = new FileInputStream("c:\\Clocks.txt");
	    OutputStream out = new FileOutputStream("c:\\Clocks.txt");           // Transferindo bytes de entrada para saída
	    byte[] buf = new byte[1024];
	    int len;
	    
	    in.read(buf, 0, buf.length);
	    
	    String chave = "bbbbbbbb";
        SecretKey chaveDES  = new SecretKeySpec(chave.getBytes("UTF8"),"DES");
        
        Cipher cifraDES;
        cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cifraDES.init(Cipher.DECRYPT_MODE, chaveDES);
        
        byte[] arquivoEncriptado = cifraDES.doFinal(buf);
        
	     
	    while ((len = in.read(buf)) > 0) {
	    	out.write(arquivoEncriptado, 0, arquivoEncriptado.length);
	    }
	    
		in.close();
		out.close();	
	}
}
