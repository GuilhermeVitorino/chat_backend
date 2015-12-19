package pkg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Array;

public class RecebeAtualizacaoForcada {
	private String query;
	public Connection connection;
		
	RecebeAtualizacaoForcada(ArrayList<String> atualizacoes){
		
		for(String atualizacao : atualizacoes){
			
				connection = new ConnectionFactory().getConnection();
		
				query = atualizacao.replaceAll("dns2", "dns");
				query = query.substring(query.indexOf(":")+2);
				
			try {
				//prepared statement para inserção
				PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);
				
				System.out.println("   Executando a query: "+query);
				
				// executa
				stmt.execute();
				stmt.close();
												
				}catch (SQLException e) {
					//throw new RuntimeException(e);
					System.out.println(e);
					if(1062 == e.getErrorCode())
						System.out.println("   Serviço já cadastrado!");
				}
			}
			System.out.println("Base de dados do servidor primário atualizada com sucesso!");

	}
}

