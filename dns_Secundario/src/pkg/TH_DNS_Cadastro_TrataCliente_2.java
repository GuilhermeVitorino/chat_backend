package pkg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class TH_DNS_Cadastro_TrataCliente_2 extends Thread {
    
	private Socket client;
	private Socket c_socket_replica;
	private String strService;
	private DataOutputStream outputMensagem;
	private DataOutputStream outputQuery;
	private DataInputStream mensagemRetorno;
	private String querySQL;
	public static ArrayList<Servico_2> servicesList = new ArrayList<Servico_2>();
    
    public TH_DNS_Cadastro_TrataCliente_2(Socket s){
        client = s;
    }
	    
	public void cadastra() throws IOException{
		
		outputMensagem = new DataOutputStream(client.getOutputStream());
	
		Servico_2 servico = new Servico_2().strToService(strService);
		
		try{
			
		System.out.println("\nIniciando o cadastro do serviço: "+strService.substring(0, strService.indexOf(";")));	
		//efetua o cadastro e retorna a query para replicar o cadastro no servidor dns secundario
		querySQL = new ServicoDAO_2().adiciona(servico);	
		
		outputMensagem.writeUTF("Serviço cadastrado com sucesso!");
		System.out.println("Serviço cadastrado com sucesso!");
		
		//inicia a replicacao do servico cadastrado acima para o servidor dns secundario
		c_socket_replica = new Socket("127.0.0.1", 8813);
		outputQuery = new DataOutputStream(c_socket_replica.getOutputStream());
		mensagemRetorno = new DataInputStream(c_socket_replica.getInputStream());
		
		outputQuery.writeUTF(querySQL);
		System.out.println(mensagemRetorno.readUTF());
	
		}catch (SQLException e) {
			if(1062 == e.getErrorCode())
				outputMensagem.writeUTF("Serviço já cadastrado!");
		}catch(java.net.ConnectException e){
			if("Connection refused: connect".equals(e.getMessage()))
				System.out.println("   Não foi possivel efetuar a conexão com o servidor primário! A atualização ficará pendente");
				TH_DNS_Envio_Atualizacao_Forcada_2.atualizacoesPendentesParaSecundario.add(querySQL);
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
