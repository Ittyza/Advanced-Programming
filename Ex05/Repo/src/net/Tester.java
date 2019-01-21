package net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;

public class Tester {
	
	public static void main(String[] args){
		try{
			HelloServer serv = new HelloServer();
			HelloClient client = new HelloClient();
			client.connect(null, serv.listen());
			PrintStream console = new PrintStream(client.clientSocket.getOutputStream());
			client.connect(null, serv.listen());
			serv.run(console);
			client.run(console, "http://localhost:", serv.listen(), "Itty");
		} catch (IOException e){
			System.out.println("NOOOO");
		}
		
		
		
		
	}
}
