package BPlusTree;

import java.nio.ByteBuffer;

/**
 * This class mainly used to describe the leaf node. Note besides the
 * key list. There is another list that stores the pointer of actual values. 
 * Since we allow duplicates here, it should be a list of lists.
 * The format of the pointer is an array with 2 elements, with the first one
 * being the pointer to the file channel and second one is the index of the
 * tuple in the single page. Both of them are represented as integers.
 * @author messfish
 *
 */
public class LeafNode extends Node {

	
	
	/**
	 * Constructor: this constructor extends the constructor from the Node
	 * class. The format of the leaf node should be as follows: the first
	 * one is the length of the table entries for a single key, Note it is 
	 * an integer so it would use 4 bytes. The second
	 * one is the actual key, followed by the list of table entries. 
	 * @param filelocation
	 */
	public LeafNode(String filelocation) {
		super(filelocation);
	}

	/**
	 * This method is used to write the content from the byte buffer into 
	 * the key list and the 
	 */
	@Override
	protected void assignData(ByteBuffer buffer) {
		
	}

}
