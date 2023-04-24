package lab9.problems;

import lab9.tree.BTNode;

public class MinimumDifferenceProblem {
	
	/* TODO DECLARE AS MANY PRIVATE FIELDS AS NECESSARY HERE */
	int minDiff = Integer.MAX_VALUE;
	int prev = -1;

	/**
	 * TODO EXERCISE 4
	 * 
	 * Implement a method called getMinimumDifference() that takes 
	 * in as parameter the root of a Binary Search Tree and returns 
	 * an integer denoting the minimum absolute difference of any 
	 * two distinct nodes within the Binary Search Tree.
	 * 
	 * Assumptions:
	 * 
	 * 1) The node values in the binary search tree are greater than or equal to zero.
	 * 2) All node values in the binary search tree are unique.
	 * 3) The method will never receive the root of a tree that less than 2 nodes.
	 * 
	 * WARNING: YOU MUST COMPLETE THIS PROBLEM IN O(1) SPACE COMPLEXITY TO RECEIVE CREDIT
	 * 
	 * @param root	Root of a Binary Search Tree
	 * @return		The minimum absolute difference of two distinct nodes
	 */
	public int getMinimumDifference(BTNode<Integer, Integer> root) {
		while (root != null) {
			if (root.getLeftChild() == null) {
				if (prev != -1) {
					minDiff = Math.min(minDiff, root.getEntry().getKey() - prev);
				}
				prev = root.getEntry().getKey();
				root = root.getRightChild();
			} else {
				BTNode<Integer, Integer> predecessor = root.getLeftChild();
				while (predecessor.getRightChild() != null && predecessor.getRightChild() != root) {
					predecessor = predecessor.getRightChild();
				}
				if (predecessor.getRightChild() == null) {
					predecessor.setRightChild(root);
					root = root.getLeftChild();
				} else {
					if (prev != -1) {
						minDiff = Math.min(minDiff, root.getEntry().getKey() - prev);
					}
					prev = root.getEntry().getKey();
					predecessor.setRightChild(null);
					root = root.getRightChild();
				}
			}
		}
		return minDiff;
	}
}