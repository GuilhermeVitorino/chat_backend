package pkg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor_DNS_2 {
	static ServerSocket s_socket;
	public static DataInputStream inputAtualizacao;
	public static ArrayList<String> atualizacoes = new ArrayList<String>();
	
    public static void main(String[] args) throws InterruptedException, IOException {
    		System.out.println("Procurando por atualizações pendentes no Servidor DNS Primário...");
	    	try{
	    		Socket c_socket = new Socket("127.0.0.1", 8812);
				
				while(true){
					inputAtualizacao = new DataInputStream(c_socket.getInputStream());
					String atualizacao = inputAtualizacao.readUTF();
					if(atualizacao.equals("atualizacao encerrada"))
						break;
					atualizacoes.add(atualizacao);
				}
				
				RecebeAtualizacaoForcada_2 recebeAt =
						new RecebeAtualizacaoForcada_2(atualizacoes);
				
				c_socket.close();
				
	    	}catch(Exception e){
	    		System.out.println("   Servidor DNS Primário offline!");
	    	}
    		
    		System.out.println("\nServidor Secundário DNS online!");
	    	
	        TH_DNS_Cadastro_2 dnsCad = new TH_DNS_Cadastro_2();
	        TH_DNS_Busca_2 dnsBus = new TH_DNS_Busca_2();
	        TH_DNS_Verifica_Disponibilidade_2 dnsDisp = new TH_DNS_Verifica_Disponibilidade_2();
	        TH_DNS_Envio_Atualizacao_Forcada_2 dnsAtForc =
	        		new TH_DNS_Envio_Atualizacao_Forcada_2();
	        TH_DNS_Replica_2 dnsReplica = new TH_DNS_Replica_2();
	       	        
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
