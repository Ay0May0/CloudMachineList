///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Cloud.java
// File:             DLinkedList.java
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
public class DLinkedList<E> implements ListADT<E> {
	private DblListnode<E> head; //Points to first node
	private DblListnode<E> tail; //Points to last node
	private int numItems; //Holds total # of items in list

	/**
	 * Constructs an empty doubly linked list with the the head and
	 * tail references set to null and the # of items in list as 0
	 */
	public DLinkedList() {
		head = null;
		tail = null;
		numItems = 0;
	}
	
	/**
	 * Adds an object into the list at the end of the list.
	 *
	 * @param item object
	 */
	public void add(E item) {
		DblListnode<E> newnode = new DblListnode<E>(item);
		//Check for empty list
		if (head == null) {
			head = newnode;
			tail = newnode;
			numItems++;
		}
		//Add node to end of list
		else {
			newnode.setPrev(tail);
			tail.setNext(newnode);
			tail = newnode;
			numItems++;
		}
	}
	
	/**
	 * Adds an object at a specific point in the list.
	 *
	 * @param pos integer between the size of list to 0
	 * @param item object
	 */
	public void add(int pos, E item) {
		//Check for valid input
		if (pos > numItems || head == null || pos < 0) 
			throw new IllegalArgumentException();

		DblListnode<E> curr = head; //Used to traverse list
		DblListnode<E> newItem = new DblListnode<E>(item);
		//Position is the beginning of the list
		if (pos == 0) {
			newItem.setNext(head);
			newItem.setPrev(tail);
			head.setPrev(newItem);
			tail.setNext(newItem);
			head = newItem;
		}
		else {
			for (int i = 0; i < pos; i++) {
				curr = curr.getNext();
			}
			newItem.setPrev(curr.getPrev());
			newItem.setNext(curr);
			curr.getPrev().setNext(newItem);
			curr.setPrev(newItem);
		}
		numItems++;
	}
	
	/**
	 * Sees if the list contains an object.
	 *
	 * @param item object
	 * @return true if the list has the same object and false otherwise
	 */
	public boolean contains(E item) {
		DblListnode<E> curr = head; //Used to traverse list
		for (int i = 0; i < numItems; i++) {
			if (curr.getData().equals(item))
				return true;
			curr.getNext();
		}
		return false;
	}
	
	/**
	 * Retrieves an object from a given position in the list.
	 *
	 * @param pos integer between the size of the list and 0
	 * @return object from the given position
	 */
	public E get(int pos) {
		//Check for valid input
		if (pos >= numItems || head == null || pos < 0) {
			throw new IllegalArgumentException();
		}
		DblListnode<E> curr = head; //Used to traverse list
		for (int i = 0; i < pos; i++) {
			curr = curr.getNext();
		}
		return curr.getData();
	}
	
	/**
	 * Sees if the list has any objects in it.
	 *
	 * @return true if the list is empty and false otherwise
	 */
	public boolean isEmpty() {
		if (head == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Removes the object at a given position and returns what it removed.
	 *
	 * @param pos an integer that should be between the list size and 0
	 * @return the object at the given position that was removed
	 */
	public E remove(int pos) {
		//Check for valid input
		if (pos >= numItems || head == null || pos < 0) 
			throw new IllegalArgumentException();
		E result =  get(pos); //Saves object being removed to be returned
		DblListnode<E> curr = head;
	
		//Item is at the beginning of chain
		if (pos == 0 && head.getNext() != null) {
			head = curr.getNext();
			head.setPrev(null);
		}
		
		//item is only one in the list
		else if (pos == 0 && head.getNext() == null) {
			head = null;
			tail = null;	
		}
		else {
			for (int i = 0; i < pos - 1; i++) {
				curr = curr.getNext();
			}
			
			//Item is at end of the chain
			if (pos == numItems - 1) {
				tail.setPrev(tail.getPrev());
				curr.setNext(null);
			}
			
			//Item is one before the end of chain
			else if (pos == numItems - 2) {
				tail.setPrev(tail.getPrev().getPrev());
				curr.setNext(curr.getNext().getNext());
			}
			
			//All other positions
			else {
				curr.setNext(curr.getNext().getNext());
				curr.getNext().setPrev(curr);
			}
		}
		numItems--;
		return result;	
	}
	
	/**
	 * Gives the size of the list.
	 *
	 * @return the size of the list
	 */
	public int size() {
		return numItems;
	}
}