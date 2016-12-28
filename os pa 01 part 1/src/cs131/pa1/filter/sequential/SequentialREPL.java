package cs131.pa1.filter.sequential;
import java.io.*;                      
import java.util.*;

public class SequentialREPL {	
	public static String absolutePath;
	public static void main(String[] args){
		final String FILE_SEPARATOR = System.getProperty("file.separator");
		absolutePath = System.getProperty("user.dir");   //return a string
		Scanner console = new Scanner(System.in);
		System.out.println("Welcome to the Unix-ish command line.");
		String result;       //determine whether goes back to the loop
		do {
			System.out.print("> ");
			String userCommand = console.nextLine();
			
			if (userCommand.equals("cd")) {
				result = "The command [" + userCommand + "] requires parameter(s).";         //test 19
				System.out.println(result);
			} else if (userCommand.contains("| cd")) {   				 //test 16
				int indexofCd = userCommand.lastIndexOf("cd");
				String wrongCd = userCommand.substring(indexofCd);
				result = "The command [" + wrongCd + "] cannot have an input.";
				System.out.println(result);
			} else if (userCommand.contains("cd") && userCommand.contains("|")) {   //test 18
				int indexofCd1 = userCommand.indexOf("cd");
				int indexofPipe = userCommand.indexOf("|") - 1;
				String wrongCd1 = userCommand.substring(indexofCd1, indexofPipe);
				result = "The command [" + wrongCd1 + "] cannot have an output.";
				System.out.println(result);
			} else if (userCommand.contains("cd") && userCommand.contains(" ")) {         //test 8       
				if (userCommand.equals(" cd ..")) {
					userCommand = userCommand.substring(1);           //test 46, get rid of blank space before cd ..
				}
				String[] cdContent = userCommand.split(" ");     
				if (cdContent[1].equals("..")) {
					if (absolutePath.length()>2) {
						int lastIndexSlash = absolutePath.lastIndexOf(FILE_SEPARATOR);
						absolutePath = absolutePath.substring(0, lastIndexSlash);
					} else {
						absolutePath = FILE_SEPARATOR;
					}
					result = "pass";
				} else if (cdContent[1].equals(".")) {
					result = "pass";       // do nothing special
				} else {          //when there is a text name, whether it exits or not
					absolutePath = absolutePath + FILE_SEPARATOR + cdContent[1];    //change to a new directory
					File f = new File(absolutePath);     //build a File that reads directory
					Boolean decide = f.isDirectory();
					if (decide == true) {
						result = "pass";     //it means pass the while loop test
					} else {
						result = "The directory specified by the command [cd " + cdContent[1] + "] was not found.";   //still pass the loop   
						int deleteIndex = absolutePath.lastIndexOf(FILE_SEPARATOR);
						absolutePath = absolutePath.substring(0,deleteIndex);   //change the absolute path back to the correct one.
						System.out.println(result);
					}
				}
			} else if (userCommand.equals("exit")) {
				result = "exit";
			} else {
				SequentialCommandBuilder userInfo = new SequentialCommandBuilder(userCommand, absolutePath);  //invoke commandBuilder
				result = userInfo.returnFinalResult();    
				if (!result.isEmpty()) {          //isEmpty is equal to ""
					System.out.println(result);
				}
			}
			
		} while (!result.equals("exit"));
		
		System.out.println("Thank you for using the Unix-ish command line. Goodbye!");
		
	}

}
