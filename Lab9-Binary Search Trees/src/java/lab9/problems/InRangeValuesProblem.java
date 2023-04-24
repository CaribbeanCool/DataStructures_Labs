package lab9.problems;

import java.util.Comparator;

import lab9.interfaces.Entry;
import lab9.interfaces.List;
import lab9.tree.BTNode;
import lab9.util.list.LinkedList;

public class InRangeValuesProblem<K, V> {
	
	Comparator<K> comparator;
	
	@SuppressWarnings("unchecked")
	public InRangeValuesProblem() {
		comparator = (a, b) ->  ((Comparable<K>) a).compareTo(b);
	}

	/**
	 * TODO EXERCISE 5:
	 * Implement a method inRangeValues() which returns a List with
	 * all the entries from the tree that have a key that is greater or equal 
	 * than a key key1, and it less than a second key key2 (this is called a range query).
	 * 
	 * Recall the class BTEntry that a BTNode uses implements the interface Entry. 
	 * The elements in the List must be ordered in pre-order.
	 *
	 * Hint: Implement an auxiliary recursive function to traverse the tree looking for 
	 * values in the range.
	 * 
	 * @param root		Root node of the Binary Search Tree
	 * @param key1		Lower bound of range
	 * @param key2		Upper bound of range
	 * @return 			List of entries in between [key1, key2)
	 */
	public List<Entry<K, V>> inRangeValues(BTNode<K, V> root, K key1, K key2) {
		List<Entry<K, V>> list = new LinkedList<>();
		recInRangeValues(root, key1, key2, list);
		return list;
	}

	private void recInRangeValues(BTNode<K, V> root, K key1, K key2, List<Entry<K, V>> list) {
		if (root == null) {
			return;
		}
		if (comparator.compare(root.getEntry().getKey(), key1) >= 0
				&& comparator.compare(root.getEntry().getKey(), key2) < 0) {
			list.add(root.getEntry());
		}
		recInRangeValues(root.getLeftChild(), key1, key2, list);
		recInRangeValues(root.getRightChild(), key1, key2, list);
	}
}