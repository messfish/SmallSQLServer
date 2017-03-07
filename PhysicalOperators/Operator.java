package PhysicalOperators;

import TableElement.Tuple;

/**
 * This class is the top level of the whole operator class.
 * Notice there are two main abstract methods that needs to be
 * implemented and they are the fundamentals of the database methods:
 * The first one is the getNextTuple() and the second one is 
 * reset(). 
 * @author messfish
 *
 */
public abstract class Operator {

	/**
	 * This abstract method is used to get the next valid tuple 
	 * from the table.
	 * @return the next valid tuple.
	 */
	public abstract Tuple getNextTuple();
	
	/**
	 * this abstract method is used to reset the pointer to the
	 * starting point of the table.
	 */
	public abstract void reset();
	
}
