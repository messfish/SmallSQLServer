package TableElement;

import SQLParser.PlainSelect;

/**
 * This is the class that handles the table. The table
 * could either be atomic (a specific table) or a nested query.
 * (That means there will be a plain select in there).
 * @author messfish
 *
 */
public class Table {
	
	private PlainSelect select;
	// this variable stores the table which is a select query.
	private String atom;
	// this variable stores the table which is a table name.
	
	/**
	 * Constructor: this constructor handles the table which
	 * is a select query. 
	 * @param select a table as a select query
	 */
	public Table(PlainSelect select) {
		this.select = select;
	}
	
	/**
	 * Constructor: This constructor handles the table which is
	 * a table name.
	 * @param atom a table name
	 */
	public Table(String atom) {
		this.atom = atom;
	}

}
