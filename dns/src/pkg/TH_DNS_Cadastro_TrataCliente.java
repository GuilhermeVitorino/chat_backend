package pkg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class TH_DNS_Cadastro_TrataCliente extends Thread {
    
	private Socket client;
	private Socket c_socket_replica;
	private String strService;
	private DataOutputStream outputMensagem;
	private DataOutputStream outputQuery;
	private DataInputStream mensagemRetorno;
	private String querySQL;
	public static ArrayList<Servico> servicesList = new ArrayList<Servico>();
    
    public TH_DNS_Cadastro_TrataCliente(Socket s){
        client = s;
    }
	    
	public void cadastra() throws IOException{
		
		outputMensagem = new DataOutputStream(client.getOutputStream());
	
		Servico servico = new Servico().strToService(strService);
		
		try{
			
		System.out.println("\nIniciando o cadastro do servi�o: "+strService.substring(0, strService.indexOf(";")));	
		//efetua o cadastro e retorna a query para replicar o cadastro no servidor dns secundario
		querySQL = new ServicoDAO().adiciona(servico);	
		
		outputMensagem.writeUTF("Servi�o cadastrado com sucesso!");
		System.out.println("Servi�o cadastrado com sucesso!");
		
		//inicia a replicacao do servico cadastrado acima para o servidor dns secundario
		c_socket_replica = new Socket("127.0.0.1", 8823);
		outputQuery = new DataOutputStream(c_socket_replica.getOutputStream());
		mensagemRetorno = new DataInputStream(c_socket_replica.getInputStream());
		
		outputQuery.writeUTF(querySQL);
		System.out.println(mensagemRetorno.readUTF());
	
		}catch (SQLException e) {
			if(1062 == e.getErrorCode())
				outputMensagem.writeUTF("Servi�o j� cadastrado!");
		}catch(java.net.ConnectException e){
			if("Connection refused: connect".equals(e.getMessage()))
				System.out.println("   N�o foi possivel efetuar a conex�o com o servidor secund�rio! A atualiza��o ficar� pendente");
				TH_DNS_Envio_Atualizacao_Forcada.atualizacoesPendentesParaSecundario.add(querySQL);
		}
	}
 
    public void run(){  
              try {
            	DataInputStream inputService = new DataInputStream(client.getInputStream());  
				strService =  inputService.readUTF();
				cadastra();
				inputService.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }  
}
