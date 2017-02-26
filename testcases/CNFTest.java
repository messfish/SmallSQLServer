package testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import SQLExpression.AndOperator;
import SQLExpression.ColumnNode;
import SQLExpression.DoubleValue;
import SQLExpression.Equals;
import SQLExpression.Expression;
import SQLExpression.GreaterThan;
import SQLExpression.GreaterThanOrEquals;
import SQLExpression.InOperator;
import SQLExpression.LessThan;
import SQLExpression.LessThanOrEquals;
import SQLExpression.LikeOperator;
import SQLExpression.MultiAndOperator;
import SQLExpression.MultiOrOperator;
import SQLExpression.NotEquals;
import SQLExpression.NotOperator;
import SQLExpression.OrOperator;
import SQLExpression.Parenthesis;
import SQLExpression.StringValue;
import SQLParser.CNFConverter;
import SQLParser.CheckEquals;

/**
 * this class is mainly used for testing whether we generate the 
 * correct CNF form of an expression tree. We use the name of 
 * variables that is reflected in the steps defined in the class.
 * @author messfish
 *
 */
public class CNFTest {

	private Expression original1, original2, original3;
	// these expressions stores the original expression tree.
	private Expression stepone1, stepone2, stepone3;
	// these expressions stores the step one of the expression tree.
	private Expression steptwo1, steptwo2, steptwo3;
	// these expression stores the step two of the expression tree.
	private Expression stepthree1, stepthree2, stepthree3;
	// these expression stores the step three of the expression tree.
	private Expression steplast1, steplast2, steplast3;
	// these expression stores the last step of the expression tree.
	private CNFConverter cnf1, cnf2, cnf3;
	// generate three CNF converter objects to handle the 
	// conversion of three expression trees.
	private CheckEquals check;
	
	/**
	 * Constructor: this constructor will be served to build several
	 * original construction tree and several expected trees that
	 * will be used in those test cases.
	 */
	public CNFTest() {
		/* Here is the original part of the expression tree. */
		buildOriginalExpression1();
		buildOriginalExpression2();
		buildOriginalExpression3();
		buildStepOneExpression1();
		buildStepOneExpression2();
		buildStepOneExpression3();
		buildStepTwoExpression1();
		buildStepTwoExpression2();
		buildStepTwoExpression3();
		buildStepThreeExpression1();
		buildStepThreeExpression2();
		buildStepThreeExpression3();
		buildStepLastExpression1();
		cnf1 = new CNFConverter();
		cnf2 = new CNFConverter();
		cnf3 = new CNFConverter();
		check = new CheckEquals();
	}
	
	@Test
	/**
	 * this method is used to check whether the checkEqual() method
	 * could return false if the two expression variables are pointing
	 * to the same expression tree.
	 */
	public void testStepZero() {
		assertFalse(check.checkEqual(original1, original1));
	}
	
	@Test
	/** 
	 * this method is used to check whether we clone the expression
	 * tree in the correct form that is specified in the clone class.
	 */
	public void testStepOne() {
		cnf1.reorder(original1);
		assertTrue(check.checkEqual(cnf1.getRoot(), stepone1));
		cnf2.reorder(original2);
		assertTrue(check.checkEqual(cnf2.getRoot(), stepone2));
		cnf3.reorder(original3);
		assertTrue(check.checkEqual(cnf3.getRoot(), stepone3));
	}
	
	@Test
	/**
	 * this method is used to check whether we push the not operators
	 * thoroughly. 
	 */
	public void testStepTwo() {
		cnf1.reorder(original1);
		cnf1.pushNotDown();
		assertTrue(check.checkEqual(cnf1.getRoot(), steptwo1));
		cnf2.reorder(original2);
		cnf2.pushNotDown();
		assertTrue(check.checkEqual(cnf2.getRoot(), steptwo2));
		cnf3.reorder(original3);
		cnf3.pushNotDown();
		assertTrue(check.checkEqual(cnf3.getRoot(), steptwo3));
	}
	
	@Test
	/**
	 * this method is used to check whether the expression tree is 
	 * reformed in the correct order.
	 */
	public void testStepThree() {
		cnf1.reorder(original1);
		cnf1.pushNotDown();
		cnf1.gather();
		assertTrue(check.checkEqual(cnf1.getRoot(), stepthree1));
		cnf2.reorder(original2);
		cnf2.pushNotDown();
		cnf2.gather();
		assertTrue(check.checkEqual(cnf2.getRoot(), stepthree2));
		cnf3.reorder(original3);
		cnf3.pushNotDown();
		cnf3.gather();
		assertTrue(check.checkEqual(cnf3.getRoot(), stepthree3));
	}

	@Test
	/**
	 * this method is used to check whether the expression tree is 
	 * converted into the CNF form.
	 */
	public void testLastStep() {
		cnf1.reorder(original1);
		cnf1.pushNotDown();
		cnf1.gather();
		cnf1.pushAndUp();
		assertTrue(check.checkEqual(cnf1.getRoot(), steplast1));
	}
	
	/**
	 * this method is used to build the original expression tree 1.
	 */
	private void buildOriginalExpression1() {
		Expression e1 = new DoubleValue(1.2);
		Expression e2 = new DoubleValue(2.3);
		Expression e3 = new DoubleValue(3.5);
		Expression e4 = new DoubleValue(4.6);
		Expression e5 = new DoubleValue(1.1);
		Expression e6 = new DoubleValue(2.5);
		Expression e7 = new DoubleValue(8.0);
		Expression e8 = new DoubleValue(7.2);
		Expression e9 = new LessThan(e1, e2);
		Expression e10 = new Equals(e3, e4);
		Expression e11 = new NotEquals(e5, e6);
		Expression e12 = new GreaterThan(e7, e8);
		Expression e13 = new OrOperator(e9, e10);
		Expression e14 = new OrOperator(e11, e12);
		Expression e15 = new AndOperator(e13, e14);
		Expression e16 = new Parenthesis(e15);
		original1 = new NotOperator(e16);
	}
	
	/**
	 * this method is used to build the original expression tree 2.
	 */
	private void buildOriginalExpression2() {
		Expression e1 = new DoubleValue(1.1);
		Expression e2 = new DoubleValue(2.3);
		Expression e3 = new DoubleValue(3.3);
		Expression e4 = new DoubleValue(4.5);
		Expression e5 = new ColumnNode("S.A");
		Expression e6 = new StringValue("\"%%%\"");
		Expression e7 = new ColumnNode("S.B");
		Expression e8 = new StringValue("orz");
		Expression e9 = new GreaterThanOrEquals(e1,e2);
		Expression e10 = new LessThanOrEquals(e3,e4);
		Expression e11 = new LikeOperator(e5,e6);
		Expression e12 = new InOperator(e7,e8);
		Expression e13 = new NotOperator(e9);
		Expression e14 = new OrOperator(e13,e10);
		Expression e15 = new NotOperator(e14);
		Expression e16 = new AndOperator(e11,e12);
		Expression e17 = new Parenthesis(e16);
		Expression e18 = new OrOperator(e15,e17);
		original2 = new Parenthesis(e18);
	}
	
	/**
	 * this method is used to build the original expression tree 3.
	 */
	private void buildOriginalExpression3() {
		Expression e1 = new DoubleValue(3.0);
		Expression e2 = new DoubleValue(4.0);
		Expression e3 = new DoubleValue(5.0);
		Expression e4 = new DoubleValue(6.0);
		Expression e5 = new DoubleValue(7.0);
		Expression e6 = new DoubleValue(8.0);
		Expression e7 = new DoubleValue(9.0);
		Expression e8 = new DoubleValue(10.0);
		Expression e9 = new DoubleValue(11.0);
		Expression e10 = new DoubleValue(12.0);
		Expression e11 = new DoubleValue(13.0);
		Expression e12 = new DoubleValue(14.0);
		Expression e13 = new DoubleValue(15.0);
		Expression e14 = new DoubleValue(16.0);
		Expression e15 = new DoubleValue(17.0);
		Expression e16 = new DoubleValue(18.0);
		Expression e17 = new DoubleValue(19.0);
		Expression e18 = new DoubleValue(20.0);
		Expression e19 = new GreaterThanOrEquals(e1, e2);
		Expression e20 = new LessThanOrEquals(e3, e4);
		Expression e21 = new LessThan(e5, e6);
		Expression e22 = new GreaterThan(e7, e8);
		Expression e23 = new Equals(e9, e10);
		Expression e24 = new NotEquals(e11, e12);
		Expression e25 = new LikeOperator(e13, e14);
		Expression e26 = new InOperator(e15, e16);
		Expression e27 = new GreaterThan(e17, e18);
		Expression e28 = new AndOperator(e19, e20);
		Expression e29 = new AndOperator(e21, e22);
		Expression e30 = new OrOperator(e26, e27);
		Expression e31 = new AndOperator(e29, e23);
		Expression e32 = new AndOperator(e25, e30);
		Expression e33 = new OrOperator(e24, e32);
		Expression e34 = new NotOperator(e33);
		Expression e35 = new OrOperator(e31, e34);
		Expression e36 = new Parenthesis(e28);
		Expression e37 = new Parenthesis(e35);
		original3 = new OrOperator(e36, e37);
	}
	
	/**
	 * this method is used to build the step one expression tree 1.
	 */
	private void buildStepOneExpression1() {
		Expression e1 = new DoubleValue(1.2);
		Expression e2 = new DoubleValue(2.3);
		Expression e3 = new DoubleValue(3.5);
		Expression e4 = new DoubleValue(4.6);
		Expression e5 = new DoubleValue(1.1);
		Expression e6 = new DoubleValue(2.5);
		Expression e7 = new DoubleValue(8.0);
		Expression e8 = new DoubleValue(7.2);
		Expression e9 = new LessThan(e1, e2);
		Expression e10 = new Equals(e3, e4);
		Expression e11 = new NotEquals(e5, e6);
		Expression e12 = new GreaterThan(e7, e8);
		List<Expression> list1 = new ArrayList<>();
		list1.add(e9);
		list1.add(e10);
		Expression e13 = new MultiOrOperator(list1);
		List<Expression> list2 = new ArrayList<>();
		list2.add(e11);
		list2.add(e12);
		Expression e14 = new MultiOrOperator(list2);
		List<Expression> list3 = new ArrayList<>();
		list3.add(e13);
		list3.add(e14);
		Expression e15 = new MultiAndOperator(list3);
		stepone1 = new NotOperator(e15);
	}
	
	/**
	 * this method is used to build the step one expression tree 2.
	 */
	private void buildStepOneExpression2() {
		Expression e1 = new DoubleValue(1.1);
		Expression e2 = new DoubleValue(2.3);
		Expression e3 = new DoubleValue(3.3);
		Expression e4 = new DoubleValue(4.5);
		Expression e5 = new ColumnNode("S.A");
		Expression e6 = new StringValue("\"%%%\"");
		Expression e7 = new ColumnNode("S.B");
		Expression e8 = new StringValue("orz");
		Expression e9 = new GreaterThanOrEquals(e1,e2);
		Expression e10 = new LessThanOrEquals(e3,e4);
		Expression e11 = new LikeOperator(e5,e6);
		Expression e12 = new InOperator(e7,e8);
		Expression e13 = new NotOperator(e9);
		List<Expression> list1 = new ArrayList<>();
		list1.add(e13);
		list1.add(e10);
		Expression e14 = new MultiOrOperator(list1);
		Expression e15 = new NotOperator(e14);
		List<Expression> list2 = new ArrayList<>();
		list2.add(e11);
		list2.add(e12);
		Expression e16 = new MultiAndOperator(list2);
		List<Expression> list3 = new ArrayList<>();
		list3.add(e15);
		list3.add(e16);
		stepone2 = new MultiOrOperator(list3);
	}
	
	/**
	 * this method is used to build the step one expression tree 3.
	 */
	private void buildStepOneExpression3() {
		Expression e1 = new DoubleValue(3.0);
		Expression e2 = new DoubleValue(4.0);
		Expression e3 = new DoubleValue(5.0);
		Expression e4 = new DoubleValue(6.0);
		Expression e5 = new DoubleValue(7.0);
		Expression e6 = new DoubleValue(8.0);
		Expression e7 = new DoubleValue(9.0);
		Expression e8 = new DoubleValue(10.0);
		Expression e9 = new DoubleValue(11.0);
		Expression e10 = new DoubleValue(12.0);
		Expression e11 = new DoubleValue(13.0);
		Expression e12 = new DoubleValue(14.0);
		Expression e13 = new DoubleValue(15.0);
		Expression e14 = new DoubleValue(16.0);
		Expression e15 = new DoubleValue(17.0);
		Expression e16 = new DoubleValue(18.0);
		Expression e17 = new DoubleValue(19.0);
		Expression e18 = new DoubleValue(20.0);
		Expression e19 = new GreaterThanOrEquals(e1, e2);
		Expression e20 = new LessThanOrEquals(e3, e4);
		Expression e21 = new LessThan(e5, e6);
		Expression e22 = new GreaterThan(e7, e8);
		Expression e23 = new Equals(e9, e10);
		Expression e24 = new NotEquals(e11, e12);
		Expression e25 = new LikeOperator(e13, e14);
		Expression e26 = new InOperator(e15, e16);
		Expression e27 = new GreaterThan(e17, e18);
		List<Expression> list1 = new ArrayList<>();
		list1.add(e19);
		list1.add(e20);
		Expression e28 = new MultiAndOperator(list1);
		List<Expression> list2 = new ArrayList<>();
		list2.add(e21);
		list2.add(e22);
		Expression e29 = new MultiAndOperator(list2);
		List<Expression> list3 = new ArrayList<>();
		list3.add(e26);
		list3.add(e27);
		Expression e30 = new MultiOrOperator(list3);
		List<Expression> list4 = new ArrayList<>();
		list4.add(e29);
		list4.add(e23);
		Expression e31 = new MultiAndOperator(list4);
		List<Expression> list5 = new ArrayList<>();
		list5.add(e25);
		list5.add(e30);
		Expression e32 = new MultiAndOperator(list5);
		List<Expression> list6 = new ArrayList<>();
		list6.add(e24);
		list6.add(e32);
		Expression e33 = new MultiOrOperator(list6);
		Expression e34 = new NotOperator(e33);
		List<Expression> list7 = new ArrayList<>();
		list7.add(e31);
		list7.add(e34);
		Expression e35 = new MultiOrOperator(list7);
		List<Expression> list8 = new ArrayList<>();
		list8.add(e28);
		list8.add(e35);
		stepone3 = new MultiOrOperator(list8);
	}
	
	/**
	 * this method is used to build the step two expression tree 1.
	 */
	private void buildStepTwoExpression1() {
		Expression e1 = new DoubleValue(1.2);
		Expression e2 = new DoubleValue(2.3);
		Expression e3 = new DoubleValue(3.5);
		Expression e4 = new DoubleValue(4.6);
		Expression e5 = new DoubleValue(1.1);
		Expression e6 = new DoubleValue(2.5);
		Expression e7 = new DoubleValue(8.0);
		Expression e8 = new DoubleValue(7.2);
		Expression e9 = new LessThan(e1, e2);
		Expression e10 = new Equals(e3, e4);
		Expression e11 = new NotEquals(e5, e6);
		Expression e12 = new GreaterThan(e7, e8);
		Expression e13 = new NotOperator(e9);
		Expression e14 = new NotOperator(e10);
		Expression e15 = new NotOperator(e11);
		Expression e16 = new NotOperator(e12);
		List<Expression> list1 = new ArrayList<>();
		list1.add(e13);
		list1.add(e14);
		Expression e17 = new MultiAndOperator(list1);
		List<Expression> list2 = new ArrayList<>();
		list2.add(e15);
		list2.add(e16);
		Expression e18 = new MultiAndOperator(list2);
		List<Expression> list3 = new ArrayList<>();
		list3.add(e17);
		list3.add(e18);
		steptwo1 = new MultiOrOperator(list3);
	}
	
	/**
	 * this method is used to build the step two expression tree 2.
	 */
	private void buildStepTwoExpression2() {
		Expression e1 = new DoubleValue(1.1);
		Expression e2 = new DoubleValue(2.3);
		Expression e3 = new DoubleValue(3.3);
		Expression e4 = new DoubleValue(4.5);
		Expression e5 = new ColumnNode("S.A");
		Expression e6 = new StringValue("\"%%%\"");
		Expression e7 = new ColumnNode("S.B");
		Expression e8 = new StringValue("orz");
		Expression e9 = new GreaterThanOrEquals(e1,e2);
		Expression e10 = new LessThanOrEquals(e3,e4);
		Expression e11 = new LikeOperator(e5,e6);
		Expression e12 = new InOperator(e7,e8);
		Expression e13 = new NotOperator(e10);
		List<Expression> list1 = new ArrayList<>();
		list1.add(e9);
		list1.add(e13);
		Expression e14 = new MultiAndOperator(list1);
		List<Expression> list2 = new ArrayList<>();
		list2.add(e11);
		list2.add(e12);
		Expression e15 = new MultiAndOperator(list2);
		List<Expression> list3 = new ArrayList<>();
		list3.add(e14);
		list3.add(e15);
		steptwo2 = new MultiOrOperator(list3);
	}
	
	/**
	 * this method is used to build the step two expression tree 3.
	 */
	private void buildStepTwoExpression3 () {
		Expression e1 = new DoubleValue(3.0);
		Expression e2 = new DoubleValue(4.0);
		Expression e3 = new DoubleValue(5.0);
		Expression e4 = new DoubleValue(6.0);
		Expression e5 = new DoubleValue(7.0);
		Expression e6 = new DoubleValue(8.0);
		Expression e7 = new DoubleValue(9.0);
		Expression e8 = new DoubleValue(10.0);
		Expression e9 = new DoubleValue(11.0);
		Expression e10 = new DoubleValue(12.0);
		Expression e11 = new DoubleValue(13.0);
		Expression e12 = new DoubleValue(14.0);
		Expression e13 = new DoubleValue(15.0);
		Expression e14 = new DoubleValue(16.0);
		Expression e15 = new DoubleValue(17.0);
		Expression e16 = new DoubleValue(18.0);
		Expression e17 = new DoubleValue(19.0);
		Expression e18 = new DoubleValue(20.0);
		Expression e19 = new GreaterThanOrEquals(e1, e2);
		Expression e20 = new LessThanOrEquals(e3, e4);
		Expression e21 = new LessThan(e5, e6);
		Expression e22 = new GreaterThan(e7, e8);
		Expression e23 = new Equals(e9, e10);
		Expression e24 = new NotEquals(e11, e12);
		Expression e25 = new LikeOperator(e13, e14);
		Expression e26 = new InOperator(e15, e16);
		Expression e27 = new GreaterThan(e17, e18);
		List<Expression> list1 = new ArrayList<>();
		list1.add(e19);
		list1.add(e20);
		Expression e28 = new MultiAndOperator(list1);
		List<Expression> list2 = new ArrayList<>();
		list2.add(e21);
		list2.add(e22);
		Expression e29 = new MultiAndOperator(list2);
		List<Expression> list3 = new ArrayList<>();
		list3.add(e29);
		list3.add(e23);
		Expression e30 = new MultiAndOperator(list3);
		Expression e31 = new NotOperator(e24);
		Expression e32 = new NotOperator(e25);
		Expression e37 = new NotOperator(e26);
		Expression e38 = new NotOperator(e27);
		List<Expression> list4 = new ArrayList<>();
		list4.add(e37);
		list4.add(e38);
		Expression e33 = new MultiAndOperator(list4);
		List<Expression> list5 = new ArrayList<>();
		list5.add(e32);
		list5.add(e33);
		Expression e34 = new MultiOrOperator(list5);
		List<Expression> list6 = new ArrayList<>();
		list6.add(e31);
		list6.add(e34);
		Expression e35 = new MultiAndOperator(list6);
		List<Expression> list7 = new ArrayList<>();
		list7.add(e30);
		list7.add(e35);
		Expression e36 = new MultiOrOperator(list7);
		List<Expression> list8 = new ArrayList<>();
		list8.add(e28);
		list8.add(e36);
		steptwo3 = new MultiOrOperator(list8);
	}
	
	/**
	 * this method is used to build the step three expression tree 1.
	 */
	private void buildStepThreeExpression1() {
		Expression e1 = new DoubleValue(1.2);
		Expression e2 = new DoubleValue(2.3);
		Expression e3 = new DoubleValue(3.5);
		Expression e4 = new DoubleValue(4.6);
		Expression e5 = new DoubleValue(1.1);
		Expression e6 = new DoubleValue(2.5);
		Expression e7 = new DoubleValue(8.0);
		Expression e8 = new DoubleValue(7.2);
		Expression e9 = new LessThan(e1, e2);
		Expression e10 = new Equals(e3, e4);
		Expression e11 = new NotEquals(e5, e6);
		Expression e12 = new GreaterThan(e7, e8);
		Expression e13 = new NotOperator(e9);
		Expression e14 = new NotOperator(e10);
		Expression e15 = new NotOperator(e11);
		Expression e16 = new NotOperator(e12);
		List<Expression> list1 = new ArrayList<>();
		list1.add(e13);
		list1.add(e14);
		Expression e17 = new MultiAndOperator(list1);
		List<Expression> list2 = new ArrayList<>();
		list2.add(e15);
		list2.add(e16);
		Expression e18 = new MultiAndOperator(list2);
		List<Expression> list3 = new ArrayList<>();
		list3.add(e17);
		list3.add(e18);
		stepthree1 = new MultiOrOperator(list3);
	}
	
	/**
	 * this method is used to build the step three expression tree 2.
	 */
	private void buildStepThreeExpression2() {
		Expression e1 = new DoubleValue(1.1);
		Expression e2 = new DoubleValue(2.3);
		Expression e3 = new DoubleValue(3.3);
		Expression e4 = new DoubleValue(4.5);
		Expression e5 = new ColumnNode("S.A");
		Expression e6 = new StringValue("\"%%%\"");
		Expression e7 = new ColumnNode("S.B");
		Expression e8 = new StringValue("orz");
		Expression e9 = new GreaterThanOrEquals(e1,e2);
		Expression e10 = new LessThanOrEquals(e3,e4);
		Expression e11 = new LikeOperator(e5,e6);
		Expression e12 = new InOperator(e7,e8);
		Expression e13 = new NotOperator(e10);
		List<Expression> list1 = new ArrayList<>();
		list1.add(e9);
		list1.add(e13);
		Expression e14 = new MultiAndOperator(list1);
		List<Expression> list2 = new ArrayList<>();
		list2.add(e11);
		list2.add(e12);
		Expression e15 = new MultiAndOperator(list2);
		List<Expression> list3 = new ArrayList<>();
		list3.add(e14);
		list3.add(e15);
		stepthree2 = new MultiOrOperator(list3);
	}
	
	/**
	 * this method is used to build the step three expression tree 3.
	 */
	private void buildStepThreeExpression3() {
		Expression e1 = new DoubleValue(3.0);
		Expression e2 = new DoubleValue(4.0);
		Expression e3 = new DoubleValue(5.0);
		Expression e4 = new DoubleValue(6.0);
		Expression e5 = new DoubleValue(7.0);
		Expression e6 = new DoubleValue(8.0);
		Expression e7 = new DoubleValue(9.0);
		Expression e8 = new DoubleValue(10.0);
		Expression e9 = new DoubleValue(11.0);
		Expression e10 = new DoubleValue(12.0);
		Expression e11 = new DoubleValue(13.0);
		Expression e12 = new DoubleValue(14.0);
		Expression e13 = new DoubleValue(15.0);
		Expression e14 = new DoubleValue(16.0);
		Expression e15 = new DoubleValue(17.0);
		Expression e16 = new DoubleValue(18.0);
		Expression e17 = new DoubleValue(19.0);
		Expression e18 = new DoubleValue(20.0);
		Expression e19 = new GreaterThanOrEquals(e1, e2);
		Expression e20 = new LessThanOrEquals(e3, e4);
		Expression e21 = new LessThan(e5, e6);
		Expression e22 = new GreaterThan(e7, e8);
		Expression e23 = new Equals(e9, e10);
		Expression e24 = new NotEquals(e11, e12);
		Expression e25 = new LikeOperator(e13, e14);
		Expression e26 = new InOperator(e15, e16);
		Expression e27 = new GreaterThan(e17, e18);
		List<Expression> list1 = new ArrayList<>();
		list1.add(e19);
		list1.add(e20);
		Expression e28 = new MultiAndOperator(list1);
		List<Expression> list2 = new ArrayList<>();
		list2.add(e21);
		list2.add(e22);
		list2.add(e23);
		Expression e29 = new MultiAndOperator(list2);
		Expression e30 = new NotOperator(e26);
		Expression e31 = new NotOperator(e27);
		List<Expression> list3 = new ArrayList<>();
		list3.add(e30);
		list3.add(e31);
		Expression e32 = new MultiAndOperator(list3);
		Expression e33 = new NotOperator(e25);
		List<Expression> list4 = new ArrayList<>();
		list4.add(e33);
		list4.add(e32);
		Expression e34 = new MultiOrOperator(list4);
		Expression e35 = new NotOperator(e24);
		List<Expression> list5 = new ArrayList<>();
		list5.add(e35);
		list5.add(e34);
		Expression e36 = new MultiAndOperator(list5);
		List<Expression> list6 = new ArrayList<>();
		list6.add(e28);
		list6.add(e29);
		list6.add(e36);
		stepthree3 = new MultiOrOperator(list6);
	}
	
	/**
	 * this method is used to build the last step expression tree.
	 */
	private void buildStepLastExpression1() {
		Expression e1 = new DoubleValue(1.2);
		Expression e2 = new DoubleValue(2.3);
		Expression e3 = new DoubleValue(1.1);
		Expression e4 = new DoubleValue(2.5);
		Expression e5 = new DoubleValue(1.2);
		Expression e6 = new DoubleValue(2.3);
		Expression e7 = new DoubleValue(8.0);
		Expression e8 = new DoubleValue(7.2);
		Expression e9 = new DoubleValue(3.5);
		Expression e10 = new DoubleValue(4.6);
		Expression e11 = new DoubleValue(1.1);
		Expression e12 = new DoubleValue(2.5);
		Expression e13 = new DoubleValue(3.5);
		Expression e14 = new DoubleValue(4.6);
		Expression e15 = new DoubleValue(8.0);
		Expression e16 = new DoubleValue(7.2);
		Expression e17 = new LessThan(e1, e2);
		Expression e18 = new NotEquals(e3, e4);
		Expression e19 = new LessThan(e5, e6);
		Expression e20 = new GreaterThan(e7, e8);
		Expression e21 = new Equals(e9, e10);
		Expression e22 = new NotEquals(e11, e12);
		Expression e23 = new Equals(e13, e14);
		Expression e24 = new GreaterThan(e15, e16);
		Expression e25 = new NotOperator(e17);
		Expression e26 = new NotOperator(e18);
		Expression e27 = new NotOperator(e19);
		Expression e28 = new NotOperator(e20);
		Expression e29 = new NotOperator(e21);
		Expression e30 = new NotOperator(e22);
		Expression e31 = new NotOperator(e23);
		Expression e32 = new NotOperator(e24);
		List<Expression> list1 = new ArrayList<>();
		list1.add(e25);
		list1.add(e26);
		Expression e33 = new MultiOrOperator(list1);
		List<Expression> list2 = new ArrayList<>();
		list2.add(e27);
		list2.add(e28);
		Expression e34 = new MultiOrOperator(list2);
		List<Expression> list3 = new ArrayList<>();
		list3.add(e29);
		list3.add(e30);
		Expression e35 = new MultiOrOperator(list3);
		List<Expression> list4 = new ArrayList<>();
		list4.add(e31);
		list4.add(e32);
		Expression e36 = new MultiOrOperator(list4);
		List<Expression> list5 = new ArrayList<>();
		list5.add(e33);
		list5.add(e34);
		list5.add(e35);
		list5.add(e36);
		steplast1 = new MultiAndOperator(list5);
	}
	
}
