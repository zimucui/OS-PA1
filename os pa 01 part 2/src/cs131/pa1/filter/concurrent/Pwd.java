package cs131.pa1.filter.concurrent;

import java.util.concurrent.LinkedBlockingQueue;

public class Pwd extends ConcurrentFilter{
	public String currentWorkingDirect;
	
	public Pwd (String absolutePath) {
		this.currentWorkingDirect = absolutePath;
		this.input = new LinkedBlockingQueue<String>();
		this.output = new LinkedBlockingQueue<String>();
	}
	
	public void run() {
		try {
			output.put(currentWorkingDirect);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
