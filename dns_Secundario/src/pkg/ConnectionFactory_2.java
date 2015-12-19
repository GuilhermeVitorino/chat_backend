package pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory_2{
	
	public Connection getConnection(){
		
		try{
			return DriverManager.getConnection("jdbc:mysql://localhost/dnsdb2", "root", "corinthians1910357");
		}catch (SQLException e){
				throw new RuntimeException(e);
		}
		
	}
}