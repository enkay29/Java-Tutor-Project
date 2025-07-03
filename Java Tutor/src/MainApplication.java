import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class MainApplication
{
	public static Scanner scanner1 = new Scanner(System.in);
	public static String username;
	public static String password;
	public static void main(String[] args)
	{
		boolean complete = false;
		boolean mod1complete = false;
		boolean mod2complete = false;
		boolean mod3complete = false;
		boolean mod4complete = false;
		boolean exit = false;
		boolean login = false;
		//validates user
		while (login == false && exit == false)
		{
				//get username
				System.out.println("Please input your username or press only enter to exit");
				username = scanner1.nextLine();
				if (!username.equals(""))
				{
					try
					{
						FileWriter fwriter = new FileWriter(username + ".txt",true);
						File fileObj = new File(username + ".txt");
						Scanner scannerObj = new Scanner(fileObj);
						
						if (scannerObj.hasNextLine())
						{
							//password already exists
							System.out.println("Please input your password");
							password = scannerObj.nextLine();
							if (scanner1.nextLine().equals(password))
							{
								login = true;
								System.out.println("Login successful");
							}
							else
								System.out.println("Invalid password");
						}
						else
						{
							try
							{
								//password does not exist
								System.out.println("New User detected, please select a password: ");
								password = scanner1.nextLine();
								fwriter.write(password + "\n");
								fwriter.write("mod1 " + mod1complete  + "\n");
								fwriter.write("mod2 " + mod2complete  + "\n");
								fwriter.write("mod3 " + mod3complete  + "\n");
								fwriter.write("mod4 " + mod4complete  + "\n");
								login = true;
							}
							catch(IOException e)
							{
								System.out.println("An Exception occurred!");
							}

						}
						scannerObj.close();
						fwriter.close();
					}
					catch(IOException e)
					{
						System.out.println("An Exception occurred!");
					}
				}
				else
					exit = true;
		}

		//begin modules
		
		while (login == true && exit == false && complete == false)
		{
			//reads progress stored on file
			try
			{
				File fileObj = new File(username + ".txt");
				Scanner scannerObj = new Scanner(fileObj);
				//skips password line
				scannerObj.nextLine();
				//mod1
				String line = scannerObj.nextLine();
				if (line.charAt(5) == 't')
					mod1complete = true;
				//mod2
				line = scannerObj.nextLine();
				if (line.charAt(5) == 't')
					mod2complete = true;
				//mod3
				line = scannerObj.nextLine();
				if (line.charAt(5) == 't')
					mod3complete = true;
				//mod4
				line = scannerObj.nextLine();
				if (line.charAt(5) == 't')
					mod4complete = true;
				scannerObj.close();
				
				//updates folder
				FileWriter fwriter = new FileWriter(username + ".txt");
				fwriter.write(password + "\n");
				fwriter.write("mod1 " + mod1complete  + "\n");
				fwriter.write("mod2 " + mod2complete  + "\n");
				fwriter.write("mod3 " + mod3complete  + "\n");
				fwriter.write("mod4 " + mod4complete  + "\n");

				fwriter.close();
			}
			catch(IOException e)
			{
				System.out.println("An Exception occurred!");
			}
			
			System.out.println("Please select a module or choose to exit");
			System.out.println("----------------------------------------");
			System.out.println("1. Module 1 - " + mod1complete);
			System.out.println("2. Module 2 - " + mod2complete);
			System.out.println("3. Module 3 - " + mod3complete);
			System.out.println("4. Module 4 - " + mod4complete);
			System.out.println("5. Exit");
			System.out.println("----------------------------------------");
			
			int option;
			option = scanner1.nextInt();
			scanner1.nextLine();
			
			
			switch (option)
			{	
				case 1:
					module1 mod1 = new module1();
					mod1complete = mod1.Start();
					break;
				case 2:
					module2 mod2 = new module2();
					mod2complete = mod2.Start();
					break;
				case 3:
					module3 mod3 = new module3();
					mod3complete = mod3.Start();
					break;
				case 4:
					module4 mod4 = new module4();
					mod4complete = mod4.Start();
					break;
				case 5:
					exit = true;
				default:
					System.out.println("Invalid Option");
					break;
			}
			System.out.println(username);
			
			
			
			if (mod1complete == true && mod2complete == true && mod3complete == true && mod4complete == true)
				{
				complete = true;
				System.out.println("You have completed all the modules.");
				
				}

		}
		scanner1.close();
	}
}

