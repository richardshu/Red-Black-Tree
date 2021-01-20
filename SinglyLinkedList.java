package rbtree;

/**
 * This class represents a Singly Linked List that can hold any data type.
 * 
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 * @param <E> any data type
 */
public class SinglyLinkedList<E> {
	
	/**
	 * This nested class represents a node that can hold any data type.
	 */
	private static class Node<E> {
		private E element;
		private Node<E> next;
		
		/**
		 * Constructs a node.
		 * 
		 * @param e the element stored at this node
		 * @param n the subsequent node in the list
		 */
		public Node(E e, Node<E> n) {
			element = e;
			next = n;
		}
		
		/**
		 * Returns the element in the node.
		 * 
		 * @return the element in the node
		 */
		public E getElement() {
			return element;
		}
		
		/**
		 * Returns the subsequent node.
		 * 
		 * @return the subsequent node
		 */
		public Node<E> getNext() {
			return next;
		}
		
		/**
		 * Sets the subsequent node.
		 * 
		 * @param n the new subsequent node
		 */
		public void setNext(Node<E> n) {
			next = n;
		}
	}
	
	private Node<E> head = null;
	private Node<E> tail = null;
	private int size = 0;
	
	/**
	 * Constructs an initially empty list.
	 */
	public SinglyLinkedList() {}
	
	/**
	 * Returns the size of the list.
	 * 
	 * @return the size of the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns whether or not the list is empty.
	 * 
	 * @return true when the size == 0. Otherwise returns false
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns (but does not remove) the first element.
	 * 
	 * @return the first element
	 */
	public E first() {
		if (isEmpty()) {
			return null;
		}
		return head.getElement();
	}
	
	/**
	 * Returns (but does not remove) the last element.
	 * 
	 * @return the last element
	 */
	public E last() {
		if (isEmpty()) {
			return null;
		}
		return tail.getElement();
	}
	
	/**
	 * Adds an element e to the front of the list.
	 * 
	 * @param e the element to be added to the front of the list
	 */
	public void addFirst(E e) {
		head = new Node<>(e, head);
		if (size == 0) {
			tail = head;
		}
		size++;
	}
	
	/**
	 * Adds an element e to the end of the list.
	 * 
	 * @param e the element to be added to the end of the list
	 */
	public void addLast(E e) {
		Node<E> newest = new Node<>(e, null);
		if (isEmpty()) {
			head = newest;
		}
		else {
			tail.setNext(newest);
		}
		tail = newest;
		size++;
	}
	
	/**
	 * Removes and returns the first element.
	 * 
	 * @return the first element
	 */
	public E removeFirst() {
		if (isEmpty()) {
			return null;
		}
		E answer = head.getElement();
		head = head.getNext();
		size--;
		if (size == 0) {
			tail = null;
		}
		return answer;
	}
}