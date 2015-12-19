package pkg;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {
	public static void main(String[] args) {
		try {
			Socket c_socket = new Socket("127.0.0.1", 8813);
			System.out.println("Aplicacao conectada ao Servidor Primário!");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
