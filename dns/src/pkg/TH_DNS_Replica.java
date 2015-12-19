package pkg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.text.StyleContext.SmallAttributeSet;

public class TH_DNS_Replica extends Thread {
	static ServerSocket s_socket;
    static Socket c_socket;
    static Socket c_socketVerificaPrincipal;
    public DataInputStream inputQuery;
    public DataInputStream inputNome;
	public DataInputStream inputIp;
	public DataInputStream inputPorta;
	public DataOutputStream outputMensagem;
	public Connection connection;
	public Servico service;
	public int found = 0;
	public String query;
	public TH_DNS_Replica() throws IOException{
		s_socket = new ServerSocket(8813, 10);
	}

	public void execQuery() throws IOException{
		connection = new ConnectionFactory().getConnection();
		
		System.out.println("\nRecebendo atualização...");
				
		try {
		//prepared statement para inserção
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);
		
		System.out.println("   Executando a query: "+query);
		
		// executa
		stmt.execute();
		stmt.close();
		
		outputMensagem.writeUTF("Base de dados do servidor sercundário atualizada com sucesso!");
		System.out.println("Atualização recebida com sucesso!");
		
		//atualizando arrayListStatic
		ServicoDAO atualizaArray = new ServicoDAO();
		atualizaArray.atualizaArrayStatico();
		
		}catch (SQLException e) {
			//throw new RuntimeException(e);
			System.out.println(e);
			if(1062 == e.getErrorCode())
				outputMensagem.writeUTF("Serviço já cadastrado!");
		}
	}
	
	public void run(){
	
	while(true){
		try {
			
	        c_socket = s_socket.accept();
	        	
        	inputQuery = new DataInputStream(c_socket.getInputStream());
	        
	        outputMensagem = new DataOutputStream(c_socket.getOutputStream());
			
	        query = inputQuery.readUTF();
	        query = query.replaceAll("dns2", "dns");
	        query = query.substring(query.indexOf(":")+2);
	        
	        execQuery();
	        
	        outputMensagem.writeUTF("Serividor primário atualizado com sucesso!");
			
			c_socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}
}
