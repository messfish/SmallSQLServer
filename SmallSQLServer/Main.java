package SmallSQLServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is the top level of all the whole project.
 * That means, there will be a main function which runs the whole project.
 * Remember, the argument in the string is a file directory. 
 * In the file, there are three string serves as three file directories:
 * The first one is the input file directory.
 * The second one is the output file directory.
 * The third one is the temporary file directory.
 * 
 * @author messfish
 *
 */
public class Main {

	private static String input_dir;
	// this string is the input directory of the file system.
	private static String output_dir;
	// this string is the output directory of the file system.
	private static String temp_dir;
	// this string is the temporary file directory of the file system.
	
	/**
	 * this function is the getter method of the input directory string.
	 * @return the input directory.
	 */
	public static String getInput() {
		return input_dir;
	}
	
	/**
	 * this function is the getter method of the output directory string.
	 * @return the output directory.
	 */
	public static String getOutput() {
		return output_dir;
	}
	
	/**
	 * this function is the getter method of the temporary directory string.
	 * @return the temporary directory.
	 */
	public static String getTemp() {
		return temp_dir;
	}
	
	/**
	 * this is the main method that runs the whole project.
	 * First, it scans the file presented as strings in the argument.
	 * Second, it uses a scanner to take the input of the user as query.
	 * Third, it let the SQLGrammer check whether this is a valid query.
	 * If it is valid, parse the SQL query into a parse tree.
	 * Last, it runs the query and prints the result if this is 
	 * a read query, connected with the time that spend on this query.
	 * @param args the argument stores the formated file directory.
	 */
	public static void main(String[] args) {
		File file = new File(args[0]);
		boolean isValid = true;
		try {
			BufferedReader buff = new BufferedReader(new FileReader(file));
			input_dir = buff.readLine();
			output_dir = buff.readLine();
			temp_dir = buff.readLine();
			buff.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file is not avaiable!"
					+ " Please check whether you get the correct argument.");
			isValid = false;
		} catch (IOException e) {
			System.out.println("There is something wrong with your argument"
					+ " file, please check whether it is in the correct format.");
			isValid = false;
		}
		if(isValid) {
			System.out.println("Please Enter a query: ");
			Scanner scan = new Scanner(System.in);
			String query = "", str = null;
			while((str=scan.nextLine())!=null)
				query += str;
			scan.close();
		}
	}
	
}
