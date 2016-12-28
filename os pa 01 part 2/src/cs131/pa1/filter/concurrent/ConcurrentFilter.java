package cs131.pa1.filter.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cs131.pa1.filter.Filter;


public abstract class ConcurrentFilter extends Filter implements Runnable {
	
	protected BlockingQueue<String> input;
	protected BlockingQueue<String> output;
	
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof ConcurrentFilter){
			ConcurrentFilter concurrentNext = (ConcurrentFilter) nextFilter;
			this.next = concurrentNext;
			concurrentNext.prev = this;
			if (this.output == null){
				this.output = new LinkedBlockingQueue<String>();
			}
			concurrentNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}
	
	public void run(){
		while (true) {
			try {
				String commandLine = input.take();     //it is similar to input.poll(), but it is blockingQueue.
				if (commandLine.equals("ENDFLAG")) {
					if (output != null) {         //for Redirect
						output.put(commandLine);
					}
					if (this instanceof Redirect) {     //Only for Redirect
						((Redirect) this).closeWriter();
					}
					break;
				}
				String processedLine = processLine(commandLine);
				if (processedLine != null) {
					output.put(processedLine);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
	}
	
	
	//No need for isDone(). Because I have ENDFLAG	
	public boolean isDone() {
		return input.size() == 0;
	}
	
	protected abstract String processLine(String line);
	
}
