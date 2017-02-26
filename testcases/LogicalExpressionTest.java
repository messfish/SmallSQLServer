package testcases;

import static org.junit.Assert.*;

import org.junit.Test;

import SQLExpression.Expression;
import SQLParser.LogicalExpressionParser;

/**
 * This class handles the test cases of the having query language.
 * Note that for the format of the query, every element in the language
 * is seperated by a space except the aggregation which will be grouped
 * together. Notice I check the pre-order traverseal and the in order 
 * traversal of the tree to guarantee that we make the tree in the 
 * correct form.
 * @author messfish
 *
 */
public class LogicalExpressionTest {

	/** this case tests the sentence without parenthesis */
	@Test
	public void test1() {
		String exp = "COUNT(A) < 3.2 AND MIN(B) > 12.0 + C OR MAX(D) = 4.0";
		LogicalExpressionParser parse = 
				new LogicalExpressionParser(exp.split("\\s+"));
		Expression express = parse.parse();
		String expected = "[OR]" + "\n" +
		                  "-[AND]" + "\n" +
				          "--[<]" + "\n" +
		                  "---[COUNT(A)]" + "\n" +
				          "---[3.2]" + "\n" +
		                  "--[>]" + "\n" +
				          "---[MIN(B)]" + "\n" +
		                  "---[+]" + "\n" +
				          "----[12.0]" + "\n" +
		                  "----[C]" + "\n" +
				          "-[=]" + "\n" +
		                  "--[MAX(D)]" + "\n" +
				          "--[4.0]" + "\n";
		StringBuilder sb = new StringBuilder();
		express.print("", sb);
		/* test whether the tree structure is the same in pre-order from. */
		assertEquals(sb.toString(), expected);
		StringBuilder sb2 = new StringBuilder();
		express.toString(sb2);
		sb2.deleteCharAt(sb2.length() - 1);
		/* test whether the tree structure is the same in in-order form. */
		assertEquals(sb2.toString(), exp);
	}

	@Test
	/** This case tests the case with the parenthesis. */
	public void test2() {
		String exp = "MIN(A) <= 12.0 * ( 3.1 + 4.2 ) AND " +
					 "( MAX(B) >= 16.0 OR SUM(C) <> 12.0 / ( 6.0 - 4.0 ) )";
		LogicalExpressionParser parse = 
				new LogicalExpressionParser(exp.split("\\s+"));
		Expression express = parse.parse();
		String expected = "[AND]" +"\n" +
		                  "-[<=]" + "\n" +
				          "--[MIN(A)]" + "\n" +
		                  "--[*]" +"\n" +
				          "---[12.0]" + "\n" +
		                  "---[()]" + "\n" +
		                  "----[+]" + "\n" +
				          "-----[3.1]" + "\n" +
		                  "-----[4.2]" + "\n" +
				          "-[()]" + "\n" +
		                  "--[OR]" + "\n" +
				          "---[>=]" + "\n" +
		                  "----[MAX(B)]" + "\n" +
				          "----[16.0]" + "\n" +
		                  "---[<>]" + "\n" +
				          "----[SUM(C)]" + "\n" +
		                  "----[/]" + "\n" +
				          "-----[12.0]" + "\n" +
		                  "-----[()]" + "\n" +
		                  "------[-]" + "\n" +
				          "-------[6.0]" + "\n" +
		                  "-------[4.0]" + "\n";
		StringBuilder sb = new StringBuilder();
		express.print("", sb);
		/* test whether the tree structure is the same in pre-order form. */
		assertEquals(sb.toString(), expected);
		StringBuilder sb2 = new StringBuilder();
		express.toString(sb2);
		sb2.deleteCharAt(sb2.length() - 1);
		/* test whether the string is the same in in-order form. */
		assertEquals(sb2.toString(), exp);
	}
	
	@Test
	/** this method tests the case when the not operator exists. */
	public void test3() {
		String exp = "NOT AVG(A) < 10.0 OR NOT " +
					 "( AVG(D) > 4.3 AND ( SUM(B) = 10.2 OR" +
				     " MIN(C) < 10.0 / ( 1.3 + 3.7 ) ) )" +
					 " AND SUM(E) < 9.0";
		LogicalExpressionParser parse = 
				new LogicalExpressionParser(exp.split("\\s+"));
		Expression express = parse.parse();
		String expected = "[AND]" + "\n" +
						  "-[OR]" + "\n" +
		                  "--[NOT]" + "\n" +
				          "---[<]" + "\n" +
		                  "----[AVG(A)]" + "\n" +
				          "----[10.0]" + "\n" +
		                  "--[NOT]" + "\n" +
				          "---[()]" + "\n" +
		                  "----[AND]" + "\n" +
				          "-----[>]" + "\n" +
		                  "------[AVG(D)]" + "\n" +
				          "------[4.3]" + "\n" +
		                  "-----[()]" + "\n" +
				          "------[OR]" + "\n" +
		                  "-------[=]" + "\n" +
				          "--------[SUM(B)]" + "\n" +
		                  "--------[10.2]" + "\n" +
				          "-------[<]" + "\n" +
		                  "--------[MIN(C)]" + "\n" +
				          "--------[/]" + "\n" +
		                  "---------[10.0]" + "\n" +
				          "---------[()]" + "\n" +
				          "----------[+]" + "\n" +
		                  "-----------[1.3]" + "\n" +
				          "-----------[3.7]" + "\n" +
		                  "-[<]" + "\n" + 
				          "--[SUM(E)]" + "\n" +
		                  "--[9.0]" + "\n";
		StringBuilder sb = new StringBuilder();
		express.print("", sb);
		/* test whether the tree structure is the same in pre-order form. */
		assertEquals(sb.toString(), expected);
		StringBuilder sb2 = new StringBuilder();
		express.toString(sb2);
		sb2.deleteCharAt(sb2.length() - 1);
		/* test whether the string is the same in in order form. */
		assertEquals(sb2.toString(), exp);
	}
	
}

