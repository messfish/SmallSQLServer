package testcases;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import SmallSQLServer.Main;
import Support.BinaryToHuman;
import Support.HumanToBinary;
import Support.RandomTable;

/**
 * Like the name suggests, this class is mainly used for
 * checking whether the conversion between the binary and
 * human readable is correct or not.
 * @author messfish
 *
 */
public class ConversionTest {

	@Test
	public void test() {
		Main.setTest("/Users/messfish/Desktop/SQLdatabase/test/");
		Main.setInput("/Users/messfish/Desktop/SQLdatabase/input/");
		RandomTable random = new RandomTable(10000);
		File file = random.generate(1);
		HumanToBinary human = new HumanToBinary();
		File temp = human.convert(file, "Test");
		BinaryToHuman binary = new BinaryToHuman();
		File result = binary.convert(temp, "Test"); 
	}

}