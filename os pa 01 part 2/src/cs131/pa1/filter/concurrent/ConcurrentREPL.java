package cs131.pa1.filter.concurrent;
import java.io.*;                      
import java.util.*;

public class ConcurrentREPL {
	public static String absolutePath;
	public static List<Thread> isAlive  = new ArrayList<Thread>();
	public static void main(String[] args){
		final String FILE_SEPARATOR = System.getProperty("file.separator");
		absolutePath = System.getProperty("user.dir");   //return a string
		Scanner console = new Scanner(System.in);
		System.out.println("Welcome to the Unix-ish command line.");
		List<String> backGround = new ArrayList<String>();      //save background command lines;
		String result = "";       //determine whether goes back to the loop
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
			} else if (userCommand.contains("&")) {
				int index = userCommand.lastIndexOf("&")-1;      //omit the blank space
				userCommand = userCommand.substring(0, index);
				ConcurrentCommandBuilder userInfo = new ConcurrentCommandBuilder(userCommand, absolutePath, "&");//don't wait to get any return results.
				String Nouse = userInfo.returnFinalResult();   //we don't need to use this String to do anything. Just use returnFinalResult to build threads.
				userCommand = userCommand + " &";     //add "&" back to get the proper console output
				backGround.add(userCommand);
			} else if (userCommand.equals("repl_jobs")) {
			
					for (int i=0; i <backGround.size(); i ++) {             //if it is done
						if (isAlive.get(i).isAlive() == false) {
							String dump = backGround.remove(i);
							Thread dump2 = isAlive.remove(i);
							i --;
						}
					}				
				
				int numberTrace = 0;
				int arrayTrace = 0;
				while (numberTrace<backGround.size()) {
					if (isAlive.get(numberTrace).isAlive() == true)  {     //if it is still running
						int number = arrayTrace + 1;
						arrayTrace ++;
						result = result + "\t" + number + ". " + backGround.get(numberTrace) + "\n";	
					}
					numberTrace ++;
					
				}
				
				if (result.equals("")) {
					// do nothing, waiting for a new command line
				} else {
					System.out.print(result);
				}
				
			} else {
				ConcurrentCommandBuilder userInfo = new ConcurrentCommandBuilder(userCommand, absolutePath);  //invoke commandBuilder
				result = userInfo.returnFinalResult();    
				if (!result.isEmpty()) {          //isEmpty is equal to ""
					System.out.println(result);
				}
			}
			
		} while (!result.equals("exit"));
		
		System.out.println("Thank you for using the Unix-ish command line. Goodbye!");
		
	}

}
