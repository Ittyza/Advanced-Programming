package net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HelloClient {

	Socket clientSocket;

	public static final int COUNT = 10;

	/**
	 * Connect to a remote host using TCP/IP and set {@link #clientSocket} to be the
	 * resulting socket object.
	 * 
	 * @param host remote host to connect to.
	 * @param port remote port to connect to.
	 * @throws IOException
	 */
	public void connect(String host, int port) throws IOException {
		try{
			clientSocket = new Socket(host, port);
		} catch (IOException e) {
			clientSocket.close();
		}
	}

	/**
	 * Perform the following actions {@link #COUNT} times in a row: 1. Connect
	 * to the remote server (host:port). 2. Write the string in myname (followed
	 * by newline) to the server 3. Read one line of response from the server,
	 * write it to sysout (without the trailing newline) 4. Close the socket.
	 * 
	 * Then do the following (only once): 1. send
	 * {@link HelloServer#BYE_MESSAGE} to the server (followed by newline). 2.
	 * Read one line of response from the server, write it to sysout (without
	 * the trailing newline)
	 * 
	 * If there are any IO Errors during the execution, output {@link HelloServer#ERR_MESSAGE}
	 * (followed by newline) to sysout. If the error is inside the loop,
	 * continue to the next iteration of the loop. Otherwise exit the method.
	 * 
	 * @param sysout
	 * @param host
	 * @param port
	 * @param myname
	 */
	public void run(PrintStream sysout, String host, int port, String myname) {
		
		// The loop from above			
		try{
			for (int i = 0; i < COUNT; i++) {
				connect(host, port);
				PrintWriter out =
						new PrintWriter(clientSocket.getOutputStream());
				out.println(myname);
				out.flush();
				BufferedReader in = new BufferedReader
						(new InputStreamReader(clientSocket.getInputStream()));
				String sMessage = in.readLine();
				sysout.print(sMessage);
				sysout.flush();
				clientSocket.close();
			}	
		} catch (IOException e){
			sysout.println(HelloServer.ERR_MESSAGE);
			sysout.flush();
		}
		
		// The single iteration from above
		try{
			Socket cSocket = new Socket(host, port);
			PrintStream out = 
					new PrintStream(cSocket.getOutputStream());
			out.println(HelloServer.BYE_MESSAGE);
			out.flush();
			BufferedReader in = new BufferedReader
					(new InputStreamReader(clientSocket.getInputStream()));
			String sMessage = in.readLine();
			sysout.print(sMessage);
			cSocket.close();
			
		} catch(IOException e){
			sysout.println(HelloServer.ERR_MESSAGE);
			sysout.flush();
		}
	}
}
