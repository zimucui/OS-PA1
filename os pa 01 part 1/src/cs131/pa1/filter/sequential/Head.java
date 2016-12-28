package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Head extends SequentialFilter {
	public int lines;
	public String fileName;
	
	public Head (int lines, String fileName) {
		this.lines = lines;
		this.fileName = fileName;
		this.input = new LinkedList<String>();
		this.output = new LinkedList<String>();
	}
	
	public Head (String fileName) {
		this.fileName = fileName;
		this.lines = 10;
		this.input = new LinkedList<String>();
		this.output = new LinkedList<String>();
		
	}
	
	public void process() {
		File file = new File(fileName);
		Scanner readLine = null;
		try {
			readLine = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!readLine.hasNextLine()) {
			output.add("");
		}
		if (lines == 10) {
			int count = 1;       //count lines
			while (readLine.hasNextLine() && (count <= 10) ) {
				output.add(readLine.nextLine());
				count ++;
			}
		} else {           //with specific line number
			int count = 1;       //count lines
			while (readLine.hasNextLine() && (count <= lines) ) {
				output.add(readLine.nextLine());
				count ++;
			}
		}
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	
	
	
	
}
