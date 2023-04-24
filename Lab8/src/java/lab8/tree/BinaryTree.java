package lab8.tree;

import lab8.interfaces.List;
import lab8.interfaces.Tree;
import lab8.interfaces.TreeNode;
import lab8.util.list.LinkedList;

public class BinaryTree<E> implements Tree<E> {

	private BinaryTreeNode<E> root;

	/**
	 * Constructor that takes a root and two nodes denoting the subtrees of the root
	 * 
	 * @param root	Root of the tree
	 * @param T1	Left subtree of the root
	 * @param T2	Right subtree of the root
	 */
	public BinaryTree(BinaryTreeNode<E> root, BinaryTree<E> T1, BinaryTree<E> T2) {
		this.root = root;

		if(T1 != null) {
			BinaryTreeNode<E> t1Root = (BinaryTreeNode<E>) T1.root();
			this.root.setLeftChild(t1Root);
			t1Root.setParent(this.root);
		}

		if(T2 != null) {
			BinaryTreeNode<E> t2Root = (BinaryTreeNode<E>) T2.root();
			this.root.setRightChild(t2Root);
			t2Root.setParent(this.root);
		}
	}

	/**
	 * Constructor that sets the root of the tree to the given root
	 * 
	 * @param root	Root of the tree
	 */
	public BinaryTree(BinaryTreeNode<E> root) {
		// Delegate to other constructor
		this(root, null, null);
	}

	/**
	 * Default Constructor with empty tree
	 */
	public BinaryTree() {
		this(null, null, null);
	}

	@Override
	public TreeNode<E> root() {
		return this.root;
	}

	/**
	 * Returns the left child of the given node, if it's not null
	 * 
	 * @param p	The given node
	 * @return 	The left child of p if it's not null
	 */
	@Override
	public TreeNode<E> left(TreeNode<E> p) {
		return validate(p).getLeftChild();
	}

	/**
	 * Returns the left child of the given node, if it's not null
	 * 
	 * @param p	The given node
	 * @return 	The right child of p if it's not null
	 */
	@Override
	public TreeNode<E> right(TreeNode<E> p) {
		return validate(p).getRightChild();
	}

	@Override
	public TreeNode<E> sibling(TreeNode<E> p) {
		BinaryTreeNode<E> node = validate(p);
		BinaryTreeNode<E> parent = node.getParent();

		if(parent.getLeftChild() == node) 
			return parent.getRightChild();
		else 
			return parent.getLeftChild();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return recSize(root);
	}

	/**
	 * Returns the size of the tree, counting the nodes in a pre-order fashion
	 *
	 * @param root	The root of the tree
	 * @return		The current size of the tree
	 */
	private int recSize(BinaryTreeNode<E> root) {
		if(root == null) return 0;
		return 1 + recSize(root.getLeftChild()) + recSize(root.getRightChild());
	}

	private BinaryTreeNode<E> validate(TreeNode<E> p) {
		if(p == null) throw new IllegalArgumentException("Node cannot be null");
		return (BinaryTreeNode<E>) p;
	}

	/**
	 * Private method that finds the node in the tree 
	 * that contains the value given as a parameter.
	 * 
	 * Note: Remove the SuppressWarnings annotation (DONE)
	 * 
	 * @param e		Value of node to look for
	 * @param root	Root of the current subtree
	 * @return		Node that contains the value e
	 */
	private BinaryTreeNode<E> find(E e, BinaryTreeNode<E> root){
		if (root == null) return null;
		else if (root.getValue().equals(e)) return root;
		else {
			BinaryTreeNode<E> leftSubTreeRoot = find(e, root.getLeftChild());
			return leftSubTreeRoot == null 
					? find(e, root.getRightChild()) 
							: leftSubTreeRoot;	
		}
	}

	/**
	 * TODO EXERCISE 1 (DONE):
	 * 
	 * Implement a member method for the Tree ADT called height().
	 * The method computes the height of a node with the value given as parameter.
	 * If the tree does not contain a node with the given value, the method returns -1.
	 * If the node with the given value is a leaf, the method returns 0.
	 * You can assume that the method has no duplicates.
	 * 
	 * @param e 	Given value to search for node in tree
	 * @return		The height of the node with value e
	 */
	@Override
	public int height(E e) {
		BinaryTreeNode<E> node = find(e, root);
		if (node == null) {
			return -1; // node with the value e not found in the tree
		}
		return recHeight(node);
	}

	/**
	 * TODO EXERCISE 2 (DONE):
	 * 
	 * Two elements e1 and e2 are said to be cousins in a Binary Tree if 
	 * they are located in nodes that are at the same level (depth) in a Binary Tree. 
	 * 
	 * Nodes that are siblings are considered as a special case of cousins. 
	 * 
	 * Write a method verifyCousins() which returns true if two values e1, & e2 
	 * are cousins or false otherwise. 
	 * 
	 * Assume that the tree has no duplicates and that e1 and e2 are present in the tree.
	 * 
	 * @param e1 	First value
	 * @param e2	Second Value
	 * @return 		True if e1 & e2 are in nodes located in the same depth, false otherwise
	 */
	@Override
	public boolean verifyCousins(E e1, E e2) {
		BinaryTreeNode<E> node1 = find(e1, root);
		BinaryTreeNode<E> node2 = find(e2, root);

		if (node1 == null || node2 == null) {
			return false; // at least one of the nodes not found in the tree
		}

		BinaryTreeNode<E> parent1 = node1.getParent();
		BinaryTreeNode<E> parent2 = node2.getParent();

		if (parent1 == null || parent2 == null) {
			return false; // root has no cousin
		}

		if (parent1 == parent2) {
			return true; // siblings are considered cousins
		}

		int height1 = recHeight(parent1);
		int height2 = recHeight(parent2);

		return height1 == height2;
	}

	/**
	 * TODO EXERCISE 3 (DONE):
	 * 
	 * Complete the following member method for the class BinaryTree<E> called getLevelElements(). 
	 * 
	 * The method receives as a parameter an integer level that determines the level 
	 * to find all of its nodes. 
	 * 
	 * The execution throws IllegalArgumentException in the case that the 
	 * specified level is a value that is less or equal to 0.
	 * 
	 * The method returns the number of nodes that are at a given 
	 * level in the binary tree in the order in which they would be visited 
	 * by an In-Order Traversal of the tree.
	 * 
	 * @param level		The given level to count all the nodes in the tree
	 * @return			The number of nodes in a given level of the binary tree
	 */
	@Override
	public int getLevelCount(int level) {
		if (level <= 0) {
			throw new IllegalArgumentException("Level must be greater than 0");
		}
		return recGetLevelCount(root, level);
	}

	/**
	 * TODO EXERCISE 4 (DONE):
	 * 
	 * Complete the following member method for the class BinaryTree<E> called getLevelCount(). 
	 * 
	 * The method receives as a parameter an integer level that determines the level 
	 * to find all of its nodes. 
	 * 
	 * The execution throws IllegalArgumentException in the case that the 
	 * specified level is a value that is less or equal to 0.
	 * 
	 * The method returns a List<E> that stores all the nodes that are at a given 
	 * level in the binary tree in the order in which they would be visited 
	 * by an In-Order Traversal of the tree. 
	 * 
	 * @param level		The given level to store all the nodes in the tree
	 * @return			A list of nodes in a given level of the binary tree
	 */
	@Override
	public List<E> getLevelElements(int level) throws IllegalArgumentException {
		if (level <= 0) {
			throw new IllegalArgumentException("Level must be greater than 0");
		}
		List<E> list = new LinkedList<>();
		recGetLevelElements(root, level, list);
		return list;
	}

	/** 
	 * (DONE)
	 * Implement a member method for the Tree ADT called isComplete().
	 * 
	 * The method returns true if the tree is a complete tree, otherwise it returns false.
	 * 
	 * Recall that a complete tree is a tree that had all of its levels full, 
	 * except for maybe the last level. Aside from this, all nodes added to the
	 * tree must be added from left to right in each level.
	 * 
	 * WARNING: YOU MUST IMPLEMENT THIS METHOD USING O(1) SPACE 
	 * (i.e YOU CANNOT USE ANY EXTERNAL DATA STRUCTURES TO IMPLEMENT YOUR SOLUTION)
	 * (i.e. NO LISTS, STACKS, QUEUES, ARRAYS, ETC.)
	 * 
	 * @return	True if the tree is a complete tree, false otherwise
	 */
	@Override
	public boolean isComplete() {
		return isComplete(root, 0, countNodes(root));
	}

	/*********************************************************** 
	 * YOU MUST IMPLEMENT ANY AUXILIARY METHOD YOU MAY NEED.   *
	 * ADD ALL THE AUXILAIRY METHODS YOU IMPLEMENT HERE, BELOW *
	 * THE EXERCISES										   *
	 ***********************************************************/
	private int recHeight(BinaryTreeNode<E> node) {
		if (node == null) {
			return -1;
		}
		return 1 + Math.max(recHeight(node.getLeftChild()), recHeight(node.getRightChild()));
	}

	private int recGetLevelCount(BinaryTreeNode<E> root, int level) {
		if (root == null) {
			return 0;
		}
		if (level == 1) {
			return 1;
		}
		return recGetLevelCount(root.getLeftChild(), level - 1) + recGetLevelCount(root.getRightChild(), level - 1);
	}

	private void recGetLevelElements(BinaryTreeNode<E> node, int level, List<E> list) {
		if (node == null) {
			return;
		}
		if (level == 1) {
			list.add(node.getValue());
		} else {
			recGetLevelElements(node.getLeftChild(), level - 1, list);
			recGetLevelElements(node.getRightChild(), level - 1, list);
		}
	}

	private boolean isComplete(BinaryTreeNode<E> node, int index, int count) {
		//An empty tree is already complete
		if (node == null) {
			return true;
		}
		// If the index of the current node is greater than or equal to the number of nodes in the tree
		// then the tree is not complete
		if (index >= count) {
			return false;
		}
		// Recursively check the left and right sub_trees
		return isComplete(node.getLeftChild(), 2 * index + 1, count) &&
				isComplete(node.getRightChild(), 2 * index + 2, count);
	}

	private int countNodes(BinaryTreeNode<E> node) {
		if (node == null) {
			return 0;
		}
		return 1 + countNodes(node.getLeftChild()) + countNodes(node.getRightChild());
	}
}