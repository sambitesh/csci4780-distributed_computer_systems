package hadoop;

/**
 * Opens file and returns data
 * 
 * @author Vincent Lee
 * @author Will Henry
 * @since April 24, 2014
 * @version 1.0
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStream {
	private String line;
	private BufferedReader reader;
	
	/**
	 * Opens file stream
	 * @param filename
	 * @return if file can be opened
	 */
	public boolean openFile(String filename) {
		try {
			reader = new BufferedReader(new FileReader(filename));
			return true;
		} catch (Exception e) {
			System.out.println("error: openFile");
		}
		return false;
	}
	
	/**
	 * Checks if there is next line
	 * @return if line in file is not blank
	 */
	public boolean next() {
		try {
			//read line
			line = reader.readLine();
			
			//if no line
			if (line == null)
				return false;
			//if line is blank
			else {
				line = line.trim();
				return !line.equals("");
			}
		} catch (Exception e) {
			System.out.println("error: next");
		}
		return false;
	}
	
	/**
	 * Returns next line in String List
	 * @return String List
	 */
	public List<String> nextTuple() {
		try {
			List<String> tokens = new ArrayList<String>();
			
			Scanner tokenize = new Scanner(line);
			while (tokenize.hasNext())
				tokens.add(tokenize.next());
			tokenize.close();
			
			if (tokens.size() != 2) throw new Exception();
			
			return tokens;
		} catch (Exception e) {
			System.out.println("error: nextTuple");
			return null;
		}
	}
}
