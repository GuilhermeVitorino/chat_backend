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

public class Verifica_Disponibilidade_2{
	public Connection connection;
	static Socket c_socket;
	static Socket c_socket_replica;
	private DataOutputStream outputQuery;
	public ArrayList<Servico_2> servicesList;
		
	public void testaConexao(){
		servicesList = new ServicoDAO_2().getArrayStatico();
		connection = new ConnectionFactory_2().getConnection();
		for(Servico_2 x : servicesList){
			try{
				c_socket = new Socket(x.getIp(), x.getPorta());
				c_socket.close();	
						
				if(x.getIndicador()==0){
					System.out.println("   Servi�o "+x.getNomeservico()+" est� dispon�vel novamente");
					atualizaIndicador(1, x.getNomeservico(), x.getIp(), x.getPorta(),
						x.getIndicador());
				}
			}catch(Exception e){
				if(x.getIndicador()==1){
					System.out.println("   Servi�o "+x.getNomeservico()+" n�o est� dispon�vel");
					atualizaIndicador(0, x.getNomeservico(), x.getIp(), x.getPorta(),
						x.getIndicador());
				}
			}
		}
		//atualiza o array statico
		ServicoDAO_2 atualizaArray = new ServicoDAO_2();
		atualizaArray.atualizaArrayStatico();
	}
	
	public void atualizaIndicador(int altIndicador, String service, String ip, int porta, int indicador){
		String sql =
		"UPDATE dns2 SET indicador=? WHERE service=? and ip=? and port=? and indicador=?;";
		
		try {
			//prepared statement para inser��o
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
				c_socket_replica = new Socket("127.0.0.1", 8813);
				outputQuery = new DataOutputStream(c_socket_replica.getOutputStream());
				outputQuery.writeUTF(querySQL);
				
			} catch (ConnectException e) {
				System.out.println("   N�o foi poss�vel se conectar com o servidor secund�rio! A atualiza��o ficar� pendente");
				TH_DNS_Envio_Atualizacao_Forcada_2.atualizacoesPendentesParaSecundario.add(querySQL);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
