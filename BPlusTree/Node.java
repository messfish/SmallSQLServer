package BPlusTree;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import TableElement.DataType;

/**
 * This class is the super class of both the index node and the leaf
 * node. Basically the difference between the leaf node and the index
 * node is the leaf node only contains a file that stores a list of 
 * key and the pointer to the tuple. The index node, on the other hand,
 * may have a file that contains the list of keys. In addition, it 
 * has a list of file directory that serves as the children of the 
 * node. For each page, it will be a binary file which has a storage
 * as 4KB. 
 * @author messfish
 *
 */
public abstract class Node {

	protected static final int NUM_OF_BYTES = 4096;
	protected boolean isLeafNode;
	protected List<DataType> keylist;
	protected int datatype;
	protected boolean isFirst = true;
	
	/**
	 * Constructor: this constructor takes the path of the file as the
	 * parameter. Note the single file in the path is always named as
	 * "keylist". The format of the key file is as follows: The first
	 * integer is a flag that indicates whether the node is a leaf node
	 * or an index node. Followed by that is the type of the data used
	 * for index. The rest of the content will be discussed in the
	 * index node and leaf node, respectively.
	 * @param filelocation the location of the file.
	 */
	public Node(String filelocation) {
		File file = new File(filelocation + "keylist");
		keylist = new ArrayList<>();
		try {
			FileInputStream in = new FileInputStream(file);
			FileChannel fc = in.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(NUM_OF_BYTES);
			int bufferlength = fc.read(buffer);
			isLeafNode = buffer.getInt(0) == 0;
			datatype = buffer.getInt(4);
			while(bufferlength!=-1) {
				assignData(buffer);
				buffer = ByteBuffer.allocate(NUM_OF_BYTES);
				isFirst = false;
				bufferlength = fc.read(buffer);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This class is used for assigning the data into the list of
	 * data type as lists. Since the format of the IndexNode and the format
	 * of the LeafNode is not the same, I make this abstract.
	 * @param buffer the byte bufffer that store the data.
	 */
	protected abstract void assignData(ByteBuffer buffer);
	
	/**
	 * This method is used to check whether there is an overflow in
	 * the B+ Tree. Basically it checks whether the order of the BPlusTree
	 * times by 2 is smaller than the size of the key list.
	 * @return the boolean value shows whether there is an overflow.
	 */
	public boolean isOverflowed() {
		return keylist.size() > 2 * BPlusTree.KEY_LIMITS;
	}
	
	/**
	 * This method is used to check whether there is an underflow in the 
	 * B+ Tree. Basically it checks whether the order of the BPlusTree
	 * is greater than the size of the key list. Note for the root, we
	 * should allow the existence of underflow.
	 * @return the boolean value shows whether there is an underflow.
	 */
	public boolean isUnderflowed() {
		return keylist.size() < BPlusTree.KEY_LIMITS;
	}
	
}
