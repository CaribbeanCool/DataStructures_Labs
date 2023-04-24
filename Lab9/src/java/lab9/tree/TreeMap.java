package lab9.tree;

import java.util.Comparator;

import lab9.tree.BTEntry;
import lab9.interfaces.Entry;
import lab9.interfaces.List;
import lab9.interfaces.Map;
import lab9.util.list.LinkedList;

/**
 * Implementation of the Map ADT using a Binary Search Tree Structure
 * @author Fernando J. Bermudez - bermed28
 *
 * @param <K>	Keys of each Key-Value pair of BST
 * @param <V>	Values of each Key-Value pair of BST
 */
public class TreeMap<K, V> implements Map<K, V> {
	
	/**
	 * TODO EXERCISE 2:
	 * 
	 * Java has a version of the Map ADT that is built 
	 * with a Binary Search Tree called TreeMap.
	 * 
	 * Finish completing the implementation of the Map ADT using the 
	 * Binary search Tree Logic, WITHOUT using Object Composition
	 * 
	 * Hint: Isn't this the same as just re-using the logic 
	 * from a Binary Search Tree that uses a linked structure?
	 * The way to implement this was discussed in lectures.
	 * 
	 * Note: The testers for this exercise (TreeMapTester.java) 
	 * that check if clear(), size() & isEmpty() will NOT work
	 * if the remove() method is not implemented correctly.
	 */
	private BTNode<K, V> root;
	private int currentSize;
	private Comparator<K> comparator;
	
	public TreeMap(Comparator<K> comparator) {
		this.root = null;
		this.comparator = comparator;
		this.currentSize = 0;
	}
	
	@SuppressWarnings("unchecked")
	public TreeMap() {
		this((k1, k2) -> ((Comparable<K>) k1).compareTo(k2));
	}

	@Override
	public V get(K key) {
		if (key == null)
			throw new IllegalArgumentException("Key cannot be null");
		return this.getAux(this.root, key);
	}

	private V getAux(BTNode<K, V> N, K key) {
		if (N == null)
			return null;
		int compare = this.comparator.compare(key, N.getEntry().getKey());
		if (compare == 0)
			return N.getEntry().getValue();
		else if (compare < 0)
			return this.getAux(N.getLeftChild(), key);
		else
			return this.getAux(N.getRightChild(), key);
	}

	@Override
	public void put(K key, V value) {
		if (key == null || value == null)
			throw new IllegalArgumentException("Parameters cannot be null.");
		this.root = this.putAux(this.root, key, value);
	}

	private BTNode<K, V> putAux(BTNode<K, V> N, K key, V value) {
		if (N == null) {
			this.currentSize++;
			Entry<K, V> entry = new BTEntry<>(key, value);
			return new BTNode<>(entry.getKey(), entry.getValue());
		}
		int compare = this.comparator.compare(key, N.getEntry().getKey());
		if (compare == 0)
			N.setEntry(new BTEntry<>(key, value));
		else if (compare < 0)
			N.setLeftChild(this.putAux(N.getLeftChild(), key, value));
		else
			N.setRightChild(this.putAux(N.getRightChild(), key, value));
		return N;
	}

	/**
	 * TODO EXERCISE 1:
	 * 
	 * Removes node that has the given key. If key not found returns null.
	 * 
	 * If the node is found then HOW the node is removed is dependent of how many children the node has.
	 * 1. Node is a leaf. It's an easy remove.
	 * 2. Node has 1 child. The child replaces the node being removed by becoming the new child of the node's parent.
	 * 3. Node has both children. Hardest case. The node being removed is replaced by its in-order predecessor. 
	 * 
	 * NOTE: Also consider what would happen with cases when BST only has the root or has 2 nodes and we remove the root.
	 * Hint: Much like get() and add() you will need an auxiliary function.
	 * 
	 * Note: The testers for this exercise (TreeMapTester2.java) will NOT work fully
	 * if the other ADT methods are not implemented fully
	 * 
	 * @param key - Key of the Entry we want to remove.
	 * @return Returns the Entry that was removed or null if not found.
	 */
	@Override
	public V remove(K key) {
		if (key == null)
			throw new IllegalArgumentException("Key cannot be null.");
		Entry<K, V> entry = this.removeAux(this.root, key);
		if (entry == null)
			return null;
		this.currentSize--;
		return entry.getValue();
	}

	private Entry<K, V> removeAux(BTNode<K, V> N, K key) {
		if (N == null)
			return null;
		int compare = this.comparator.compare(key, N.getEntry().getKey());
		if (compare < 0)
			return this.removeAux(N.getLeftChild(), key);
		else if (compare > 0)
			return this.removeAux(N.getRightChild(), key);
		else
			return this.removeNode(N);
	}

	private Entry<K, V> removeNode(BTNode<K, V> N) {
		if (N.getLeftChild() == null && N.getRightChild() == null) {
			N = null;
			return N.getEntry();
		}
		if (N.getLeftChild() != null && N.getRightChild() != null) {
			BTNode<K, V> predecessor = this.getPredecessor(N.getLeftChild());
			N.setEntry(predecessor.getEntry());
			predecessor = null;
			return N.getEntry();
		}
		if (N.getLeftChild() != null) {
			N.setEntry(N.getLeftChild().getEntry());
			N.setLeftChild(null);
			return N.getEntry();
		} else {
			N.setEntry(N.getRightChild().getEntry());
			N.setRightChild(null);
			return N.getEntry();
		}
	}

	private BTNode<K, V> getPredecessor(BTNode<K, V> N) {
		if (N.getRightChild() == null)
			return N;
		return this.getPredecessor(N.getRightChild());
	}

	@Override
	public boolean containsKey(K key) {
		if (key == null)
			throw new IllegalArgumentException("Key cannot be null.");
		if (this.get(key) == null)
			return false;
		return true;
	}

	@Override
	public List<K> getKeys() {
		List<K> keys = new LinkedList<>();
		this.getKeysAux(this.root, keys);
		return keys;
	}

	private void getKeysAux(BTNode<K, V> N, List<K> keys) {
		if (N != null) {
			this.getKeysAux(N.getLeftChild(), keys);
			keys.add(N.getEntry().getKey());
			this.getKeysAux(N.getRightChild(), keys);
		}
	}

	@Override
	public List<V> getValues() {
		List<V> values = new LinkedList<>();
		this.getValuesAux(this.root, values);
		return values;
	}

	private void getValuesAux(BTNode<K, V> N, List<V> values) {
		if (N != null) {
			this.getValuesAux(N.getLeftChild(), values);
			values.add(N.getEntry().getValue());
			this.getValuesAux(N.getRightChild(), values);
		}
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	@Override
	public void clear() {
		this.root = null;
		this.currentSize = 0;
	}
	
	/* DO NOT EDIT, TESTS WILL FAIL IF REMOVED/MODIFIED */
	@Override
	public BTNode<K, V> getRoot(){
		return this.root;
	}
	
	public void print() {
		this.printAux(this.root, 0);
	}

	private void printAux(BTNode<K, V> N, int i) {
		if (N != null) {
			this.printAux(N.getRightChild(), i + 8);
			System.out.println();
			for (int j=0; j < i; ++j) {

				System.out.print(" ");
			}
			System.out.println(N.getEntry().getValue());
			this.printAux(N.getLeftChild(), i + 8);
		}
		
	}
}
