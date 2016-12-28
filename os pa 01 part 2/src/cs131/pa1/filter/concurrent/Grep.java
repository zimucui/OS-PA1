package cs131.pa1.filter.concurrent;


import java.util.concurrent.LinkedBlockingQueue;

public class Grep extends ConcurrentFilter {
	public String keyWord;
	
	public Grep (String keyWord) {
		this.keyWord = keyWord;
		this.input = new LinkedBlockingQueue<String>();
		this.output = new LinkedBlockingQueue<String>();
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
