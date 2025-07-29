import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JavaTutor {
    
	public static Scanner scanner1 = new Scanner(System.in);
	public static String username;
	public static String password;
	public static User user = new User();
	public static Timestamp ts = new Timestamp();
		
	public static void main(String[] args) {

		// create a Timestamp object for log file entries
		
		
		boolean complete = false;
		boolean[] modcomplete = new boolean[4];
		for (int i = 1; i <= 4; i++)
			modcomplete[i-1] = false;
		String login = "false";
		
		// Flag used to signify user has not taken quiz yet
		final String NO_QUIZ = "00/00_00:00";
		// Variable for recording timestamp of quiz completions
		String quizDate = "";

		System.out.println("\nWelcome to the JAVA TUTORIAL program!\n");
		
		// create a User object with default constructor (default status of modules/quizzes)
		
		
		//validates user
		while (login.equals("false"))
		{
				//get username
				System.out.println("Please input your username or press only enter to exit\n");
				username = scanner1.nextLine();
				// call User mutator to store username in user object
				user.setUserID(username);
				//begins login
				login = loginProccess(username);
		}
		
		//begin modules
		
		while (login.equals("true") && complete == false)
		{
			//reads progress stored on file
			try
			{
				File fileObj = new File(username + ".txt");
				Scanner scannerObj = new Scanner(fileObj);
				//skips password line
				scannerObj.nextLine();
				
				for (int i = 1; i <= 4; i++)
				{
				// retrieve logfile entry with status info
				String line = scannerObj.nextLine();
				System.out.println(line);
				// split logfile string into separate tokens using whitespace as separator
				String[] tokens = line.split(" ");
				// tokens[0] = timestamp
				// tokens[1] = module name
				// tokens[2] = completion status (true or false)
				// tokens[3] = QUIZ_SCORE constant
				// tokens[4] = quiz result (0 to 100)
				// tokens[5] = QUIZ_DATE constant
				// tokens[6] = quiz date/time completion
				// call User mutator setQuizScore() to store quiz score in User object 
				user.setQuizScore(i, Integer.parseInt(tokens[4]));
				// call User mutator setQuizTimestamp() to store quiz timestamp in User object
				user.setQuizTimestamp(i, tokens[6]);
				// check module completion status extracted from logfile
				if (tokens[2].equals("true"))
					modcomplete[i-1] = true;
					
				}
				
				// close Scanner object
				scannerObj.close();
				
				// update logfile using Timestamp getTimestamp() method and User object data
				// via class accessors for status of modules and quiz results
				FileWriter fwriter = new FileWriter(username + ".txt");
				fwriter.write(password + "\n");
				for (int i = 1; i <= 4; i++)
				{
				fwriter.write(ts.getTimestamp() + " Module-" + i + " " + modcomplete[i-1]  + " QUIZ_SCORE " + 
						user.getQuizScore(i) + " QUIZ_DATE " + user.getQuizTimestamp(i) + "\n");
				}
				fwriter.close();
			}
			catch(IOException e)
			{
				System.out.println("An Exception occurred!");
			}
			
			// display menu header text
			System.out.println("Please select a Java Tutor Module or choose to exit");
			System.out.println("   MENU OPTION                      MODULE COMPLETED?");
			System.out.println("-----------------------------------------------------");
			
			for (int i = 1; i <= 4; i++)
			{
			// format output
			// get quiz timestamp via User object's getQuizTimestamp() accessor method
			quizDate = user.getQuizTimestamp(i);
			// if quizDate equals NO_QUIZ flag, display module results without quiz_score and quiz_date
			if (quizDate.equals(NO_QUIZ))
				System.out.println(i + ". Module " + i + " - Java Data Types      " + " COMPLETE = " + modcomplete[i-1]);
			// if quizDate NOT equals NO_QUIZ flag, display module results with quiz_score and quiz_date
			else
				System.out.println(i + ". Module " + i + " - Java Data Types      " + " COMPLETE = " + modcomplete[i-1]  + " --> QUIZ_SCORE = " + 
						user.getQuizScore(i) + "% QUIZ_DATE = " + user.getQuizTimestamp(i));
			}
			
			System.out.println("5. Exit");
			System.out.println("-----------------------------------------------------");

			int option;
			option = scanner1.nextInt();
			scanner1.nextLine();
			
			
			switch (option)
			{	
				case 1:
					// call displayModulule_1() method with USER object argument and result from return value
					modcomplete[0] = Module_DataTypes.displayModule_1(user);
					break;
				case 2:
					// call displayModulule_2() method with USER object argument and result from return value
					modcomplete[1] = Module_Loops.displayModule_2(user);
					break;
				case 3:
					// call displayModulule_3() method with USER object argument and result from return value
					modcomplete[2] = Module_FileOperations.displayModule_3(user);
					break;
				case 4:
					// call displayModulule_4() method with USER object argument and result from return value
					modcomplete[3] = Module_Classes.displayModule_4(user);
					break;										
				case 5:
					// set exit flag to TRUE based on user input
					login = "exit";
					System.out.println("Thank you for using the Java Tutorial program. Good bye . . . ");
					break;
				default:
					System.out.println("Invalid Option");
					break;
			}		
			try
			{
			//updates logfile after taking module quiz
			FileWriter fwriter = new FileWriter(username + ".txt");
			fwriter.write(password + "\n");
			for (int i = 1; i <= 4; i++)
			{
			fwriter.write(ts.getTimestamp() + " Module-" + i + " " + modcomplete[i-1]  + " QUIZ_SCORE " + 
					user.getQuizScore(i) + " QUIZ_DATE " + user.getQuizTimestamp(i) + "\n");
			}
			fwriter.close();
			}

			catch(IOException e)
			{
				System.out.println("An Exception occurred!");
			}
			
			
			if (modcomplete[0] == true && modcomplete[1] == true && modcomplete[2] == true && modcomplete[3] == true)
				{
				complete = true;
				System.out.println("You have completed all the modules!");
				System.out.println("Thank you for using the Java Tutorial program. Good bye . . . ");
				}
		}
		scanner1.close();
	}

	private static String loginProccess (String username)
	{
		if (!username.equals(""))
		{
			try
			{
				FileWriter fwriter = new FileWriter(username + ".txt",true);
				File fileObj = new File(username + ".txt");
				Scanner scannerObj = new Scanner(fileObj);
					
				if (scannerObj.hasNextLine())
				{
					//password already exists, logging in
					System.out.println("Please input your password");
					password = scannerObj.nextLine();
					// call User mutator to store password in user object
					user.setPassword(password);
					String enteredPassword = scanner1.nextLine();
		            //checks password
					if (enteredPassword.equals(password)) 
		            {
		                System.out.println("Login successful!");
		                scannerObj.close();
						fwriter.close();
		                return "true";
		            }
		            else 
		            {
		                System.out.println("Invalid password.");
		                scannerObj.close();
						fwriter.close();
		                return "false";
		            }
				}
				else
				{
					//creating new user
					scannerObj.close();
					fwriter.close();
					return newUser();
				}
			}
			catch(IOException e)
			{
				System.out.println("An Exception occurred!");
				return "false";
			}
		}
		else
			return "exit";
	}
	
	private static String newUser ()
	{
		try
		{
			FileWriter fwriter = new FileWriter(username + ".txt",true);
			File fileObj = new File(username + ".txt");
			Scanner scannerObj = new Scanner(fileObj);
			//password does not exist
			System.out.println("New User detected, please select a password: ");
			password = scanner1.nextLine();
			//creates new blank file for new user
			fwriter.write(password + "\n");
			for (int i = 1; i <= 4; i++)
                fwriter.write("0 Module-" + i + " false QUIZ_SCORE 0 QUIZ_DATE 00/00_00:00\n");							
			scannerObj.close();
			fwriter.close();
			return "true";
		}
		catch(IOException e)
		{
			System.out.println("An Exception occurred!");
			return "false";
		}
	}
	
	
}
