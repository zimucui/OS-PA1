package cs131.pa1.filter.sequential;

import java.util.LinkedList;

public class Pwd extends SequentialFilter{
	public String currentWorkingDirect;
	
	public Pwd (String absolutePath) {
		this.currentWorkingDirect = absolutePath;
		this.input = new LinkedList<String>();
		this.output = new LinkedList<String>();
	}
	
	public void process() {
		output.add(currentWorkingDirect);
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	
}
