package testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import SQLExpression.AddOperator;
import SQLExpression.AndOperator;
import SQLExpression.ColumnNode;
import SQLExpression.DoubleValue;
import SQLExpression.Expression;
import SQLExpression.GreaterThan;
import SQLExpression.GreaterThanOrEquals;
import SQLExpression.LessThan;
import SQLExpression.LongValue;
import SQLExpression.MultiplyOperator;
import SQLExpression.Parenthesis;
import SQLParser.CheckEquals;
import SQLParser.PlainSelect;
import TableElement.Table;

/**
 * this class serves as the full test of the SQL parser. In general
 * it will test all kinds of possible combination of the basic SQL
 * query. I will create a plain select query and fetch all the 
 * associated elements to check whether they matches the expectations.
 * Notice the input could only be a valid query. That means it should 
 * follow the format defined in the PlainSelect class.
 * @author messfish
 *
 */
public class SQLPsrserFullTest {

	/**
	 * this method tests the most fundamental query.
	 */
	@Test
	public void testBasic() {
		String query = "SELECT * FROM Sailors ;";
		PlainSelect plain = new PlainSelect(query);
		CheckEquals check = new CheckEquals();
		assertTrue(check.isEqual(plain.getSelectElements(), new ArrayList<>()));
		Map<String, Table> tablemap = new HashMap<>();
		tablemap.put("Sailors", new Table("Sailors"));
		assertTrue(check.checkEqual(plain.getFromList(), tablemap));
		assertTrue(check.checkEqual(null, plain.getWhereExpression()));
		assertTrue(check.checkEqual(plain.getGroupByElements(), new ArrayList<>()));
		assertTrue(check.checkEqual(null, plain.getHavingExpression()));
		assertTrue(check.isEqual(plain.getOrderByElements(), new ArrayList<>()));
	}
	
	/**
	 * this method tests the query that contains a where query.
	 */
	@Test
	public void testWhere() {
		String query = "SELECT Sailors.A FROM Sailors WHERE Sailors.B > 9000 ;";
		PlainSelect plain = new PlainSelect(query);
		CheckEquals check = new CheckEquals();
		List<Expression> list = new ArrayList<>();
		list.add(new ColumnNode("Sailors.A"));
		assertTrue(check.isEqual(plain.getSelectElements(), list));
		List<String> aliaslist = new ArrayList<>();
		aliaslist.add("");
		assertTrue(check.checkEqual(plain.getSelectAlias(), aliaslist));
		Map<String, Table> tablemap = new HashMap<>();
		tablemap.put("Sailors", new Table("Sailors"));
		assertTrue(check.checkEqual(plain.getFromList(), tablemap));
		Expression exp1 = new ColumnNode("Sailors.B");
		Expression exp2 = new LongValue(9000);
		Expression exp3 = new GreaterThan(exp1, exp2);
		assertTrue(check.checkEqual(plain.getWhereExpression(), exp3));
		assertTrue(check.checkEqual(plain.getGroupByElements(), new ArrayList<>()));
		assertTrue(check.checkEqual(null, plain.getHavingExpression()));
		assertTrue(check.isEqual(plain.getOrderByElements(), new ArrayList<>()));
	}
	
	/**
	 * this method tests the query that contains a group by query.
	 */
	@Test
	public void testGroupBy() {
		String query = "SELECT Sailors.A AS SB FROM Sailors GROUP BY Sailors.A ;";
		PlainSelect plain = new PlainSelect(query);
		CheckEquals check = new CheckEquals();
		List<Expression> list = new ArrayList<>();
		list.add(new ColumnNode("Sailors.A"));
		assertTrue(check.isEqual(plain.getSelectElements(), list));
		List<String> aliaslist = new ArrayList<>();
		aliaslist.add("SB");
		assertTrue(check.checkEqual(plain.getSelectAlias(), aliaslist));
		Map<String, Table> tablemap = new HashMap<>();
		tablemap.put("Sailors", new Table("Sailors"));
		assertTrue(check.checkEqual(plain.getFromList(), tablemap));
		assertTrue(check.checkEqual(null, plain.getWhereExpression()));
		List<String> list1 = new ArrayList<>();
		list1.add("Sailors.A");
		assertTrue(check.checkEqual(plain.getGroupByElements(), list1));
		assertTrue(check.checkEqual(null, plain.getHavingExpression()));
		assertTrue(check.isEqual(plain.getOrderByElements(), new ArrayList<>()));
	}
	
	/**
	 * this method tests the query that contains a having query.
	 */
	@Test
	public void testHaving() {
		String query = "SELECT S.A , COUNT ( DISTINCT * ) "
				+ "FROM Sailors AS S GROUP BY S.A "
				+ "HAVING MIN ( S.A ) >= 10.0 AND 100 < MAX ( S.A ) ;";
		PlainSelect plain = new PlainSelect(query);
		CheckEquals check = new CheckEquals();
		List<Expression> list = new ArrayList<>();
		list.add(new ColumnNode("S.A"));
		list.add(new ColumnNode("COUNT(DISTINCT*)"));
		assertTrue(check.isEqual(plain.getSelectElements(), list));
		List<String> aliaslist = new ArrayList<>();
		aliaslist.add("");
		aliaslist.add("");
		assertTrue(check.checkEqual(plain.getSelectAlias(), aliaslist));
		Map<String, Table> tablemap = new HashMap<>();
		tablemap.put("S", new Table("Sailors"));
		assertTrue(check.checkEqual(plain.getFromList(), tablemap));
		assertTrue(check.checkEqual(null, plain.getWhereExpression()));
		List<String> list1 = new ArrayList<>();
		list1.add("S.A");
		assertTrue(check.checkEqual(plain.getGroupByElements(), list1));
		Expression exp1 = new ColumnNode("MIN(S.A)");
		Expression exp2 = new DoubleValue(10.0);
		Expression exp3 = new LongValue(100);
		Expression exp4 = new ColumnNode("MAX(S.A)");
		Expression exp5 = new GreaterThanOrEquals(exp1, exp2);
		Expression exp6 = new LessThan(exp3, exp4);
		Expression exp7 = new AndOperator(exp5, exp6);
		assertTrue(check.checkEqual(plain.getHavingExpression(), exp7));
		assertTrue(check.isEqual(plain.getOrderByElements(), new ArrayList<>()));
	}
	
	/**
	 * this method that tests the query that contains the order by clause.
	 */
	@Test
	public void testOrderBy() {
		String query = "SELECT S.A * S.B AS SB "
				+ "FROM Sailors AS S "
				+ "ORDER BY S.A * ( S.C + 100 ) ;"; 
		PlainSelect plain = new PlainSelect(query);
		CheckEquals check = new CheckEquals();
		List<Expression> list = new ArrayList<>(); 
		Expression exp1 = new ColumnNode("S.A");
		Expression exp2 = new ColumnNode("S.B");
		Expression exp3 = new MultiplyOperator(exp1, exp2);
		list.add(exp3);
		assertTrue(check.isEqual(plain.getSelectElements(), list));
		List<String> list1 = new ArrayList<>();
		list1.add("SB");
		assertTrue(check.checkEqual(plain.getSelectAlias(), list1));
		Map<String, Table> tablemap = new HashMap<>();
		tablemap.put("S", new Table("Sailors"));
		assertTrue(check.checkEqual(plain.getFromList(), tablemap));
		assertTrue(check.checkEqual(null, plain.getWhereExpression()));
		assertTrue(check.checkEqual(plain.getGroupByElements(), new ArrayList<>()));
		assertTrue(check.checkEqual(null, plain.getHavingExpression()));
		Expression exp4 = new ColumnNode("S.C");
		Expression exp5 = new LongValue(100);
		Expression exp6 = new AddOperator(exp4, exp5);
		Expression exp7 = new Parenthesis(exp6);
		Expression exp8 = new ColumnNode("S.A");
		Expression exp9 = new MultiplyOperator(exp8, exp7);
		List<Expression> list2 = new ArrayList<>();
		list2.add(exp9);
		assertTrue(check.isEqual(plain.getOrderByElements(), list2));
	}
	
	/**
	 * This method is used to test complex query by having multiple
	 * elements in the query.
	 */
	@Test
	public void testCombinations1() {
		String query = "SELECT S.A , S.B , B.E "
				+ "FROM Boats AS B , Sailors AS S , Reserves "
				+ "WHERE Reserves.G LIKE \"%%_%%\" AND ";
				
	}

}
