package files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Streams {
	/**
	 * Read from an InputStream until a quote character (") is found, then read
	 * until another quote character is found and return the bytes in between the two quotes. 
	 * If no quote character was found return null, if only one, return the bytes from the quote to the end of the stream.
	 * @param in
	 * @return A list containing the bytes between the first occurrence of a quote character and the second.
	 */
	public static List<Byte> getQuoted(InputStream in) throws IOException {
		int c = 0;
		
		// Creating a List of bytes
		List<Byte> bite = new ArrayList<Byte>();
		
		// Read the entire file
		 while ((c = in.read()) != -1 ){
			 
			 // Starts to add the bytes after the quotation
			 if (c == '"'){
				 
				 // Ensures it stops reading if we reach quotation or end
				 while ((c = in.read()) != -1 && (c != '"')){
					 Byte b = (byte)c;
					 bite.add(b);
				 }
				 in.close();
				 return bite;
			 }
		 }
		return null;
	}
	
	
	/**
	 * Read from the input until a specific string is read, return the string read up to (not including) the endMark.
	 * @param in the Reader to read from
	 * @param endMark the string indicating to stop reading. 
	 * @return The string read up to (not including) the endMark (if the endMark is not found, return up to the end of the stream).
	 */
	public static String readUntil(Reader in, String endMark) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		int c = 0;
		int markL = endMark.length();
		
		
		while (c != -1){
			
			// Reads the input (line by line) 
			// and creates a string with a string builder
			while (c != '\n' && c != -1){
				c = in.read();
				sb.append((char) c);
			}
			
			// Iterates over sb looking for a substring matching endMark
			for (int i = 0; i < sb.length() - markL; i++) {
				if (sb.substring(i, i + markL).equals(endMark)){
					return sb.substring(0, i);		
				}
			}
		}
		
		// Converts the string building into a string and returns it
		return sb.toString();
	}
	
	/**
	 * Copy bytes from input to output, ignoring all occurrences of badByte.
	 * @param in
	 * @param out
	 * @param badByte
	 */
	public static void filterOut(InputStream in, OutputStream out, byte badByte) throws IOException {
		
		int c = 0;
		
		// Reads until end of input stream
		while ((c = in.read()) != -1){
			
			// Writes all bytes that are not badByte
			if(c != badByte){
				out.write(c);
			}
		}
		in.close();
	}
	/**
	 * Read a 48-bit (unsigned) integer from the stream and return it. The number is represented as five bytes, 
	 * with the most-significant byte first. 
	 * If the stream ends before 5 bytes are read, return -1.
	 * @param in
	 * @return the number read from the stream
	 */
	public static long readNumber(InputStream in) throws IOException {
		
		int c = 0;
		long num = 0;
		int counter = 0;
		
		// Reads the number and shifts the bit to their right location (MSB to
		// the left) and increases counter
		while ((c = in.read()) != -1 && counter < 5){
			num = (num << 8) | c;
			counter++;
		}
		
		// Accounts for the stream being too short
		return (counter == 5) ? num : -1;
	}
}






