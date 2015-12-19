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
		
		System.out.println("\nRecebendo atualiza��o...");
				
		try {
		//prepared statement para inser��o
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);
		
		System.out.println("   Executando a query: "+query);
		
		// executa
		stmt.execute();
		stmt.close();
		
		outputMensagem.writeUTF("Base de dados do servidor sercund�rio atualizada com sucesso!");
		System.out.println("Atualiza��o recebida com sucesso!");
		
		//atualizando arrayListStatic
		ServicoDAO atualizaArray = new ServicoDAO();
		atualizaArray.atualizaArrayStatico();
		
		}catch (SQLException e) {
			//throw new RuntimeException(e);
			System.out.println(e);
			if(1062 == e.getErrorCode())
				outputMensagem.writeUTF("Servi�o j� cadastrado!");
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
	        
	        outputMensagem.writeUTF("Serividor prim�rio atualizado com sucesso!");
			
			c_socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}
}
