package rbtree;

/**
 * This class represents a node on a Red-Black Tree.
 * 
 * @author Richard Shu
 * @date Oct 28, 2017
 */
public class RBNode<E extends Comparable<E>> {
	protected E data;
	protected String color; // Can be "R", "B", or "DB"
	protected RBNode<E> left;
	protected RBNode<E> right;
	protected RBNode<E> parent;
	
	/**
	 * Default constructor that initializes an RBNode with a data value.
	 * 
	 * @param data the data value stored in the node
	 */
	public RBNode(E data, String color) {
		this.data = data;
		this.color = color;
	}
	
	/**
	 * Returns the data value stored in the node.
	 * 
	 * @return the data value stored in the node
	 */
	public E getData() {
		return data;
	}
	
	/**
	 * Assigns the data value stored in the node.
	 * 
	 * @param data the data value stored in the node
	 */
	public void setData(E data) {
		this.data = data;
	}
	
	/**
	 * Returns the data value and color of the node.
	 */
	public String toString() {
		if (data == null) {
			return "NIL (" + color + ")";
		}
		return data + " (" + color + ")";
	}
}