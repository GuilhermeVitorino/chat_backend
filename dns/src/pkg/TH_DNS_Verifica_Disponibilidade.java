package pkg;

import java.util.ConcurrentModificationException;

public class TH_DNS_Verifica_Disponibilidade extends Thread{
	
	public void run(){
		while(true){
			try {
				System.out.println("\nVerificando disponibilidade dos serviços...");
				Verifica_Disponibilidade dnsVerifica = 
						new Verifica_Disponibilidade();
				dnsVerifica.testaConexao();
				System.out.println("Verificação concluída com sucesso!");
				sleep(600000);
			} catch (ConcurrentModificationException e){
				System.out.println("   O banco de dados foi alterdo durante a verificação. "
						+ "A verificação será reiniciada..!");
				Verifica_Disponibilidade dnsVerifica = 
						new Verifica_Disponibilidade();
				dnsVerifica.testaConexao();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}

	