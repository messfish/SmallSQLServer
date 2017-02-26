package SQLExpression;

/**
 * This abstract class is the top level of this package in an expression tree.
 * @author messfish
 *
 */
public abstract class Expression {

	/**
	 * This is the abstract method for accepting visitor.
	 * @param expression the visitor that would be accepted.
	 */
	public abstract void accept(ExpressionVisitor expression);

	/**
	 * This is the abstract method for accepting visitor.
	 * @param expression the visitor that would be accepted.
	 */
	public abstract Expression accept(CloneExpressionVisitor expression);

	/**
	 * this is mainly used for debugging, it prints out a string to
	 * identify the type of the expression.
	 */
	public abstract String getString();
	
	/**
	 * This is mainly used for debugging: it will print the 
	 * structure of the expression in a tree structure. The 
	 * number of "-" indicates the level of the tree.
	 * @param s a list of '-' to indicate the level of the tree.
	 */
	public abstract void print(String s, StringBuilder sb);
	
	/**
	 * This is used to convert the tree structure into a string.
	 * perform the in order traversal of the tree. When we finish
	 * the traverse, the String will be stored in the string builder.
	 * @param sb the StringBuilder to store the target string.
	 */
	public abstract void toString(StringBuilder sb);
	
	/**
	 * this method is the method for accepting visitor: just calls the 
	 * visitor in the CloneExpressionVisitor Interface. The logic of the tree
	 * traverse will be handled by the class who implement that interface.
	 * @param expression the visitor to be accepted.
	 */
	public void accept(AbstractVisitor visitor) {
		visitor.visit(this);
	}
	
}
