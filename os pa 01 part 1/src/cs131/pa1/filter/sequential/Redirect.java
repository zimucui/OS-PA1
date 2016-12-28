package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Redirect extends SequentialFilter {
	public String fileName;
	public String path;
	
	public Redirect (String absolutePath, String fileName) {
		this.fileName = fileName;
		this.path = absolutePath;
		this.input = new LinkedList<String>();
		this.output = new LinkedList<String>();
	}
	
	public void process(){
		try {
			PrintWriter file = new PrintWriter(new File(path + FILE_SEPARATOR + fileName));
			while (!input.isEmpty()) {
				String cell = input.poll();
				if ((cell != null) && (!cell.equals(fileName))) {
					file.println(cell);
				}
			}
			if (isDone() == true){
				file.close();     //close the PrintWriter
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	protected String processLine(String line)  {			
		return null;
	}
}
