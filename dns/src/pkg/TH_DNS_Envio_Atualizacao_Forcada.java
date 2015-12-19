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

public class TH_DNS_Envio_Atualizacao_Forcada extends Thread {
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
	public static ArrayList<String> atualizacoesPendentesParaSecundario = new ArrayList<String>();
	
	public TH_DNS_Envio_Atualizacao_Forcada() throws IOException{
		s_socket = new ServerSocket(8812, 10);
	}

	public void run(){
	
	while(true){
		try {
			
	        c_socket = s_socket.accept();

	        for(String query : atualizacoesPendentesParaSecundario){
	        	outputMensagem = new DataOutputStream(c_socket.getOutputStream());
		        outputMensagem.writeUTF(query);
	        }
	        outputMensagem = new DataOutputStream(c_socket.getOutputStream());
	        outputMensagem.writeUTF("atualizacao encerrada");
			atualizacoesPendentesParaSecundario.clear();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}
}
