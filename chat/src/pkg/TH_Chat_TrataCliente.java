package pkg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TH_Chat_TrataCliente extends Thread {
    
	private Socket client;
	public DataInputStream chega;
    public DataOutputStream mensagemParaTodos;
    	
    public TH_Chat_TrataCliente(Socket s){
        client = s;
    }
 
    public void run(){  	
  
        while (true) {
            try {
            	
            	DataInputStream inputMensagem = null;
            	String strMensagem = "";
            	String idMensagem = "";
            	String userPrivado = "";
            	String listaParticipantes = "#participantes-Todos";
            	
            	inputMensagem = new DataInputStream(client.getInputStream());
            	strMensagem = inputMensagem.readUTF();
            	
            	idMensagem = strMensagem.substring(0, strMensagem.indexOf("-"));
            	
            	strMensagem = strMensagem.substring(strMensagem.indexOf("-")+1,strMensagem.length());
            			
				switch(idMensagem) {
				
		            case "#entrada":
		            	
		            	for(Socket sk : Servidor_Chat.clientSocketList){
							
		            		mensagemParaTodos = new DataOutputStream(sk.getOutputStream());
							mensagemParaTodos.writeUTF("Cliente "+client.getPort()+" entrou!"+strMensagem);
							listaParticipantes = listaParticipantes+"-"+sk.getPort();
						}
		            	
		            	for(Socket sk : Servidor_Chat.clientSocketList){
							
		            		mensagemParaTodos = new DataOutputStream(sk.getOutputStream());
							mensagemParaTodos.writeUTF(listaParticipantes);
							
						}
		                break;        
		                
		                
		            case "#mensagem":
		            	
		            	for(Socket sk : Servidor_Chat.clientSocketList){
							
		            		mensagemParaTodos = new DataOutputStream(sk.getOutputStream());
							mensagemParaTodos.writeUTF("Cliente "+client.getPort()+" disse: "+strMensagem);
						}
		                break;
		                
		                
		            case "#privado":
		            	
		            	userPrivado = strMensagem.substring(0, strMensagem.indexOf("-"));
		            	strMensagem = strMensagem.substring(strMensagem.indexOf("-")+1,strMensagem.length());
		            	
		            	for(Socket sk : Servidor_Chat.clientSocketList){
							
		            		if(sk.getPort()==Integer.parseInt(userPrivado)){
		            			mensagemParaTodos = new DataOutputStream(sk.getOutputStream());
								mensagemParaTodos.writeUTF("Cliente "+client.getPort()+" disse (mensagem privada*): "+strMensagem);
								break;
		            		}
						}
		                break;
		                
		                
		            case "#saida":
		            	
		            	for(Socket sk : Servidor_Chat.clientSocketList){
							
		            		mensagemParaTodos = new DataOutputStream(sk.getOutputStream());
							mensagemParaTodos.writeUTF("Cliente "+client.getPort()+" saiu!");
						}
		            	
		            	for(int i=0; i < Servidor_Chat.clientSocketList.size(); i++){
		            		
		            		if(Servidor_Chat.clientSocketList.get(i).getPort()==client.getPort())
		            			Servidor_Chat.clientSocketList.remove(i);
						}
		           	
		            	for(Socket sk : Servidor_Chat.clientSocketList){
							listaParticipantes = listaParticipantes+"-"+sk.getPort();
						}
		            	
		            	for(Socket sk : Servidor_Chat.clientSocketList){
							
		            		mensagemParaTodos = new DataOutputStream(sk.getOutputStream());
							mensagemParaTodos.writeUTF(listaParticipantes);
							
						}
		            	
		            	client.close();
		            	break;
		        }
				
				if(idMensagem.equals("#saida"))
					break;
            	
            } catch (IOException ex) {
                
            	Logger.getLogger(receber.class.getName()).log(Level.SEVERE, null, ex);
                try {                	
					
                	chega.close();
					break;
				} catch (IOException e) {
					
					e.printStackTrace();
				}
            }         
        }
    }  
}
