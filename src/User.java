///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Cloud.java
// File:             User.java
// Semester:         CS367 Summer 2017
//
// Author:           Manish Dhungana
// CS Login:         dhungana
// Lecturer's Name:  Meena Syamkumar
// Lab Section:      
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
import java.util.Random;

/**
 * The User class uses DLinkedList to build a price ordered list called 
 * 'machineList' of machines.
 * Machines with higher price fields should come earlier in the list.
 */
public class User {
    //Random number generator, used for generateMachineStock. DO NOT CHANGE
    private static Random randGen = new Random(1234);
    
    private String username;                //stores the user's name
    private String passwd;                  //stores the user's password
    private double credit;                  //user's credit
    private ListADT<Machine> machineList;   //stores the user's list of machines
    
    /**
     * Constructs a User instance with a name, password, credit and an empty 
     * machineList. 
     * 
     * @param username name of user
     * @param passwd password of user
     * @param credit amount of credit the user had in $ 
     */
    public User(String username, String passwd, double credit) {
    	this.username = username;
    	this.passwd = passwd;
    	this.credit = credit;
    	machineList = new DLinkedList<Machine>();
    }
    
    /**
     * Checks if login for this user is correct.
     *
     * @param username the name to check
     * @param passwd the password to check
     * @return true if credentials correct, false otherwise
     * @throws IllegalArgumentException if arguments are null 
     */
    public boolean checkLogin(String username, String passwd) {
    	//check for valid input
    	if (username == null || passwd == null) {
    		throw new IllegalArgumentException();
    	}
    	//check if input credentials are correct
    	if (this.username.equals(username) && this.passwd.equals(passwd)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    /**
     * Adds a machine to the user's machineList. 
     * Maintain the order of the machineList from highest priced to lowest 
     * priced machines.
     * @param machine the Machine to add
     * @throws IllegalArgumentException if arguments are null 
     */
    public void addToMachineList(Machine machine) {
    	//check for valid input
    	if (machine == null) {
    		throw new IllegalArgumentException();
    	}
    	for (int i = 0; i < machineList.size(); i++) {
    		if (machine.getPrice() > machineList.get(i).getPrice()) {
    			//adds lower priced machine to next position
    			machineList.add(i, machine);
    			return;
    		}
    	}
    	//adds higher priced machine to previous position
    	machineList.add(machine);
    	
    }
    
    /**
     * Removes a machine from the user's machineList. 
     * Do not charge the user for the price of this machine
     * @param machineName the name of the machine to remove
     * @return the machine on success, null if no such machine found
     * @throws IllegalArgumentException if arguments are null
     */
    public Machine removeFromMachineList(String machineName) {
    	//check for valid input
    	if (machineName == null) {
    		throw new IllegalArgumentException();
    	}
    	//machine is found & removed
    	for (int i = 0; i < machineList.size(); i++) {
    		if (machineList.get(i).getName().equals(machineName)) {
    			return machineList.remove(i);
    		}
    	}
    	//machine is not found
    	return null;
    }
    
    /**
     * Print each machine in the user's machineList in its own line by using
     * the machine's toString function.
     */
    public void printMachineList() {
    	for (int i = 0; i < machineList.size(); i++) {
    		System.out.println(machineList.get(i).toString());
    	}
    }
    
    /**
     * Rents the specified machine in the user's machineList.
     * Charge the user according to the price of the machine by updating the 
     * credit.
     * Remove the machine from the machineList as well.
     * Throws an InsufficientCreditException if the price of the machine is 
     * greater than the credit available. Throw the message as:
     * Insufficient credit for <username>! Available credit is $<credit>.
     * 
     * @param machineName name of the machine
     * @return true if successfully bought, false if machine not found 
     * @throws InsufficientCreditException if price > credit 
     */
    public boolean rent(String machineName) throws InsufficientCreditException {
    	Machine newMachine;
    	//tracks whether machine with matching name was found
    	boolean machineFound = false;
    	newMachine = new Machine(machineName, 0, 0, 0, credit);
    	for (int i = 0; i < machineList.size(); i++) {
    		if (machineList.get(i).getName().equals(machineName)) {
    			newMachine = machineList.get(i); //retrieve machine in list
    			machineList.remove(i); //remove machine from list after renting
    			machineFound = true;
    			break;
    		}
    	}
    	if (!machineFound) {
    		return false;
    	}
    	if (newMachine.getPrice() > this.credit) {
    		//insufficient credit message
    		throw new InsufficientCreditException( "For renting "
    				+ newMachine.getName() + ": Insufficient "
    				+ "credit for " +  this.username
    				+ "! Available credit is $" + this.credit );
    	}
    	else {
    		this.credit -= newMachine.getPrice(); //updates user credit
    		return true;
    	}
    }
    
    /** 
     * Returns the credit of the user
     * @return the credit
     */
    public double getCredit() {
    	return credit;
    }

    /**
     * This method is already implemented for you. Do not change.
     * Declare the first n machines in the currentUser's machineList to be 
     * available.
     * n is generated randomly between 0 and size of the machineList.
     * 
     * @returns list of machines in stock 
     */
    public ListADT<Machine> generateMachineStock() {
        ListADT<Machine> availableMachines = new DLinkedList<Machine>();

        int size = machineList.size();
        if(size == 0)
            return availableMachines;
       
       //N items in stock where n >= 0 and n < size
        int n = randGen.nextInt(size+1); 

        //pick first n items from machineList
        for(int ndx = 0; ndx < n; ndx++)
            availableMachines.add(machineList.get(ndx));
        
        return availableMachines;
    }

}