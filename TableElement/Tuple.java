package TableElement;

/**
 * This class is used to define a collection of data types and
 * present some methods and constructors for manipulating it.
 * @author messfish
 *
 */
public class Tuple {

	private DataType[] datalist; // this array represents a tuple.
	private long[] tupleIDlist; // this array stores a list of tuple ID.
	
	/**
	 * Constructor: this constructor is used to generate a data list
	 * which has the length given the argument.
	 * @param length the length of the data list.
	 */
	public Tuple(int length, int size) {
		datalist = new DataType[length];
		tupleIDlist = new long[size];
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
	
	/**
	 * this method is the getter method of the tupleID from the single index.
	 * @param index the index used to retrieve the tupleID.
	 * @return the tupleID at the given index.
	 */
	public long getTupleID(int index) {
		return tupleIDlist[index];
	}
	
	/**
	 * This method is the setter method of the tupleID list.
	 * @param index the index where we set the tupleID.
	 * @param tupleID the tupleID that will be settled.
	 */
	public void setTupleID(int index, long tupleID) {
		tupleIDlist[index] = tupleID;
	}
	
	/**
	 * this method returns the length of the data list.
	 * @return the length of the data list.
	 */
	public int datasize() {
		return datalist.length;
	}
	
	/**
	 * This method is used to combine multiple tuples into one ID.
	 * @param tupleID the tupleID needs to be assigned.
	 */
	public void resetTupleID(long tupleID) {
		tupleIDlist = new long[1];
		tupleIDlist[0] = tupleID;
	}
	
}
