package net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HelloServer {

	public static final String ERR_MESSAGE = "IO Error!";
	public static final String LISTEN_MESSAGE = "Listening on port: ";
	public static final String HELLO_MESSAGE = "hello ";
	public static final String BYE_MESSAGE = "bye"; 
	
	ServerSocket sSocket;
	
	public ServerSocket getServerSocket() {
			return sSocket;

	}
	
	/**
	 * Listen on the first available port in a given list.
	 * 
	 * <p>Note: Should not throw exceptions due to ports being unavailable</p> 
	 *  
	 * @return The port number chosen, or -1 if none of the ports were available.
	 *   
	 */
	public int listen(List<Integer> portList) throws IOException {
		
		if (portList.isEmpty()){
			return -1;  
		} else {
			int i = 0;
			
			// Iterates over all possible ports in the list
			while (i < portList.size()) {
				if (portList.get(i) != -1){
					sSocket = new ServerSocket(portList.get(i));
					return portList.get(i);
				}
				i++;
			}
		}
		return -1;
	}

	
	/**
	 * Listen on an available port. 
	 * Any available port may be chosen.
	 * @return The port number chosen.
	 */
	public int listen() throws IOException {
		this.sSocket = new ServerSocket(0);
		return this.sSocket.getLocalPort();
	}


	/**
	 * 1. Start listening on an open port. Write {@link #LISTEN_MESSAGE} followed 
	 * by the port number (and a newline) to sysout.
	 * 	  If there's an IOException at this stage, exit the method.
	 * 
	 * 2. Run in a loop; 
	 * in each iteration of the loop, wait for a client to connect,
	 * then read a line of text from the client. If the text is {@link #BYE_MESSAGE}, 
	 * send {@link #BYE_MESSAGE} to the client and exit the loop. Otherwise, 
	 * send {@link #HELLO_MESSAGE} 
	 * to the client, followed by the string sent by the client (and a newline)
	 * After sending the hello message, close the client connection and wait for 
	 * the next client to connect.
	 * 
	 * If there's an IOException while in the loop, or if the client closes the 
	 * connection before sending a line of text,
	 * send the text {@link #ERR_MESSAGE} to sysout, but continue to the next 
	 * iteration of the loop.
	 * 
	 * *: in any case, before exiting the method you must close the server socket. 
	 *  
	 * @param sysout a {@link PrintStream} to which the console messages are sent.
	 * 
	 * 
	 */
	public void run(PrintStream sysout) {
		
		// Listening to port
		try {
			listen();
			if (this.sSocket.getLocalPort() != -1) {
				int myPort = sSocket.getLocalPort();
				sysout.println(LISTEN_MESSAGE + myPort);
				sysout.flush();
			}
		} catch (IOException e){
			sysout.println("Error creating a server socket");
			sysout.flush();
		}
		
		// Loop as explained above
		for(;;){
			Socket cSocket = null;
			
			try {
				cSocket = sSocket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader
						(cSocket.getInputStream()));
				PrintWriter out = new PrintWriter(cSocket.getOutputStream());
				String cMessage = in.readLine();
				
				if (cMessage.equals(BYE_MESSAGE)){
					out.println(BYE_MESSAGE);
					out.flush();
					cSocket.close();
					
					break;
				} else {
					out.println(HELLO_MESSAGE + cMessage);
					out.flush();
					cSocket.close();
				}
			} catch (IOException e) {
				sysout.println(ERR_MESSAGE);
				sysout.flush();
			}
		}
		try {
			sSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		HelloServer server = new HelloServer();
		server.run(System.err);
	}
}
