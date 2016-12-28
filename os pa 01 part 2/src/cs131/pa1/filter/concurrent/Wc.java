package cs131.pa1.filter.concurrent;

import java.util.concurrent.LinkedBlockingQueue;

public class Wc extends ConcurrentFilter {
	public int lines;
	public int words;
	public int chars;
	
	public Wc() {
		this.lines = 0;
		this.words = 0;
		this.chars = 0;
		this.input = new LinkedBlockingQueue<String>();
		this.output = new LinkedBlockingQueue<String>();
	}
	

	public void run() {
		while (true) {
			try {
				String commandLine = input.take();
				if (commandLine.equals("ENDFLAG")) {
					output.put(lines + " " + words + " " + chars);
					output.put(commandLine);
					break;
				}
				
				processLine(commandLine);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
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
		return null;
	}


}
