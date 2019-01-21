package files;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccess {
	
	/**
	 * Treat the file as an array of (unsigned) 8-bit values and sort them 
	 * in-place using a bubble-sort algorithm.
	 * You may not read the whole file into memory! 
	 * @param file
	 */
	public static void sortBytes(RandomAccessFile file) throws IOException {
		
		long numOfB = file.length();
		
		// The bubble sort iterators going through the file
		for (long i = 0; i < numOfB; i++) {
			for (long j = 0; j < numOfB - i; j++) {
				
				// Finding the correct j
				file.seek(j);
				int left = file.read();
				int right = file.read();
				
				// Sorting by overwriting the previous values
				if (left > right){ 
					file.seek(j);
					file.write(right);
					file.write(left);
				}
			}
		}
	}
	
	/**
	 * Treat the file as an array of unsigned 24-bit values (stored MSB first) and sort
	 * them in-place using a bubble-sort algorithm. 
	 * You may not read the whole file into memory! 
	 * @param file
	 * @throws IOException
	 */
	public static void sortTriBytes(RandomAccessFile file) throws IOException {
		
		long numOfB = file.length();
		
		// The bubble sort going through the file three bytes at a 
		// time
		for (int i = 0; i < numOfB - 4; i = i + 3) {
			for (int j = 0; j < numOfB - i - 4; j = j + 3) {
				
				// Finding the correct j
				file.seek(j);
				
				// Uses helper function to join 3 bytes into integers for the
				// sake of comparison
				int left = makeTriples(file, j);
				int right = makeTriples(file, j + 3);
				
				// Initiates sorting
				if (left > right){ 
					
					// Writes the 3 bytes in the correct order in their 
					// correct places by swapping left and right
					for (int k = 0; k < 3; k++) {
						file.seek(j + 3 + k);
						int c = file.readByte();
						file.seek(j + k);
						int d = file.read();
						file.seek(j + k);
						file.write(c);
						file.seek(j + 3 + k);
						file.write(d);
					}
					
				}
			}
		}

	}
	public static int makeTriples(RandomAccessFile file, int j) throws IOException {
		
		int count = 0;
		
		// Finding the index which we are going to connect together
		file.seek(j);

		int k = 0;
		
		// Connecting the bytes together and returning them as one
		while (count < 3){
			int c = file.read();
			k = (k << 8) | c;
			count++;
		}
		return k;
	}
	/**
	 * Treat the file as an array of unsigned 24-bit values (stored MSB first) and sort
	 * them in-place using a quicksort algorithm. 
	 * You may not read the whole file into memory! 
	 * @param file
	 * @throws IOException
	 */
	public static void sortTriBytesQuick(RandomAccessFile file) throws IOException {
		// TODO: implement
	}
}
