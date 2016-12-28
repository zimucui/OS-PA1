package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.LinkedList;


public class Ls extends SequentialFilter{
	private String path;
	
	public Ls (String absolutePath) {
		this.path = absolutePath;
		this.input = new LinkedList<String>();
		this.output = new LinkedList<String>();
	}
	
	public void process() {
		File[] files =	new File (path).listFiles();
			for (int i =  0; i < files.length; i++) {
				String name = files[i].getName();
				this.output.add(name);
		}
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	
	
}
