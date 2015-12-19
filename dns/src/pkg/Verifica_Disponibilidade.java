package pkg;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Verifica_Disponibilidade{
	public Connection connection;
	static Socket c_socket;
	static Socket c_socket_replica;
	private DataOutputStream outputQuery;
	public ArrayList<Servico> servicesList;
		
	public void testaConexao(){
		servicesList = new ServicoDAO().getArrayStatico();
		connection = new ConnectionFactory().getConnection();
		for(Servico x : servicesList){
			try{
				c_socket = new Socket(x.getIp(), x.getPorta());
				c_socket.close();	
						
				if(x.getIndicador()==0){
					System.out.println("   Serviço "+x.getNomeservico()+" está disponível novamente");
					atualizaIndicador(1, x.getNomeservico(), x.getIp(), x.getPorta(),
						x.getIndicador());
				}
			}catch(Exception e){
				if(x.getIndicador()==1){
					System.out.println("   Serviço "+x.getNomeservico()+" não está disponível");
					atualizaIndicador(0, x.getNomeservico(), x.getIp(), x.getPorta(),
						x.getIndicador());
				}
			}
		}
		//atualiza o array statico
		ServicoDAO atualizaArray = new ServicoDAO();
		atualizaArray.atualizaArrayStatico();
	}
	
	public void atualizaIndicador(int altIndicador, String service, String ip, int porta, int indicador){
		String sql =
		"UPDATE dns SET indicador=? WHERE service=? and ip=? and port=? and indicador=?;";
		
		try {
			//prepared statement para inserção
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			
			//seta os valores
			stmt.setInt(1, altIndicador);
			stmt.setString(2, service);
			stmt.setString(3, ip);
			stmt.setInt(4, porta);
			stmt.setInt(5, indicador);
			String querySQL = stmt.toString();
			
			// executa
			stmt.execute();
			stmt.close();
			
			try {
				c_socket_replica = new Socket("127.0.0.1", 8823);
				outputQuery = new DataOutputStream(c_socket_replica.getOutputStream());
				outputQuery.writeUTF(querySQL);
				
			} catch (ConnectException e) {
				System.out.println("   Não foi possível se conectar com o servidor secundário! A atualização ficará pendente");
				TH_DNS_Envio_Atualizacao_Forcada.atualizacoesPendentesParaSecundario.add(querySQL);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
