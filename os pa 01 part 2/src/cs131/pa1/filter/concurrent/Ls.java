package cs131.pa1.filter.concurrent;

import java.io.File;

import java.util.concurrent.LinkedBlockingQueue;


public class Ls extends ConcurrentFilter {
	private String path;
	
	public Ls (String absolutePath) {
		this.path = absolutePath;
		this.input = new LinkedBlockingQueue<String>();
		this.output = new LinkedBlockingQueue<String>();
	}
	
	public void run() {
		File[] files =	new File (path).listFiles();
			for (int i =  0; i < files.length; i++) {
				String name = files[i].getName();
				try {
					output.put(name);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
