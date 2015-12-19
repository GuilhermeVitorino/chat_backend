package pkg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Array;

public class RecebeAtualizacaoForcada_2 {
	private String query;
	public Connection connection;
		
	RecebeAtualizacaoForcada_2(ArrayList<String> atualizacoes){
		
		for(String atualizacao : atualizacoes){
			
				connection = new ConnectionFactory_2().getConnection();
		
				query = atualizacao.replaceAll("dns", "dns2");
				query = query.substring(query.indexOf(":")+2);
				
			try {
				//prepared statement para inser��o
				PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);
				
				System.out.println("   Executando a query: "+query);
				
				// executa
				stmt.execute();
				stmt.close();
												
				}catch (SQLException e) {
					//throw new RuntimeException(e);
					System.out.println(e);
					if(1062 == e.getErrorCode())
						System.out.println("   Servi�o j� cadastrado!");
				}
			}
			System.out.println("Base de dados do servidor secund�rio atualizada com sucesso!");

	}
}

