package BPlusTree;

import java.nio.ByteBuffer;

import TableElement.DataType;

/**
 * This is the index node that stores the list of keys and a list of 
 * child entries. 
 * @author messfish
 *
 */
public class IndexNode extends Node {
	
	/**
	 * Constructor: this constructor extends the constructor from the 
	 * Node class. Note here is the format of the index node: Besides
	 * from the first two bytes specified in the node class, there is
	 * a number that tells how many keys are there in the key list, followed
	 * by the actual keys. Note for the String type, we need to attach one
	 * byte that indicates the length of the string. Also, for each page,
	 * the first four bytes indicates how many keys are there in the single
	 * page.
	 * @param filelocation the location of the file.
	 */
	public IndexNode(String filelocation) {
		super(filelocation);
	}

	/**
	 * This is the method that extends from the Node class: fetch the
	 * desired data out and 
	 */
	@Override
	protected void assignData(ByteBuffer buffer) {
		int start = 0;
		/* for the first page, there is a flag shows whether the node is
		 * index node or not, and an integer shows the type of the data.
		 * As a result, we should start from 8. */
		if(isFirst) start = 8;
		int numberofkeys = buffer.getInt(start);
		start += 4;
		for(int i=0;i<numberofkeys;i++) {
			int accumulates = 8;
			/* this indicates the data is a long integer. */
			if(datatype==1)
				keylist.add(new DataType(buffer.getLong(start)));
			/* this indicates the data is a double integer. */
			else if(datatype==2)
				keylist.add(new DataType(buffer.getDouble(start)));
			/* this indicates the data is a string. */
			else if(datatype==3) {
				int length = buffer.get(start);
				start++;
				StringBuilder sb = new StringBuilder();
				for(int j=0;j<length;j++) {
					sb.append((char)buffer.get(start));
					start++;
				}
				keylist.add(new DataType(sb.toString()));
				accumulates = 0;
			}
			start += accumulates;
		}
	}
	
}
