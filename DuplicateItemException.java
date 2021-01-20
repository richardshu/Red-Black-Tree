package rbtree;

/**
 * This class represents an exception that is thrown when duplicate objects are added to the tree.
 * 
 * @author Richard Shu
 */
public class DuplicateItemException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * This constructor sets the error message.
	 */
	public DuplicateItemException() {
		super("A duplicate item exists in the tree.");
	}
}