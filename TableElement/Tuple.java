package TableElement;

/**
 * This class is used to define a collection of data types and
 * present some methods and constructors for manipulating it.
 * @author messfish
 *
 */
public class Tuple {

	private DataType[] datalist; // this array represents a tuple.
	
	/**
	 * Constructor: this constructor is used to generate a data list
	 * which has the length given the argument.
	 * @param length the length of the data list.
	 */
	public Tuple(int length) {
		datalist = new DataType[length];
	}
	
	/**
	 * this method is the getter method of the data from the single index.
	 * @param index the index used to retrieve the data.
	 * @return the data at the given index.
	 */
	public DataType getData(int index) {
		return datalist[index];
	}
	
	/**
	 * This method is the setter method of the data list.
	 * @param index the index where we set the data.
	 * @param data the data that will be settled.
	 */
	public void setData(int index, DataType data) {
		datalist[index] = data;
	}
	
}
