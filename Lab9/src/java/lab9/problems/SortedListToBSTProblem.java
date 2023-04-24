package lab9.problems;

import java.util.LinkedList;
import java.util.List;

import lab9.interfaces.Entry;
import lab9.tree.BTEntry;
import lab9.tree.BTNode;

public class SortedListToBSTProblem<K extends Comparable<? super K>, V> {
	/**
	 * TODO EXERCISE 3 (DONE):
	 * 
	 * Write a method named sortedListToBST(). 
	 * Given a linked list where elements are sorted in ascending order, 
	 * convert it to a height balanced Binary Search Tree (BST). 
	 * 
	 * The method receives as parameter a non-empty list with 
	 * integers sorted in increasing (ascending) order, and returns
	 * the root of the Binary Search Tree that is the equivalent of the sorted list.
	 *   
	 * Note: The tester will use the method isBalanced() (see given code) 
	 * to check if the tree is balanced.
	 * 
	 * Hint 1: Recall binary search on an array of integers. Divide & Conquer
	 * 
	 * @param <K>	Comparable Keys of each Key-Value pair of BST, as well as the elements of the list
	 * @param <V>	Values mapped to each key in the BST
	 * @param L		List of values of type K in sorted order
	 * @return		Root of Binary Search Tree with values of L correctly placed
	 */
	public BTNode<K, V> sortedListToBST(List<K> L){
		if(L.size() == 0) return null;
		else {
			BTNode<K, V> root = new BTNode<>(null,null);
			int mid = L.size() / 2;
			root.setEntry(new BTEntry<>(L.get(mid), null));
			root.setLeftChild(sortedListToBST(L.subList(0, mid)));
			root.setRightChild(sortedListToBST(L.subList(mid + 1, L.size())));
			return root;
		}
	}

	/************************************
	 * DO NOT EDIT. THIS IS FOR TESTING *
	 ************************************/
	public boolean isBalanced(BTNode<K, V> root) {
		if(root == null || isLeaf (root)) return true;
		else return Math.abs(height(root.getLeftChild()) - height(root.getRightChild())) <= 1;
	}
	
	public int height(BTNode<K, V> root) {
		if (root == null) return 0;
		else if (root.getLeftChild() == null && root.getRightChild() == null) return 0;
		else return 1 + Math.max(height(root.getLeftChild()), height(root.getRightChild()));
	}
	
	private boolean isLeaf(BTNode<K, V> node) {
		return node.getLeftChild() == null && node.getRightChild() == null;
	}
	
	public List<K> flatten(BTNode<K, V> root) {
		List<K> L = new LinkedList<>();
		recFlatten(root, L);
		return L;
	}
	
	private void recFlatten(BTNode<K, V> root, List<K> L){
		if(root == null) return;
		else {
			recFlatten(root.getLeftChild(), L);
			L.add(root.getEntry().getKey());
			recFlatten(root.getRightChild(), L);
		}
	}
}