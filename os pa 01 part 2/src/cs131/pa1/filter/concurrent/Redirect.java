package cs131.pa1.filter.concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;

public class Redirect extends ConcurrentFilter {
	public String fileName;
	public String path;
	PrintWriter f;
	
	public Redirect (String absolutePath, String fileName) {
		this.fileName = fileName;
		this.path = absolutePath;
		this.input = new LinkedBlockingQueue<String>();   //no output QUEUE here
		try {
			f = new PrintWriter(new File(path + FILE_SEPARATOR + fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	protected String processLine(String line) {
		f.println(line);
		return null;
	}
	
	public void closeWriter() {
		f.close();
	}
}
