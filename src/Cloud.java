///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 1
// Files:            Cloud.java User.java Machine,java DLinkedList.java
//					 InsufficientCreditException,java DblListnode.java
//					 ListADT,java
// Semester:         CS367 Summer 2017
//
// Author:           Manish Dhungana
// Email:            mdhungana@wisc.edu
// CS Login:         dhungana
// Lecturer's Name:  Meena Syamkumar
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Jack Cerhan
// Email:            jcerhan@wisc.edu
// CS Login:         cerhan
// Lecturer's Name:  Meena Syamkumar
// Lab Section:      
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
// 
// Online sources:   StackOverflow
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class which simulates the cloud environment.
 */
public class Cloud {

	//store record of users and machines
	private static ListADT<Machine> machines = new DLinkedList<Machine>();
	private static ListADT<User> users = new DLinkedList<User>();
	private static User currentUser = null; //current user logged in

	//scanner for console input
	public static final Scanner stdin = new Scanner(System.in);

	//main method
	public static void main(String args[]) {

		//populate the two lists using the input files: Machines.txt User1.txt 
		//User2.txt ... UserN.txt
		if (args.length < 2) {
			System.out.println("Usage: java Cloud [MACHINE_FILE] [USER1_FILE]"
					+ "[USER2_FILE] ...");
			System.exit(0);
		}

		//load store machines
		loadMachines(args[0]);

		//load users one file at a time
		for(int i = 1; i< args.length; i++) {
			loadUser(args[i]);
		}

		//user Input for login
		boolean done = false;
		while (!done) 
		{
			System.out.print("Enter username : ");
			String username = stdin.nextLine();
			System.out.print("Enter password : ");
			String passwd = stdin.nextLine();

			if(login(username, passwd) != null)
			{
				//generate random items in stock based on this user's machine
				//list
				ListADT<Machine> inStock = currentUser.generateMachineStock();
				//show user menu
				userMenu(inStock);
			}
			else
				//error message
				System.out.println("Incorrect username or password");

			System.out.println("Enter 'exit' to exit program or anything else"
					+ "to go back to login");
			if(stdin.nextLine().equals("exit"))
				done = true;
		}
	}

	/**
	 * Tries to login for the given credentials. Updates the currentUser if 
	 * successful login
	 * 
	 * @param username name of user
	 * @param passwd password of user
	 * @returns the currentUser 
	 */
	public static User login(String username, String passwd) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).checkLogin(username, passwd)){
				currentUser = users.get(i); //updates current user
				return users.get(i);
			}
		}
		return null;
	}

	/**
	 * Reads the specified file to create and load machines into the cloud. 
	 * Every line in the file has the format: 
	 * <MACHINE NAME>#<NUMBER OF CPUs>#<MEMORY SIZE>#<DISK SIZE>#<PRICE> 
	 * Create new machines based on the attributes specified in each line and
	 * insert them into the machines list.
	 * Order of machines list should be the same as the machines in the file.
	 * For any problem in reading the file print: 'Error: Cannot access file'
	 * 
	 * @param fileName name of the file to read
	 */
	public static void loadMachines(String fileName) {
		try {
			File machineFile = new File(fileName);
			Scanner mFileReader = new Scanner(machineFile); //scanner to read
			                                                //machines.txt input
			try {
				//holds machines file input
				String[] mFileInput;
				
				while (mFileReader.hasNextLine()) {
					//creates and adds Machines based on file input
					mFileInput = mFileReader.nextLine().split("#");
					machines.add(new Machine(mFileInput[0],
							Integer.parseInt(mFileInput[1]), 
							Integer.parseInt(mFileInput[2]),
							Integer.parseInt(mFileInput[3]),
							Double.parseDouble(mFileInput[4])));
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Cannot access file");

			} finally {
				mFileReader.close(); //ends machines.txt scanner
			}
		} catch (IOException e) {
			System.out.println("Error: Cannot access file");
		}
	}
	/**
	 * Reads the specified file to create and load a user into the cloud. 
	 * The first line in the file has the format:<NAME>#<PASSWORD>#<CREDIT> 
	 * Every other line after that is the same format as the machines file:
	 * <MACHINE NAME>#<NUMBER OF CPUs>#<MEMORY SIZE>#<DISK SIZE>#<PRICE> 
	 * For any problem in reading the file print: 'Error: Cannot access file'
	 * 
	 * @param fileName name of the file to read
	 */
	public static void loadUser(String fileName) {
		try {
			File userFile = new File(fileName);
			Scanner uFileReader = new Scanner(userFile); //scanner to read
			                                             //user<#>.txt input
			try {
				//holds line of file input
				String[] uFileInput;
				uFileInput = uFileReader.nextLine().split("#");
				
				//creates and adds user based on first line of file input
				users.add(new User(uFileInput[0], uFileInput[1],
						Double.parseDouble(uFileInput[2]))); 
				
				while (uFileReader.hasNextLine()) {
					//creates and adds machines to user's machine list
					//based on remaining lines in the user's file
					uFileInput = uFileReader.nextLine().split("#");
					users.get(users.size()-1).
						addToMachineList(new Machine(uFileInput[0], 
						Integer.parseInt(uFileInput[1]),
						Integer.parseInt(uFileInput[2]), 
						Integer.parseInt(uFileInput[3]),
						Double.parseDouble(uFileInput[4])));
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: cannot access file 1");
			}
			uFileReader.close(); //ends user<#>.txt scanner
		} catch (IOException e) {
			System.out.println("Error: Cannot access file");
		}
	}

	/**
	 * The order of the machines should be the same as in input machines file. 
	 * The output format should be the consolidated String format mentioned in 
	 * Machine class.
	 */

	public static void printMachines() {
		for (int i = 0; i < machines.size(); i++) {
			System.out.println(machines.get(i).toString());
		}
	}


	/**
	 * Interacts with the user by processing commands
	 * 
	 * @param inStock list of machines that are available to rent
	 */
	public static void userMenu(ListADT<Machine> inStock) {

		boolean done = false;
		while (!done) 
		{
			System.out.print("Enter option : ");
			String input = stdin.nextLine();

			//only do something if the user enters at least one character
			if (input.length() > 0) 
			{
				String[] commands = input.split(":");
				//split on colon, because names have spaces in them
				//addendum to account for wider ranges of invalid inputs
				if(commands[0].length()>1 || commands[0] == null ||
						commands[0].length() == 0)
				{
					System.out.println("Invalid Command");
					continue;
				}
				switch(commands[0].charAt(0)) {
				//view machines in various lists
				case 'v':
					//All machines
					if (commands[1].equals("all")) {
						for (int i = 0; i < machines.size(); i++)
							System.out.println(machines.get(i).toString());
					}
					
					//Current user machines
					else if (commands[1].equals("machinelist")) {
						currentUser.printMachineList();
					}
					
					//In stock machines
					else if (commands[1].equals("instock")) {
						for (int k = 0; k < inStock.size(); k++)
							System.out.println(inStock.get(k).toString());
					}
					else {
						System.out.println("Invalid Command");
						continue;
					}
					break;
				
				//search for machines matching user input
				case 's':
					for (int i = 0; i < machines.size(); i++) {
						if (machines.get(i).toString().contains(commands[1])) {
							System.out.println(machines.get(i).toString());
						}
					}
					break;
				
				//add machines to current user's list
				case 'a':
					boolean machineFound = false;
					for (int i = 0; i < inStock.size(); i++) {
						if (inStock.get(i).getName().equals(commands[1])) {
							currentUser.addToMachineList((inStock.get(i)));
							machineFound = true;
							break;
						}	
					}
					if (machineFound == false) {
						System.out.println("Machine not found");
					}
					break;
				
				//remove machines from current user's list
				case 'r':

					if (currentUser.removeFromMachineList(commands[1]) == null)
					{
						System.out.println("Machine not found");
					}
					break;

				//rent machines from stock
				case 'b':
					for (int i = 0; i < inStock.size(); i++) {
						try {
							if (currentUser.rent(inStock.get(i).getName())) {
								System.out.println("Rented " +
										inStock.get(i).getName());
							}
							//machine is not stocked
							else {
								System.out.println("Machine not needed: " +
										inStock.get(i).getName());
							}
						//not enough credit to rent machine
						} catch (InsufficientCreditException e) {
							System.out.println(e.getMessage());
						}
					}
					break;

				//displays remaining current user's credit
				case 'c':
					System.out.println("$" + currentUser.getCredit());
					break;

				//logs out
				case 'l':
					done = true;
					System.out.println("Logged Out");
					break;

				default:  //a command with no argument
					System.out.println("Invalid Command");
					break;
				}
			}
		}
	}
}