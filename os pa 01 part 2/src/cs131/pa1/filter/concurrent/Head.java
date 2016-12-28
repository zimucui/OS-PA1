package cs131.pa1.filter.concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class Head extends ConcurrentFilter {
	public int lines;
	public String fileName;
	
	public Head (int lines, String fileName) {
		this.lines = lines;
		this.fileName = fileName;
		this.input = new LinkedBlockingQueue<String>();
		this.output = new LinkedBlockingQueue<String>();
	}
	
	public Head (String fileName) {
		this.fileName = fileName;
		this.lines = 10;
		this.input = new LinkedBlockingQueue<String>();
		this.output = new LinkedBlockingQueue<String>();
		
	}
	
	public void run() {
		File file = new File(fileName);
		Scanner readLine = null;
		try {
			readLine = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!readLine.hasNextLine()) {
			try {
				output.put("");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (lines == 10) {
			int count = 1;       //count lines
			while (readLine.hasNextLine() && (count <= 10) ) {
				try {
					output.put(readLine.nextLine());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count ++;
			}
		} else {           //with specific line number
			int count = 1;       //count lines
			while (readLine.hasNextLine() && (count <= lines) ) {
				try {
					output.put(readLine.nextLine());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count ++;
			}
			
		}
		
		readLine.close();      //close the file
		
		// When done, put ENDFLAG at the end
		try {
			output.put("ENDFLAG");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	
	
	
	
}
