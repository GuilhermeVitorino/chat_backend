package pkg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class TH_DNS_Busca extends Thread {
	static ServerSocket s_socket;
    static Socket c_socket;
	public DataInputStream inputNome;
	public DataOutputStream outputMensagem;
	public DataOutputStream outputIp;
	public DataOutputStream outputPorta;
	public Connection connection;
	public Servico service;
	public static ArrayList<Servico> servicesList = new ArrayList<Servico>();
	public int found = 0;
	
	public TH_DNS_Busca() throws IOException{
		s_socket = new ServerSocket(8810, 10);
	}
				
	public void find() throws IOException{
		String nomeServico = inputNome.readUTF();
		for(Servico x : servicesList){
			if(x.getNomeservico().equals(nomeServico) && x.getIndicador()==1){
				this.found = 1;
				outputMensagem.writeUTF("Serviço localizado!");
				outputIp.writeUTF(x.getIp());
				outputPorta.writeUTF(""+x.getPorta());
			}
		}
				
		if(this.found==0){
			outputMensagem.writeUTF("Serviço não localizado!");
			outputIp.writeUTF(" ");
			outputPorta.writeUTF(" ");
		}	
	}
	
	public void run(){
		while(true){
			try {
				servicesList = new ServicoDAO().getArrayStatico();
		        c_socket = s_socket.accept();
		        	
	        	System.out.println("Cliente do IP " + c_socket.getInetAddress() + " conectou!!");
		        inputNome = new DataInputStream(c_socket.getInputStream());
		        
		        outputMensagem = new DataOutputStream(c_socket.getOutputStream());
		        outputIp = new DataOutputStream(c_socket.getOutputStream());
		        outputPorta = new DataOutputStream(c_socket.getOutputStream());
				
				find();
				
				c_socket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
