package rbtree;

/**
 * This class represents an exception that is thrown when the tree is empty.
 * 
 * @author Richard Shu
 */
public class EmptyTreeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * This constructor sets the error message.
	 */
	public EmptyTreeException() {
		super("The tree is empty.");
	}
}