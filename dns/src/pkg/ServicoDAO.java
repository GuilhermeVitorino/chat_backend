package pkg;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServicoDAO {
	
	private Connection connection;
	private ArrayList<Servico> servicesList = new ArrayList<Servico>();
	private static ArrayList<Servico> servicesListStatic = new ArrayList<Servico>();
	
	public ServicoDAO(){
		 connection = new ConnectionFactory().getConnection();
		 atualizaArrayStatico();
	}
	
	public void atualizaArrayStatico(){
		//obter tabela de endereços
		try {			
			servicesListStatic.clear();
			//query sql para obter todos os registros da tabela DNS no banco de dados
			String sql = "select * from dns";
			
			//prepared statement para busca dos servicos
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			
			//armazenamento do resultado da busca em uma variavel do tipo ResultSet
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {				
				// criando o objeto Servico para ser armazenado no ArrayList services
				Servico srv = new Servico();
				srv.setNomeservico(rs.getString("service"));
				srv.setIp(rs.getString("ip"));
				srv.setPorta(rs.getInt("port"));
				srv.setIndicador(rs.getInt("indicador"));
				
				// adicionando o objeto à lista
				servicesListStatic.add(srv);
			}
			rs.close();
			stmt.close();

		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String adiciona(Servico servico) throws IOException, SQLException{

		String sql = "insert into dns " +
		"(service,ip,port,indicador)" +
		" values (?,?,?,1)";
	
		//prepared statement para inserção
		PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
		
		//seta os valores
		stmt.setString(1, servico.getNomeservico());
		stmt.setString(2, servico.getIp());
		stmt.setInt(3,(servico.getPorta()));
		
		String querySQL = stmt.toString();
		
		// executa
		stmt.execute();
		stmt.close();
		
		//adiciona o servico na array 
		servicesListStatic.add(servico);
		
		return querySQL;
	}
	
	public ArrayList<Servico> getArrayStatico(){
		return servicesListStatic;
	}

}
