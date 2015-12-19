package ftp;

import java.io.IOException;

public class Servidor_FTP {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		TH_FTP_Lista_Arquivos filelist = new TH_FTP_Lista_Arquivos();
		TH_FTP_Envio_Dados ftp = new TH_FTP_Envio_Dados();
		
		filelist.start();
		ftp.start();
		
		filelist.join();
		ftp.join();
	}
}
