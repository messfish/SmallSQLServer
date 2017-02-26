package SQLParser;

import java.util.LinkedList;
import java.util.Queue;

import SQLExpression.AbstractVisitor;
import SQLExpression.BinaryExpression;
import SQLExpression.Expression;
import SQLExpression.MultipleExpression;
import SQLExpression.UnaryExpression;

/**
 * As the name suggests, this class helps check whether two trees
 * are identical. That means, they have the same structure. This is
 * mainly used for debugging copied trees to check whether there is
 * a variable that both tree shares.
 * @author messfish
 *
 */
public class CheckEquals implements AbstractVisitor {

	private boolean isFirst = true; 
	// use this variable to control which queue to push.
	// set true will add elements in queue1.
	// set false will add elements in queue2.
	private Queue<Expression> queue1 = new LinkedList<>();
	private Queue<Expression> queue2 = new LinkedList<>();
	// these two are the queues that handles the BFS.
	
	/**
	 * This method checks whether the two trees are equal. Use BFS
	 * to traverse through the tree and check whether they are the 
	 * same or they are the same object.
	 * @param e1 one of the expression tree
	 * @param e2 one of the expression tree
	 * @return the result that whether they are equal or not.
	 */
	public boolean checkEqual(Expression e1, Expression e2) {
		queue1.offer(e1);
		queue2.offer(e2);
		while(!queue1.isEmpty()&&!queue2.isEmpty()) {
			Expression dummy1 = queue1.poll();
			Expression dummy2 = queue2.poll();
			/* they refer to the same object, return false */
			if(dummy1.equals(dummy2)) return false;
			String str1 = dummy1.getString();
			String str2 = dummy2.getString();
			if(!str1.equals(str2)) return false;
			isFirst = true;
			dummy1.accept(this);
			isFirst = false;
			dummy2.accept(this);
		}
		/* if they are the same, there will be no elements left. */
		return queue1.isEmpty() && queue2.isEmpty();
	}

	/**
	 * this is the visit method of the expression class. Since only leaf 
	 * expressions will go into this category and there are no
	 * child in these expressions, there is no need to push 
	 * any children in it, simply return it.
	 * @param express the expression that will be visited. 
	 */
	@Override
	public void visit(Expression express) {	}

	/**
	 * this is the visit method of the binary expression class, push the
	 * left child in the queue first and the right child in the queue.
	 * @param express the expression that will be visited. 
	 */
	@Override
	public void visit(BinaryExpression express) {
		if(isFirst) {
			queue1.offer(express.getLeftChild());
			queue1.offer(express.getRightChild());
		}else {
			queue2.offer(express.getLeftChild());
			queue2.offer(express.getRightChild());
		}
	}

	/**
	 * this is the visit method of the unary expression class, push the
	 * only child into the queue.
	 * @param express the expression that will be visited. 
	 */
	@Override
	public void visit(UnaryExpression express) {
		if(isFirst) queue1.offer(express.getChild());
		else queue2.offer(express.getChild());
	}

	/**
	 * this is the visit method of the multiple expression class, push
	 * the children of the class from the left of the list to the right.
	 * @param express the expression that will be visited. 
	 */
	@Override
	public void visit(MultipleExpression express) {
		if(isFirst) {
			for(int i=0;i<express.size();i++)
				queue1.offer(express.getChild(i));
		}else {
			for(int i=0;i<express.size();i++)
				queue2.offer(express.getChild(i));
		}
	}
	
}
