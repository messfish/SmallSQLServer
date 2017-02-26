package Support;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import SmallSQLServer.Main;

/** 
 * This is the class that transforms the schema of the database.
 * Store them into the map structure mentioned in the global data definitions.
 * @author messfish
 *
 */
public class Catalog {

	private static final String base = "/db/data/";
	// this is the location of the data directory.
	private static final String schema = "/db/schema.txt";
	// this is the location of the schema file.
	private static Catalog instance = new Catalog(); // The object of type catalog.
	private Map<String, Map<String, Mule>> table_schema;
	// this variable uses the table name as the key and stores 
	// the schema in the map as the value.
	private Map<String, String> file_map;
	// this variable uses the table name as the key and stores
	// the file location of the table name as the value.
	
	
	/**
	 * This inner class stores the index of the schema and the 
	 * data type of the data as integers.
	 * @author messfish
	 *
	 */
	public class Mule{
		private int index;
		private int datatype;
		private Mule(int index, int datatype){
			this.index = index;
			this.datatype = datatype;
		}
	}
	
	/**
	 * Constructor: This constructor sets the schema map which will be
	 * used to get the index of a specific attribute and check whether
	 * the attribute is presented in the database by the SQLGrammar.
	 * I mark it private for security reasons. And use a public 
	 * function to retrieve the object for this class.
	 */
	private Catalog() {
		table_schema = new HashMap<>();
		file_map = new HashMap<>();
		try{
			FileReader fread = new FileReader(Main.getInput() + schema);
			BufferedReader buff = new BufferedReader(fread);
			String s = null;
			while((s = buff.readLine())!=null) {
				String[] str = s.split("\\s+");
				file_map.put(str[0], Main.getInput() + base + str[0]);
				Map<String, Mule> schema = new HashMap<>();
				for(int i=1;i<str.length;i+=2){
					String build = str[0] + "." + str[i];
					int type = Integer.parseInt(str[i + 1]);
					Mule mule = new Mule(i / 2, type);
					schema.put(build, mule);
				}
				table_schema.put(str[0], schema);
			}
			buff.close();
		}catch(IOException e) {
			System.out.print("The schema file is not find in the given"
					+ "directory, please check the directory.");
		}
	}
	
	/**
	 * this method returns the path of the file using the table name.
	 * @param s the table name.
	 * @return the file location. a null value if the table does not exist.
	 */
	public String getFileLocation(String s) {
		if(!file_map.containsKey(s)) return null;
		return file_map.get(s);
	}
	
	/**
	 * This method returns an object of type Catalog.
	 * @return the Catalog object instance.
	 */
	public static Catalog getInstance() {
		return instance;
	}
	
	/**
	 * This method returns the schema of the given table name.
	 * @param s the table name.
	 * @return the schema of the table, null if the table does not exist.
	 */
	public Map<String, Mule> getSchema(String s) {
		if(!table_schema.containsKey(s)) return null;
		return table_schema.get(s);
	}
	
	
	
}
