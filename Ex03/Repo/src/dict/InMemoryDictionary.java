package dict;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;



/**
 * Implements a persistent dictionary that can be held entirely in memory.
 * When flushed, it writes the entire dictionary back to a file.
 * 
 * The file format has one keyword per line:
 * <pre>word:def1:def2:def3,...</pre>
 * 
 * Note that an empty definition list is allowed (in which case the entry would have the form: <pre>word:</pre> 
 * 
 * @author talm
 *
 */
public class InMemoryDictionary extends TreeMap<String,String> implements PersistentDictionary{
	private static final long serialVersionUID = 1L; // (because we're extending a serializable class)
	
	private File dictFile;
	
	public InMemoryDictionary(File dictFile ) {
		this.dictFile = dictFile;
	}
	
	@Override
	public void open() throws IOException {
		
		// Creates a file if one does not exist
		if (!dictFile.exists()){
			dictFile.createNewFile();
		}
			BufferedReader br = new BufferedReader(new FileReader(dictFile));	
			
			String line = br.readLine();
			
			// Reads a line and sets the keys and values to the TreeMap
			while (line != null){
				int separator = line.indexOf(':');
				String word = line.substring(0, separator);
				String def =  line.substring(separator + 1);
				put(word, def);
				line = br.readLine();
			}
			br.close();
	}
	

	@Override
	public void close() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(dictFile));
		
		// Writes the keys and values in the file
		for (Map.Entry<String, String> mapper : entrySet()) {
			bw.write(mapper.getKey() + ":" + mapper.getValue() + "\n");
		
		}
		bw.close();
	}
}
