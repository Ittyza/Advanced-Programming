package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class smeh {
	
	public void netBaseConvert(int port, int inBase, int outBase)
		throws IOException {
			ServerSocket server = new ServerSocket(port);
			
			Socket giver = server.accept();
			Socket getter = server.accept();
			
			InputStream in = giver.getInputStream();
			OutputStream out = getter.getOutputStream();
			
			long result = 0;
			byte digit;
			
			for (int i = 0; i < inBase; i++) {
				
				digit = (byte) in.read();
				result += digit*Math.pow(inBase, i);
			}
			
			byte[] numArray = new byte[64];
			
			for (int i = 0; i < numArray.length; i++) {
				digit = (byte) (result % outBase);
				numArray[i] = digit;
				result /= outBase;
			}
			
			for (int i = 0; i < numArray.length; i++) {
				out.write(numArray[i]);
			}
			
			giver.close();
			getter.close();
			server.close();
	}
}

