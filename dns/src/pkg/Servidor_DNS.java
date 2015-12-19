package pkg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor_DNS {
	static ServerSocket s_socket;
	public static DataInputStream inputAtualizacao;
	public static ArrayList<String> atualizacoes = new ArrayList<String>();
	
    public static void main(String[] args) throws InterruptedException, IOException {
    		System.out.println("Procurando por atualizações pendentes no Servidor DNS Secundário...");
	    	try{
	    		Socket c_socket = new Socket("127.0.0.1", 8822);
				
				while(true){
					inputAtualizacao = new DataInputStream(c_socket.getInputStream());
					String atualizacao = inputAtualizacao.readUTF();
					if(atualizacao.equals("atualizacao encerrada"))
						break;
					atualizacoes.add(atualizacao);
				}
				
				RecebeAtualizacaoForcada recebeAt =
						new RecebeAtualizacaoForcada(atualizacoes);
				
				c_socket.close();
				
	    	}catch(Exception e){
	    		System.out.println("   Servidor DNS secundário offline!");
	    	}
    		
    		System.out.println("\nServidor DNS online!");
	    	
	        TH_DNS_Cadastro dnsCad = new TH_DNS_Cadastro();
	        TH_DNS_Busca dnsBus = new TH_DNS_Busca();
	        TH_DNS_Verifica_Disponibilidade dnsDisp = new TH_DNS_Verifica_Disponibilidade();
	        TH_DNS_Envio_Atualizacao_Forcada dnsAtForc =
	        		new TH_DNS_Envio_Atualizacao_Forcada();
	        TH_DNS_Replica dnsReplica = new TH_DNS_Replica();
	       	        
	        dnsCad.start();
	        dnsBus.start();
	        dnsDisp.start();
	        dnsAtForc.start();
	        dnsReplica.start();

	        dnsCad.join();
	        dnsBus.join();
	        dnsDisp.join();
	        dnsAtForc.join();
	        dnsReplica.join();
    }
}
