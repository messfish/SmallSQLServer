package TableElement;

import java.io.File;

/**
 * As the name suggests, this class stores all kinds of data types
 * that could be available for the tuple. It could be a integer,
 * a double value or a string, or could even be a file which indicates
 * the result of a sub query.
 * @author messfish
 *
 */
public class DataType {

	private int type; // this mainly tells the type of the pointer.
	private long longdata;
	private double doubledata;
	private String stringdata;
	private File filedata;
	
	/**
	 * Constructor: this constructor is used to store a long value
	 * into the global variable. 
	 * @param longdata the long value from the parameter.
	 */
	public DataType(long longdata) {
		type = 1;
		this.longdata = longdata;
	}
	
	/**
	 * Constructor: this constructor is used to store a double value
	 * into the global variable. Notice the type could be either a 
	 * plain double value, a date number or a time number. So I just
	 * simply set it to 5. 
	 * @param doubledata the double value from the parameter.
	 */
	public DataType(double doubledata) {
		type = 5;
		this.doubledata = doubledata;
	}
	
	/**
	 * Constructor: this constructor is used to store a String value
	 * into the global variable.
	 * @param stringdata the string value from the parameter.
	 */
	public DataType(String stringdata) {
		type = 2;
		this.stringdata = stringdata;
	}
	
	/**
	 * Constructor: this constructor is used to store a File value
	 * into the global variable.
	 * @param filedata the file value from the parameter.
	 */
	public DataType(File filedata) {
		type = 3;
		this.filedata = filedata;
	}
	
	/**
	 * This method is the getter method of the long data.
	 * @return the long value.
	 */
	public long getLong() {
		return longdata;
	}
	
	/**
	 * This method is the getter method of the double data.
	 * @return the double value.
	 */
	public double getDouble() {
		return doubledata;
	}
	
	/**
	 * This method is the getter method of the string data.
	 * @return the string value.
	 */
	public String getString() {
		return stringdata;
	}
	
	/**
	 * This method is the getter method of the file data.
	 * @return the file value.
	 */
	public File getFile() {
		return filedata;
	}
	
	/**
	 * This method is the getter method of the type value.
	 * @return the type value.
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * this method is used to compare the two data types. note we 
	 * return -1 when this data type is smaller than that one. 1
	 * means this data type is larger than that one. 0 means these 
	 * two data types are literally equal.
	 * @param that another data type that needs to be compared.
	 * @return the integer indicates the relative greatness of the data type.
	 */
	public int compare(DataType that) throws IllegalArgumentException {
		if(this.type != that.type)
			throw new IllegalArgumentException
						("These two data types are not the same type!");
		if(this.type==1) {
			if(this.longdata < that.longdata) return -1;
			else if(this.longdata > that.longdata) return 1;
		}else if(this.type==5) {
			if(this.doubledata < that.doubledata) return -1;
			else if(this.doubledata > that.doubledata) return 1;
		}else if(this.type==2) 
			return this.stringdata.compareTo(that.stringdata);
		return 0;
	}
	
	/**
	 * This method is mainly for debugging: it will prints the data 
	 * as long as the data is not null.
	 */
	public void print() {
		if(type == 1) 
			System.out.println(longdata);
		if(type == 2)
			System.out.println(stringdata);
		if(type == 5)
			System.out.println(doubledata);
	}
	
}
