package testcases;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import PhysicalOperators.ScanOperator;
import SQLExpression.ColumnNode;
import SQLExpression.Expression;
import SmallSQLServer.Main;
import Sorting.CheckSort;
import Sorting.ExternalSort;
import Support.HumanToBinary;
import Support.RandomTable;

/**
 * This class is mainly used for checking whether we correctly
 * implement the sort method or not. Notice for each case, we should
 * test that for multiple times to make sure the sort method works.
 * @author messfish
 *
 */
public class SortCheck {

	/**
	 * This method is mainly used for testing a small amount of data.
	 * By small amount, that means it will not go through the second
	 * phase of the external sort.
	 */
	@Test
	public void testSmallCase() {
		Main.setTemp("/Users/messfish/Desktop/SQLdatabase/temp/");
		Main.setTest("/Users/messfish/Desktop/SQLdatabase/test/");
		for(int i=0;i<400;i++) {
			RandomTable random = new RandomTable(1000);
			File file = random.generate(1);
			HumanToBinary human = new HumanToBinary();
			File temp = human.convert(file, "Test1");
			ScanOperator scan = new ScanOperator(temp);
			List<Expression> list = new ArrayList<>();
			list.add(new ColumnNode("Test.Tb"));
			list.add(new ColumnNode("Test.Td"));
			ExternalSort ex = new ExternalSort(scan, list, 1);
			CheckSort check = new CheckSort(ex);
			assertTrue(check.checkValid());
			assertEquals(1000, check.getSize());
		}
	}
	
	/**
	 * This method is mainly used for testing a medium amount of data.
	 * By medium amount, that means it will only do the second phase
	 * for one time.
	 */
	@Test
	public void testMediumCase() {
		Main.setTemp("/Users/messfish/Desktop/SQLdatabase/temp/");
		Main.setTest("/Users/messfish/Desktop/SQLdatabase/test/");
		for(int i=0;i<400;i++){
			RandomTable random = new RandomTable(10000);
			File file = random.generate(2);
			HumanToBinary human = new HumanToBinary();
			File temp = human.convert(file, "Test2");
			ScanOperator scan = new ScanOperator(temp);
			List<Expression> list = new ArrayList<>();
			list.add(new ColumnNode("Test.Ta"));
			list.add(new ColumnNode("Test.Tc"));
			list.add(new ColumnNode("Test.Td"));
			ExternalSort ex = new ExternalSort(scan, list, 2);
			CheckSort check = new CheckSort(ex);
			assertTrue(check.checkValid());
			assertEquals(10000, check.getSize());
		}
	}
	
	/**
	 * This method is mainly used for testing a medium amount of data.
	 * By medium amount, that means it will do the second phase
	 * for multiple time.
	 */
	@Test
	public void testLargeCase() {
		Main.setTemp("/Users/messfish/Desktop/SQLdatabase/temp/");
		Main.setTest("/Users/messfish/Desktop/SQLdatabase/test/");
		for(int i=0;i<400;i++) {
			RandomTable random = new RandomTable(1000000);
			File file = random.generate(3);
			HumanToBinary human = new HumanToBinary();
			File temp = human.convert(file, "Test3");
			ScanOperator scan = new ScanOperator(temp);
			List<Expression> list = new ArrayList<>();
			list.add(new ColumnNode("Test.Tb"));
			list.add(new ColumnNode("Test.Td"));
			ExternalSort ex = new ExternalSort(scan, list, 3);
			CheckSort check = new CheckSort(ex);
			for(int j=1;j<=222;j++) {
				File filetemp = new File(Main.getTemp()+ "3 " + j);
				assertTrue(check.checkValid(filetemp));
			}
			assertTrue(check.checkValid());
			assertEquals(1000000, check.getSize());
		}
	}
	
}
