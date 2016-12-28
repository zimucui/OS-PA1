package cs131.pa1.filter.sequential;

import java.util.LinkedList;

public class Wc extends SequentialFilter {
	public int lines;
	public int words;
	public int chars;
	
	public Wc() {
		this.lines = 0;
		this.words = 0;
		this.chars = 0;
		this.input = new LinkedList<String>();
		this.output = new LinkedList<String>();
	}
	
	@Override
	protected String processLine(String line) {
		if (!line.isEmpty()) {        //in case of blank line input
			lines = lines + 1;   
			String numberOfLines[] = line.split(" ");
				for (int i=0; i<numberOfLines.length; i++) {
					words = words + 1;
				}
			//System.out.println("Line: "+line + " length: "+ line.length());
			chars += line.length();
		}
		
		if (isDone() == true) {           //every time input.poll(), size of input will decrease; when size = 0, return String
				return lines + " " + words + " " + chars;
		} else {
			return null;
		}
	}

}
