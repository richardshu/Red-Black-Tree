package rbtree;

public class Main {
	public static void main(String[] args) {
		
		// Testing constructors
		System.out.println("---------- Testing constructors ----------");
		Integer[] array1 = {20, 10, 30};
		RedBlackTree<Integer> tree1 = new RedBlackTree<>(array1);
		tree1.printTree();
		System.out.println();
		
		Integer[] array2 = {40, 15, 20, 85, 45, 60, 10, 30, 35, 75, 70, 90, 25, 50, 55};
		RedBlackTree<Integer> tree2 = new RedBlackTree<>(array2);
		tree2.printTree();
		System.out.println();
		tree2.printLeavePaths();
		
		// Testing insert
		System.out.println("---------- Testing insertion ----------");
		tree1.printTree();
		System.out.println();
		System.out.println("Inserting 35:"); // Case 2
		tree1.insert(35);
		tree1.printTree();
		System.out.println();
		System.out.println("Inserting 5"); // Case 2
		tree1.insert(5);
		tree1.printTree();
		System.out.println();
		System.out.println("Inserting 25"); // Case 2
		tree1.insert(25);
		tree1.printTree();
		System.out.println();
		System.out.println("Inserting 1"); // Case 4
		tree1.insert(1);
		tree1.printTree();
		System.out.println();
			
		// Testing delete
		System.out.println("---------- Testing delete ----------");
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 55:");
		tree2.delete(55);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 40:");
		tree2.delete(40);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 15:");
		tree2.delete(15);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 20:");
		tree2.delete(20);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		/* Now start deleting from the root */
		
		System.out.println("Deleting 45:");
		tree2.delete(45);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 35:");
		tree2.delete(35);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 60:");
		tree2.delete(60);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 50:");
		tree2.delete(50);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 30:");
		tree2.delete(30);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 25:");
		tree2.delete(25);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 75:");
		tree2.delete(75);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 70:");
		tree2.delete(70);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 85:");
		tree2.delete(85);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Deleting 10:");
		tree2.delete(10);
		tree2.printTree();
		System.out.println();
		System.out.println();
		System.out.println();
	}
}