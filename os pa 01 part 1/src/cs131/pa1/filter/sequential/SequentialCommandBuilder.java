package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SequentialCommandBuilder {
	private String userInput;
	private String absolutePath;      
	
	public SequentialCommandBuilder(String userCommand, String absolutePath) {
		this.userInput = userCommand;
		this.absolutePath = absolutePath;
	}
	
	public String returnFinalResult(){
		String[] items = null;
		String returnResult = "";
		if (userInput.contains(" | ")) {
			items = userInput.split("\\| ");        //it splits | with a blank space
			String test = "";
			for (int i=0; i<items.length; i++) {
				test += items[i];
			}
			items = test.split(" ");              //now, every cell in array will not have space
		} else if (userInput.contains("|") && !userInput.contains(" | ")) {        //for test 30 where no blank space for "|"
			int index = userInput.indexOf("|");
			String before = userInput.substring(0, index);
			String after = userInput.substring(index+1);
			String finalString = before + " | " + after;
			items = finalString.split("\\| ");        //it splits | with a blank space
			String test = "";
			for (int i=0; i<items.length; i++) {
				test += items[i];
			}
			items = test.split(" ");
		}  else if (userInput.equals("pwd") || userInput.equals(" pwd") || userInput.equals(" pwd ")) {                     // test 46, get rid of blank space before pwd
			Pwd pwd = new Pwd(absolutePath);
			pwd.process();
			int size = pwd.output.size();
			returnResult = pwd.output.poll();
			for (int i = 1; i < size; i++) {            //if there is more than one element in the queue
				returnResult = returnResult + "\n" + pwd.output.poll();
			}
			
		} else if (userInput.equals("ls")) {
			Ls list = new Ls(absolutePath);
			list.process();
			int size = list.output.size();
			returnResult = list.output.poll();
			for (int i = 1; i < size; i++) {            //if there is more than one element in the queue
				returnResult = returnResult + "\n" + list.output.poll();
			}
			
		} else if (userInput.equals("head")) {      //test 21
			returnResult = "The command [" + userInput + "] requires parameter(s).";
		} else if (userInput.equals("wc")) {      //test 26
			returnResult = "The command [" + userInput + "] requires input.";
		} else if (userInput.contains(" ")) {              //split the array by blank space
			items = userInput.split(" ");
		} else {
			returnResult = "The command [" + userInput + "] was not recognized.";             //test 5 and test 7 and test 8
		}
		
		
		if (!returnResult.equals("")) {                //check to see if there is an error. If it does, quit.
			return returnResult;
		} else if (returnResult.equals("")) {         //check for the array for more errors
			for (int i=0; i<items.length; i++) {
				if (items[i].equals("head")) {
					if (i != 0) {
						String combination = items[i] + " " + items[i+1];
						returnResult = "The command [" + combination + "] cannot have an input.";      //test 20
						return returnResult;         //quit
					} else if (items[i+1].charAt(0) == '-') {             //head, followed by something
						int checknextnext = i + 2;
						if (checknextnext == items.length) {              //test 22
							String comb1 = items[i] + " " + items[i+1];
							returnResult = "The command [" + comb1 + "] requires parameter(s).";
							return returnResult;   //quit
						} else {
							String fakeint1 = items[i+1];
							Boolean check;
						    	try
						    	{
						    		Integer.parseInt(fakeint1);
						    		check = true;
						    	} catch (NumberFormatException ex)
						    	{
						    		check = false;
						    	}
						    	if (check == false) {           // see whether it is a number or not
						    		String comb = items[i] + " " + items[i+1] + " " + items[i+2];
						    		returnResult = "The parameter for command [" + comb + "] is invalid.";       //test 23
						    		return returnResult;            //quit
						    	} else {
						    		String newPath = absolutePath + "/" + items[i+2];
						    		File testPath = new File(newPath);                 //test 11
						    		Boolean passOrNot = testPath.isFile();
						    		if (passOrNot == false) {        //when items[i+3] is not a correct file
						    			returnResult = "At least one of the files in the command [" + items[i] + " " + items[i+1] + " " + items[i+2] + "] was not found.";
						    			return returnResult;  //quit
						    		} else {
						    			i = i + 2;             //pass all the test, and move i two steps.
						    		}
						    		
						    	}
						}
					} else {   //a file name directly follows head
						String newPath1 = absolutePath + "/" + items[i+1];     //test 12
						File testPath1 = new File(newPath1);
						Boolean passOrNot1 = testPath1.isFile();
			    		if (passOrNot1 == false) {  
			    			String wrongAnwser = items[i] + " " + items[i+1];
			    			returnResult = "At least one of the files in the command [" + wrongAnwser + "] was not found.";
			    			return returnResult;  //quit
			    		} else {
			    			i = i + 1;             //pass all the test, and move i one step.
			    		}
					}
				} else if (items[i].equals("grep")) {
					if (i == 0) {        //test 24
						String wrongGrep =  items[0] + " " + items[1];
						returnResult = "The command [" +  wrongGrep +  "] requires input.";
						return returnResult; //quit
					} else if ((i+1) == items.length) {    //test 25
						returnResult = "The command [" + items[i] +  "] requires parameter(s).";
						return returnResult; //quit
					} else {
						i = i + 1;         //pass all the test, and move i one step.
					}			
				} else if (items[i].equals("wc")) {
					if (i == 0) {      //test 26
						returnResult = "The command [" +  items[0] +  "] requires input.";
						return returnResult; //quit
					} else {
						     //pass all the test, do nothing, don't add i + 1
					}
				} else if (items[i].equals(">")) {
					if (i == 0) {             //test 27
						String wrongPipe = items[i] + " " + items[i+1];
						returnResult = "The command [" + wrongPipe + "] requires input.";
						return returnResult; //quit
					} else if ((i+1) == items.length) {           //test 28
						returnResult = "The command [" + items[i] + "] requires parameter(s).";
						return returnResult; //quit
					} else if ((i+2) < items.length) {                //test 30
						String wrongPipe1 = items[i] + " " + items[i+1];
						returnResult = "The command [" + wrongPipe1 + "] cannot have an output.";
						return returnResult; //quit
					} else {              //pass all the test, i = i+1
						i = i + 1;
					}
				} else if (items[i].equals("pwd")) {
					if (i != 0) {          				//test 14
						returnResult = "The command [" + items[i] + "] cannot have an input.";
						return returnResult; //quit
					} else {
						// pass all the test, do nothing, don't add i + 1.
					}
				} else if (items[i].equals("ls")) {
					if (i > 1) {         //test 15,  i=1 for test 10
						returnResult = "The command [" + items[i] + "] cannot have an input.";
						return returnResult; //quit
					} else {
						// pass all the test, do nothing, don't add i + 1.
					}
				} else if (items[i].equals("")) {         //for test 10
					i ++;
				} else {                  //spelling error check
					if ((i+1) == items.length) {
						returnResult = "The command [" + items[i] + "] was not recognized.";
						return returnResult;
					}
					String wrongSpelling = items[i] + " " + items[i+1];
					returnResult = "The command [" + wrongSpelling + "] was not recognized.";
					return returnResult; //quit
				}
			}
			
			//NOW, array is PERFECT!!!!!!!!!!!
			int count = 0;
			SequentialFilter temp = null;
			if (items[count].equals("head")) {
				if (items[count+1].charAt(0) == '-') {              
					int numberLines = Integer.parseInt(items[count+1]);
					int absoluteLines = Math.abs(numberLines);
					temp = new Head(absoluteLines, items[count+2]);          
					temp.process();
					count = count + 3;
				} else {
					temp = new Head(items[count+1]);
					temp.process();
					count = count + 2;
				}
			} else if (items[count].equals("ls")) {       //test 3
				temp = new Ls(absolutePath);            		
				temp.process();
				count = count + 1;
			}
			
			while ( count < items.length) {
				if (items[count].equals("grep")) {
					Grep grep = new Grep(items[count+1]);
					count = count + 2;
					temp.setNextFilter(grep);
					grep.process();
					temp = grep;          //change temp
				} else if (items[count].equals("wc")) {
					Wc wc = new Wc();
					count = count + 1;
					temp.setNextFilter(wc);
					wc.process();
					temp = wc;
				} else if (items[count].equals(">")) {
					Redirect redirect = new Redirect(absolutePath, items[count+1]);
					count = count + 2;
					temp.setNextFilter(redirect);
					redirect.process();
					temp = redirect;
				}
			}
			
			int size = temp.output.size();
			returnResult = temp.output.poll();
			for (int i = 1; i < size; i++) {            //if there is more than one element in the queue
				returnResult = returnResult + "\n" + temp.output.poll();
			}
		}
		if (returnResult == null)
			returnResult = "";
		return returnResult;

	}

}
