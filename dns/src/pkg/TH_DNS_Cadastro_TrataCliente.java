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
			
		System.out.println("\nIniciando o cadastro do serviço: "+strService.substring(0, strService.indexOf(";")));	
		//efetua o cadastro e retorna a query para replicar o cadastro no servidor dns secundario
		querySQL = new ServicoDAO().adiciona(servico);	
		
		outputMensagem.writeUTF("Serviço cadastrado com sucesso!");
		System.out.println("Serviço cadastrado com sucesso!");
		
		//inicia a replicacao do servico cadastrado acima para o servidor dns secundario
		c_socket_replica = new Socket("127.0.0.1", 8823);
		outputQuery = new DataOutputStream(c_socket_replica.getOutputStream());
		mensagemRetorno = new DataInputStream(c_socket_replica.getInputStream());
		
		outputQuery.writeUTF(querySQL);
		System.out.println(mensagemRetorno.readUTF());
	
		}catch (SQLException e) {
			if(1062 == e.getErrorCode())
				outputMensagem.writeUTF("Serviço já cadastrado!");
		}catch(java.net.ConnectException e){
			if("Connection refused: connect".equals(e.getMessage()))
				System.out.println("   Não foi possivel efetuar a conexão com o servidor secundário! A atualização ficará pendente");
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
