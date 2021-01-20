package rbtree;

/**
 * This class represents a Linked Stack that can hold any data type.
 * 
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 * @param <E> any data type
 * @see SinglyLinkedList
 */
public class LinkedStack<E> {
	private SinglyLinkedList<E> list = new SinglyLinkedList<>();
	
	/**
	 * Constructs an initially empty stack.
	 */
	public LinkedStack() {}
	
	/**
	 * Returns the size of the stack.
	 * 
	 * @return the size of the stack
	 */
	public int size() {
		return list.size();
	}
	
	/** 
	 * Returns whether or not the stack is empty.
	 * 
	 * @return true if the list size == 0. Otherwise returns false.
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/**
	 * Adds a new element to the stack.
	 * 
	 * @param element the new element added to the stack
	 */
	public void push(E element) {
		list.addFirst(element);
	}
	
	/** 
	 * Returns the element at the top of the stack.
	 * 
	 * @return the element at the top of the stack
	 */
	public E top() {
		return list.first();
	}
	
	/**
	 * Removes and returns the element at the top of the stack.
	 * 
	 * @return the element at the top of the stacks
	 */
	public E pop() {
		return list.removeFirst();
	}
}