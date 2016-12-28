package cs131.pa1.filter.sequential;

import java.util.LinkedList;

public class Grep extends SequentialFilter {
	public String keyWord;
	
	public Grep (String keyWord) {
		this.keyWord = keyWord;
		this.input = new LinkedList<String>();
		this.output = new LinkedList<String>();
	}
	
	@Override
	protected String processLine(String line) {
		if(line.contains(keyWord)){
			return line;
		} else {
			return null;
		}		
	}
}
