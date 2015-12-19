package pkg;

import java.util.ConcurrentModificationException;

public class TH_DNS_Verifica_Disponibilidade extends Thread{
	
	public void run(){
		while(true){
			try {
				System.out.println("\nVerificando disponibilidade dos servi�os...");
				Verifica_Disponibilidade dnsVerifica = 
						new Verifica_Disponibilidade();
				dnsVerifica.testaConexao();
				System.out.println("Verifica��o conclu�da com sucesso!");
				sleep(600000);
			} catch (ConcurrentModificationException e){
				System.out.println("   O banco de dados foi alterdo durante a verifica��o. "
						+ "A verifica��o ser� reiniciada..!");
				Verifica_Disponibilidade dnsVerifica = 
						new Verifica_Disponibilidade();
				dnsVerifica.testaConexao();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}

	