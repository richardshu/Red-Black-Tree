package rbtree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a Red-Black Tree made up of nodes linked together.
 * 
 * @author Richard Shu
 * @date Oct 27, 2017
 */

public class RedBlackTree<E extends Comparable<E>> {
	protected RBNode<E> root;
	protected final RBNode<E> NIL = new RBNode<E>(null, "B");
	
	/**
	 * Constructs an initially empty red-black tree.
	 */
	public RedBlackTree() {}
	
	/**
	 * Initializes a red-black tree with an array.
	 * 
	 * @param array an array of data values used to initialize the tree
	 */
	public RedBlackTree(E[] array) {
		if (array.length != 0) {
			for (int i = 0; i < array.length; i++) {
				insert(array[i]);
			}
		}
	}
	
	/**
	 * Adds a new value to the tree according to the rules of a red-black tree.
	 * 
	 * @param key the item that will be inserted into the tree
	 */
	public void insert(E key) {
		RBNode<E> child = new RBNode<>(key, "R");
		child.left = NIL; // Default left child is set to NIL
		child.right = NIL; // Default right child is set to NIL
		if (isEmpty()) {
			root = child;
		}
		else {
			try {
				RBNode<E> parent = insertionPoint(key);
				if (key.compareTo(parent.getData()) < 0) {
					parent.left = child;
				}
				else {
					parent.right = child;
				}
				child.parent = parent;
			} catch (DuplicateItemException e) {
				throw new DuplicateItemException();
			}
		}
		insertionCleanup(child); // Update tree to have the properties of a Red-Black Tree
	}
	
	/**
	 * Returns the node that the new node will attach to. 
	 * This is a helper method for the insert method.
	 * 
	 * @param key the item that will be inserted into the tree
	 * @return the node that the new node will attach to
	 */
	private RBNode<E> insertionPoint(E key) {
		RBNode<E> parent = root;
		RBNode<E> current = root;
		while (current != NIL) { // Stop traversing the tree when you get to NIL
			if (key.equals(current.getData())) {
				throw new DuplicateItemException();
			}
			else if (key.compareTo(current.getData()) < 0) {
				parent = current;
				current = current.left;
			}
			else if (key.compareTo(current.getData()) > 0) {
				parent = current;
				current = current.right;
			}
		}
		return parent;
	}
	
	private void insertionCleanup(RBNode<E> node) {
		
		// Case 1: The root is red
		if (root.color.equals("R")) {
			root.color = "B"; // Base case
		}
		
		// Case 2: The parent is black
		else if (node.parent.color.equals("B")) {} // Do nothing
		
		else {
			// Since cases 1 & 2 are skipped, the node is 
			// guaranteed to have a parent, uncle, & grandparent
			RBNode<E> parent = node.parent;
			RBNode<E> uncle = uncle(node);
			RBNode<E> grandparent = grandparent(node);
			// Case 3: The parent and uncle are red
			if (isRed(parent) && isRed(uncle)) {
				parent.color = "B"; 			// Change parent to black
				uncle.color = "B"; 			 	// Change uncle to black
				grandparent.color = "R"; 		// Change grandparent to red
				insertionCleanup(grandparent); 	// Recursively check grandparent for any violations
			}
			
			// Case 4: The parent is red and the uncle is black
			else if (parent.color.equals("R") && uncle.color.equals("B")) {
				
				// Case 4a: The node is a right child & the parent is a left child
				if (isRightChild(node) && isLeftChild(parent)) {
					leftRotate(parent);
					
					// Swap the node and the parent. Go to case 5.
					RBNode<E> temp = node;
					node = parent;
					parent = temp;
				}
				
				// Case 4b: The node is a left child & the parent is a right child
				else if (isLeftChild(node) && isRightChild(parent)) {
					rightRotate(parent);
					
					// Swap the node and the parent. Go to case 5.
					RBNode<E> temp = node;
					node = parent;
					parent = temp;
				}
				
				// Case 5a: The node and parent are left children
				if (isLeftChild(node) && isLeftChild(parent)) {
					parent.color = "B";			// Change parent to black
					grandparent.color = "R";	// Change grandparent to red
					rightRotate(grandparent);	// Right rotate the grandparent
				}
				
				// Case 5b: The node and parent are right children
				else if (isRightChild(node) && isRightChild(parent)) {
					parent.color = "B";			// Change the parent to black
					grandparent.color = "R";	// Change the grandparent to red
					leftRotate(grandparent);	// Left rotate the grandparent
				}
			}
		}
	}
	
	/**
	 * Takes a key and removes the node from the tree.
	 * 
	 * @param key the item that will be deleted from the tree
	 */
	public void delete(E key) {
		if (isEmpty()) {
			throw new EmptyTreeException();
		}
		try {
			RBNode<E> node = nodeToDelete(key);
			RBNode<E> parent = node.parent;
			if (isLeaf(node)) { // Case 1: Node is a leaf
				if (node.equals(root)) { // Root is the only node in the tree
					root = null;
				}
				else {
					if (isLeftChild(node)) {
						parent.left = NIL;
					}
					else if (isRightChild(node)) {
						parent.right = NIL;
					}
					NIL.parent = parent; // This step is necessary for the fixDoubleBlack method
					if (node.color.equals("B")) {
						NIL.color = "DB";
						fixDoubleBlack(NIL);
					}
				}
			}
			else if (numChildren(node) == 1) { // Case 2: Node has one child
				RBNode<E> child;
				if (node.left == NIL) {
					child = node.right;
				}
				else {
					child = node.left;
				}
				if (node.equals(root)) {
					root = child;
				}
				else if (isLeftChild(node)) {
					parent.left = child;
				}
				else {
					parent.right = child;
				}
				child.parent = parent; // Update parent reference
				if (child.color.equals("R") || node.color.equals("R")) { // Note that both node & child cannot be red
					child.color = "B";
				}
				else if (child.color.equals("B") && node.color.equals("B")) {
					child.color = "DB";
					fixDoubleBlack(child);
				}
			}
			else if (numChildren(node) == 2) { // Case 3: Node has two children
				if (node.equals(root) && node.left == NIL) {
					root = node.right;
					root.color = "B";
				}
				else {
					E max = maxLeftSubtree(node);
					delete(max); // Recursion
					node.setData(max);
				}
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("The item cannot be found in the tree.");
		}
	}
	
	/**
	 * Returns the node that will be deleted from the tree. 
	 * This is a helper method for the delete method.
	 * 
	 * @param key the item that will be deleted from the tree
	 * @return the node that will be deleted
	 */
	private RBNode<E> nodeToDelete(E key) {
		RBNode<E> current = root;
		while (current != NIL) {
			if (key.equals(current.getData())) {
				return current;
			}
			else if (key.compareTo(current.getData()) < 0) {
				current = current.left;
			}
			else if (key.compareTo(current.getData()) > 0) {
				current = current.right;
			}
		}
		throw new NullPointerException(); // Handle this exception in the delete method
	}
	
	/**
	 * Removes the double black node from the tree.
	 * 
	 * @param node the double black node that needs to be fixed
	 */
	private void fixDoubleBlack(RBNode<E> node) {
		
		// Case 1: The root is double black
		if (node.equals(root)) {
			node.color = "B"; // Base case
		}
		
		else {
			RBNode<E> sibling = sibling(node);
			RBNode<E> parent = node.parent;
			
			// Case 2: The sibling is red
			if (isRed(sibling)) {
				
				// Case 2a: The node is a right child
				if (isRightChild(node)) {
					sibling.color = "B"; 	// Change sibling to black
					parent.color = "R"; 	// Change parent to red
					rightRotate(parent); 	// Right rotate the parent
				}
				
				// Case 2b: The node is a left child
				else if (isLeftChild(node)) {
					sibling.color = "B"; 	// Change sibling to black
					parent.color = "R"; 	// Change parent to red
					leftRotate(parent); 	// Left rotate the parent
				}
				fixDoubleBlack(node); // Recursion
			}
			
			// Case 3: The sibling has at least one red child
			else if (hasRedChild(sibling)) {
				RBNode<E> RC; // Red child
				
				// Case 3a: The sibling is a left child
				if (isLeftChild(sibling)) {
					
					// Case 3a.1: The right child of the sibling is red
					if (isRed(sibling.right)) {
						RC = sibling.right;
						leftRotate(sibling);		// Left rotate the sibling
						rightRotate(parent);		// Right rotate the parent
						RC.color = parent.color;	// Change RC to the color of parent
						sibling.color = "B";		// Change sibling to black
						parent.color = "B";			// Change parent to black
						node.color = "B";			// Change node to black
					}
					
					// Case 3a.2: The left child of the sibling is red
					else if (isRed(sibling.left)) {
						RC = sibling.left;
						rightRotate(parent);			// Right rotate the parent
						sibling.color = parent.color;	// Change sibling to the color of parent
						RC.color = "B";					// Change RC to black
						parent.color = "B";				// Change parent to black
						node.color = "B";				// Change node to black
					}
				}
				
				// Case 3b: Sibling is a right child
				else if (isRightChild(sibling)) {
					
					// Case 3b.1: The left child of the sibling is red
					if (isRed(sibling.left)) {
						RC = sibling.left;
						rightRotate(sibling);		// Right rotate the sibling
						leftRotate(parent);			// Left rotate the parent
						RC.color = parent.color;	// Change RC to the color of parent
						sibling.color = "B";		// Change sibling to black
						parent.color = "B";			// Change parent to black
						node.color = "B";			// Change node to black
					}
					
					// Case 3b.2: The right child of the sibling is red
					else if (isRed(sibling.right)) {
						RC = sibling.right;
						leftRotate(parent);				// Left rotate the parent
						sibling.color = parent.color;	// Change sibling to the color of parent
						RC.color = "B";					// Change RC to black
						parent.color = "B";				// Change parent to black
						node.color = "B";				// Change node to black
					}
				}
			}
			
			// Case 4: The sibling and both of its children are black (or double black)
			else if ((isBlack(sibling) && isBlack(sibling.left) && isBlack(sibling.right)) ||
					 (isBlack(sibling) && isDoubleBlack(sibling.left) && isDoubleBlack(sibling.right))) {
					 // 2nd condition is used when the double black node is NIL and the sibling's children are
					 // NIL since changing NIL to double black changes all instances of NIL to double black
				
				// Case 4a: The parent of sibling is red
				if (isRed(parent)) { 		// The sibling and node share the same parent
					sibling.color = "R";	// Change sibling to red
					parent.color = "B";		// Change parent to black
					node.color = "B";		// Change node to black
				}
				
				// Case 4b: The parent of sibling is black
				else if (isBlack(sibling.parent)) {
					sibling.color = "R";	// Change sibling to red
					parent.color = "DB";	// Change parent to double black
					node.color = "B";		// Change node to black
					fixDoubleBlack(parent);	// Recursion
				}
			}
		}
	}
	
	/**
	 * Returns the maximum node on the left subtree of the given node. 
	 * This is a helper method for the delete method.
	 * 
	 * @param node the node that will be deleted from the binary search tree
	 * @return the maximum node on the left subtree of the given node
	 */
	private E maxLeftSubtree(RBNode<E> node) {
		RBNode<E> current = node.left;
		while (current.right != NIL) {
			current = current.right;
		}
		return current.getData();
	}
	
	/**
	 * Returns true or false depending on if the key is found in the tree or not.
	 * 
	 * @param key the item searched for in the tree
	 * @return true or false depending on if the key is found in the tree or not
	 */
	public boolean find(E key) {
		RBNode<E> current = root;
		while (current != NIL) {
			if (key.equals(current.getData())) {
				return true;
			}
			else if (key.compareTo(current.getData()) < 0) {
				current = current.left;
			}
			else if (key.compareTo(current.getData()) > 0) {
				current = current.right;
			}
		}
		return false;
	}
	
	/**
	 * Computes and returns the depth of the given node.
	 * 
	 * @param node the node whose depth will be calculated
	 * @return the depth of the given node
	 */
	public int depth(RBNode<E> node) {
		if (!find(node.getData())) {
			throw new NullPointerException("The node cannot be found in the tree.");
		}
		if (node.equals(root)) { // Base case
			return 0;
		}
		return 1 + depth(node.parent); // Recursive case
	}
	
	/**
	 * Computes and returns the height of the tree.
	 * 
	 * @param node the root of a tree whose height will be calculated
	 * @return the height of the tree
	 */
	public int height(RBNode<E> node) {
		if (node == null) {
			return -1;
		}
		return 1 + Math.max(height(node.left), height(node.right));
	}
	
	/**
	 * Returns true or false if the tree is empty or not.
	 * 
	 * @return true or false if the tree is empty or not
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * Returns true or false if the node is a leaf or not.
	 * 
	 * @param node the node that is determined to be a leaf or not
	 * @return true or false if the node is a leaf or not
	 */
	public boolean isLeaf(RBNode<E> node) {
		if (node == NIL) {
			return false;
		}
		return (node.left == NIL) && (node.right == NIL);
	}
	
	/**
	 * Returns true or false if the given node is a left child of its parent, or not.
	 * 
	 * @param node the node that is determined to be a left or right child
	 * @return true or false if the given node is a left child of its parent, or not
	 */
	public boolean isLeftChild(RBNode<E> node) {
		if (node.equals(root) || node.parent.left == null) {
			return false;
		}
		return node.parent.left.equals(node);
	}
	
	/**
	 * Returns true or false if the given node is a right child of its parent or not.
	 * 
	 * @param node the node that is determined to be a left or right child
	 * @return true or false if the given node is a right child of its parent or not
	 */
	public boolean isRightChild(RBNode<E> node) {
		if (node.equals(root) || node.parent.right == null) {
			return false;
		}
		return node.parent.right.equals(node);
	}
	
	/**
	 * Returns the sibling node of the given node.
	 * 
	 * @param node the node whose sibling is being found
	 * @return the sibling node of the given node
	 */
	public RBNode<E> sibling(RBNode<E> node) {
		if (node.equals(root)) {
			return null;
		}
		else if (isLeftChild(node)) {
			return node.parent.right;
		}
		return node.parent.left;
	}
	
	/**
	 * Returns the uncle node of the given node.
	 * 
	 * @param node the node whose uncle is being found
	 * @return the uncle node of the given node
	 */
	public RBNode<E> uncle(RBNode<E> node) {
		if (node.equals(root) || node.equals(root.left) || node.equals(root.right)) {
			return null;
		}
		return sibling(node.parent);
	}
	
	/**
	 * Returns the grandparent node of the given node.
	 * 
	 * @param node the node whose grandparent is being found
	 * @return the grandparent node of the given node
	 */
	public RBNode<E> grandparent(RBNode<E> node) {
		if (node.equals(root) || node.equals(root.left) || node.equals(root.right)) {
			return null;
		}
		return node.parent.parent;
	}
	
	/**
	 * Returns an ArrayList of nodes generated using preorder traversal.
	 * 
	 * @return an ArrayList of nodes generated using preorder traversal
	 */
	public ArrayList<RBNode<E>> preorder() {
		ArrayList<RBNode<E>> list = new ArrayList<>();
		if (!isEmpty()) {
			LinkedStack<RBNode<E>> stack = new LinkedStack<>();
			stack.push(root);
			while (!stack.isEmpty()) {
				RBNode<E> node = stack.pop();
				list.add(node);
				if (node.right != NIL) {
					stack.push(node.right);
				}
				if (node.left != NIL) {
					stack.push(node.left);
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * Returns an ArrayList of nodes generated using inorder traversal.
	 * 
	 * @return an ArrayList of nodes generated using inorder traversal
	 */
	public ArrayList<RBNode<E>> inorder() {
		ArrayList<RBNode<E>> list = new ArrayList<>();
		if (!isEmpty()) {
			LinkedStack<RBNode<E>> stack = new LinkedStack<>();
			RBNode<E> current = root;
			while (!stack.isEmpty() || current != NIL) {
				if (current != NIL) {
					stack.push(current);
					current = current.left;
				}
				else {
					current = stack.pop();
					list.add(current);
					current = current.right;
				}
			}
		}
		return list;
	}
	
	/**
	 * Returns an ArrayList of nodes generated using postorder traversal.
	 * 
	 * @return an ArrayList of nodes generated using postorder traversal
	 */
	public ArrayList<RBNode<E>> postorder() {
		ArrayList<RBNode<E>> list = new ArrayList<>();
		if (!isEmpty()) {
			LinkedStack<RBNode<E>> stack1 = new LinkedStack<>();
			LinkedStack<RBNode<E>> stack2 = new LinkedStack<>();
			RBNode<E> current;
			stack1.push(root);
			while (!stack1.isEmpty()) {
				current = stack1.pop();
				stack2.push(current);
				if (current.left != NIL) {
					stack1.push(current.left);
				}
				if (current.right != NIL) {
					stack1.push(current.right);
				}
			}
			while (!stack2.isEmpty()) {
				current = stack2.pop();
				list.add(current);
			}
		}
		return list;
	}
	
	/**
	 * Returns an ArrayList of nodes generated using breadthfirst traversal.
	 * 
	 * @return an ArrayList of nodes generated using breadthfirst traversal
	 */
	public ArrayList<RBNode<E>> breadthfirst() {
		ArrayList<RBNode<E>> list = new ArrayList<>();
		if (!isEmpty()) {
			Queue<RBNode<E>> queue = new LinkedList<RBNode<E>>();
			queue.add(root);
			while (!queue.isEmpty()) {
				RBNode<E> node = queue.remove();
				list.add(node);
				if (node.left != NIL) {
					queue.add(node.left);
				}
				if (node.right != NIL) {
					queue.add(node.right);
				}
			}
		}
		return list;
	}
	
	/**
	 * Prints the path of each leaf node to the root. 
	 * This method checks that each node has the correct 
	 * parent reference and is used for TESTING purposes only.
	 */
	public void printLeavePaths() {
		ArrayList<RBNode<E>> leaves = leaves();
		for (int i = 0; i < leaves.size(); i++) {
			RBNode<E> node = leaves.get(i);
			printPathToRoot(node);
			System.out.println();
		}
	}
	
	/**
	 * Returns an ArrayList containing the leaves of a tree. 
	 * This is a helper method for the printLeavePaths method.
	 * 
	 * @return an ArrayList containing the leaves of a tree.
	 */
	private ArrayList<RBNode<E>> leaves() {
		ArrayList<RBNode<E>> treeNodes = preorder(); // Can be any tree traversal
		ArrayList<RBNode<E>> leaves = new ArrayList<>();
		for (int i = 0; i < treeNodes.size(); i++) {
			RBNode<E> node = treeNodes.get(i);
			if (isLeaf(node)) {
				leaves.add(node);
			}
		}
		return leaves;
	}
	
	/**
	 * Prints the path from the given node to the root of the tree. 
	 * This is a helper method for the printLeavePaths method.
	 * 
	 * @param node the node whose path is printed out
	 */
	private void printPathToRoot(RBNode<E> node) {
		while (node.parent != null) {
			System.out.print(node + " --> ");
			node = node.parent;
		}
		System.out.print(node);
	}
	
	/**
	 * Returns the number of children of a given node.
	 * 
	 * @param node the node whose children will be counted
	 * @return the number of children of a given node
	 */
	private int numChildren(RBNode<E> node) {
		int count = 0;
		if (node.left != NIL) {
			count++;
		}
		if (node.right != NIL) {
			count++;
		}
		return count;
	}
	
	/**
	 * Returns true if the node is red.
	 * 
	 * @param node the node determined to be red or not
	 * @return true if the node is red
	 */
	private boolean isRed(RBNode<E> node) {
		return node.color.equals("R");
	}
	
	/**
	 * Returns true if the node is black.
	 * 
	 * @param node the node determined to be black or not
	 * @return true if the node is black
	 */
	private boolean isBlack(RBNode<E> node) {
		return node.color.equals("B");
	}
	
	/**
	 * Returns true if the node is double black.
	 * 
	 * @param node the node determined to be double black or not
	 * @return true if the node is double black
	 */
	private boolean isDoubleBlack(RBNode<E> node) {
		return node.color.equals("DB");
	}
	
	/**
	 * Returns true if the node has at least one red child.
	 * 
	 * @param node the node determined to have red children or not
	 * @return true if the node has at least one red child
	 */
	private boolean hasRedChild(RBNode<E> node) {
		if (isRed(node.left) || isRed(node.right)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Left rotates the tree rooted at the given node.
	 * 
	 * @param root the node used to rotate the tree
	 */
	private void leftRotate(RBNode<E> root) {
		RBNode<E> pivot = root.right;
		pivot.parent = root.parent; // Update parent reference
		
		// SPECIAL CASE: The root of the entire tree is used as the root of the rotation
		if (this.root.equals(root)) { 
			this.root = pivot;
		}
		else if (isLeftChild(root)) {
			pivot.parent.left = pivot;
		}
		else if (isRightChild(root)) {
			pivot.parent.right = pivot;
		}
		root.right = pivot.left;
		root.right.parent = root; // Update parent reference
		pivot.left = root;
		pivot.left.parent = pivot; // Update parent reference
	}
	
	/**
	 * Right rotates the tree rooted at the given node.
	 * 
	 * @param root the node used to rotate the tree
	 */
	private void rightRotate(RBNode<E> root) {
		RBNode<E> pivot = root.left;
		pivot.parent = root.parent; // Update parent reference
		
		// SPECIAL CASE: The root of the entire tree is used as the root of the rotation
		if (this.root.equals(root)) { 
			this.root = pivot;
		}
		else if (isLeftChild(root)) {
			pivot.parent.left = pivot;
		}
		else if (isRightChild(root)) {
			pivot.parent.right = pivot;
		}
		root.left = pivot.right;
		root.left.parent = root; // Update parent reference
		pivot.right = root;
		pivot.right.parent = pivot; // Update parent reference
	}
	
	/**
	 * Uses the code given below to display the tree in the console.
	 * 
	 * @author Laurent Demailly
	 */
	public void printTree() {
		if (!isEmpty()) {
			if (this.root.right != null) {
				this.printTree(this.root.right, true, "");
			}
			System.out.println(this.root);
			if (this.root.left != null) {
				this.printTree(this.root.left, false, "");
			}
		}
	}
	
	/**
	 * Prints the binary search tree.
	 * 
	 * @author Laurent Demailly
	 * @param node the node that will be printed
	 * @param isRight 
	 * @param indent the spacing between each value
	 */
	private void printTree(RBNode<E> node, boolean isRight, String indent) {
	    if (node.right != null) {
	        printTree(node.right, true, indent + (isRight ? "        " : " |      "));
	    }
	    System.out.print(indent);
	    if (isRight) {
	        System.out.print(" /");
	    }
	    else {
	        System.out.print(" \\");
	    }
	    System.out.print("----- ");
	    System.out.print(node + "\n");
	    if (node.left != null) {
	        printTree(node.left, false, indent + (isRight ? " |      " : "        "));
	    }
	}
}