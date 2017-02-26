package testcases;

import static org.junit.Assert.*;
import org.junit.Test;

import SQLExpression.Expression;
import SQLParser.CalculationParser;

/**
 * this class is served as generating some test cases of the 
 * calculation parser. Notice for the format of the string, you
 * need to insert an empty space between each calculation tokens. 
 * @author messfish
 *
 */
public class CalculationTest {

	/** test with the calculation without the parenthesis. */
	@Test
	public void test1() {
		String cal = "A + B * C / 1.2 - 3.4";
		CalculationParser parse = new CalculationParser(cal.split("\\s+"));
		Expression express = parse.parse();
		String expected = "[-]" + "\n" +
		                  "-[+]" + "\n" +
				          "--[A]" + "\n" +
		                  "--[/]" + "\n" +
				          "---[*]" + "\n" +
		                  "----[B]" + "\n" +
				          "----[C]" + "\n" +
		                  "---[1.2]" + "\n" +
				          "-[3.4]" + "\n";
		StringBuilder sb = new StringBuilder();
		express.print("", sb);
		/* test whether the tree structure is the same. */
		assertEquals(sb.toString(), expected);
		StringBuilder sb2 = new StringBuilder();
		express.toString(sb2);
		sb2.deleteCharAt(sb2.length() - 1);
		/* test whether we could return the original calculation. */
		assertEquals(sb2.toString(), cal);
	}
	
	/** test with the calculation with the parenthesis. */
	@Test
	public void test2() {
		String cal = "( A * ( B - ( 3.1 - D ) ) / 4.0 )";
		CalculationParser parse = new CalculationParser(cal.split("\\s+"));
		Expression express = parse.parse();
		String expected = "[()]" + "\n" +
		                  "-[/]" + "\n" +
				          "--[*]" + "\n" +
		                  "---[A]" + "\n" +
				          "---[()]" + "\n" +
		                  "----[-]" + "\n" +
				          "-----[B]" + "\n" +
		                  "-----[()]" + "\n" +
				          "------[-]" + "\n" +
		                  "-------[3.1]" + "\n" +
				          "-------[D]" + "\n" +
		                  "--[4.0]" + "\n";
		StringBuilder sb = new StringBuilder();
		express.print("", sb);
		/* test whether the tree structures are the same. */
		assertEquals(sb.toString(), expected);
		StringBuilder sb2 = new StringBuilder();
		express.toString(sb2);
		sb2.deleteCharAt(sb2.length() - 1);
		/* test whether the string is returned to original. */
		assertEquals(sb2.toString(), cal);
	}
	
	/** test with the calculation with the negative number. */
	@Test
	public void test3() {
		String cal = "( - A ) * ( 4.5 + ( - 3.2 ) / B )";
		CalculationParser parse = new CalculationParser(cal.split("\\s+"));
		Expression express = parse.parse();
		String expected = "[*]" + "\n" +
		                  "-[()]" + "\n" +
				          "--[~]" + "\n" +
		                  "---[A]" + "\n" +
				          "-[()]" + "\n" +
				          "--[+]" + "\n" +
		                  "---[4.5]" + "\n" +
				          "---[/]" + "\n" +
		                  "----[()]" + "\n" +
				          "-----[~]" + "\n" +
		                  "------[3.2]" + "\n" +
				          "----[B]" + "\n";
		StringBuilder sb = new StringBuilder();
		express.print("", sb);
		/* test whether the tree structures are the same. */
		assertEquals(sb.toString(), expected);
		StringBuilder sb2 = new StringBuilder();
		express.toString(sb2);
		sb2.deleteCharAt(sb2.length() - 1);
		/* test whether the string is returned to original. */
		assertEquals(sb2.toString(), cal);
	}
	
}
