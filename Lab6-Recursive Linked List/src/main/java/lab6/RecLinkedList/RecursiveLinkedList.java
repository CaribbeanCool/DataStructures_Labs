import java.util.Comparator;
public class RecursiveLinkedList<E> implements List<E> {

	private Node<E> head; // References first data node
	private int currentSize;

	@SuppressWarnings("unused")
	private static class Node<E> {
		private E value;
		private Node<E> next;

		public Node(E value, Node<E> next) {
			this.value = value;
			this.next = next;
		}

		public Node(E value) {
			this(value, null); // Delegate to other constructor
		}

		public Node() {
			this(null, null); // Delegate to other constructor
		}

		public E getValue() {
			return value;
		}

		public void setValue(E value) {
			this.value = value;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}

		public void clear() {
			value = null;
			next = null;
		}
	} // End of Node class

	public RecursiveLinkedList() {
		head = null;
		currentSize = 0;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void clear() {
		head = null;
		currentSize = 0;
	}

	@Override
	public boolean contains(E e) {
		return recContains(this.head, e);
	}

	// contains() helper method
	private boolean recContains(Node<E> f, E e) {
		if (f == null) {
			return false;
		} else if (f.getValue().equals(e)) {
			return true;
		} else {
			return recContains(f.getNext(), e);
		}
	}

	@Override
	public int removeAll(E e) {
		int count = recRemoveAll(head, e);
		currentSize -= count;
		return count;
	}

	// removeAll() helper method
	private int recRemoveAll(Node<E> node, E e) {
		if (node == null) {
			return 0;
		} else if (node.getValue().equals(e)) {
			head = node.getNext();
			return 1 + recRemoveAll(head, e);
		} else {
			Node<E> prev = node;
			Node<E> curr = node.getNext();
			int count = 0;
			while (curr != null) {
				if (curr.getValue().equals(e)) {
					prev.setNext(curr.getNext());
					curr = prev.getNext();
					count++;
				} else {
					prev = curr;
					curr = curr.getNext();
				}
			}
			return count;
		}
	}

	@Override
	public E first() {
		if (isEmpty()) {
			return null;
		} else {
			return head.getValue();
		}
	}

	@Override
	public E last() {
		if (isEmpty()) {
			return null;
		} else {
			return recLast(head);
		}
	}

	// last() helper method
	private E recLast(Node<E> node) {
		if (node.getNext() == null) {
			return node.getValue();
		} else {
			return recLast(node.getNext());
		}
	}

	@Override
	public int firstIndex(E e) {
		return recFirstIndex(head, e, 0);
	}

	// firstIndex() helper method
	private int recFirstIndex(Node<E> node, E e, int index) {
		if (node == null) {
			return -1;
		} else if (node.getValue().equals(e)) {
			return index;
		} else {
			return recFirstIndex(node.getNext(), e, index + 1);
		}
	}

	@Override
	public int lastIndex(E e) {
		return recLastIndex(head, e, 0);
	}

	// lastIndex() helper method
	private int recLastIndex(Node<E> node, E e, int index) {
		if (node == null) {
			return -1;
		} else if (node.getValue().equals(e)) {
			int lastIndex = recLastIndex(node.getNext(), e, index + 1);
			if (lastIndex == -1) {
				return index;
			} else {
				return lastIndex;
			}
		} else {
			return recLastIndex(node.getNext(), e, index + 1);
		}
	}

	/* DO NOT MODIFY ANY OF THESE METHODS BELOW UNLESS IT HAS A TODO */

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException(
					"RecursiveLinkedList.get: invalid index = " + index);
		// index is valid
		return recGet(head, index);
	}

	@Override
	public void add(E e) {
		add(size(), e); // Add at the end of the list
	}

	@Override
	public void add(int index, E e) throws IndexOutOfBoundsException {
		if (index < 0 || index > size())
			throw new IndexOutOfBoundsException(
					"RecursiveLinkedList.add: invalid index = " + index);
		// index is valid for the add operation
		head = recAdd(head, index, e);
		currentSize++;
	}

	@Override
	public boolean remove(int index) {
		if (index < 0 || index >= size()) {
			return false;
		} else {
			head = recRemove(head, index);
			currentSize--;
			return true;
		}
	}

	@Override
	public E set(int index, E e) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException(
					"RecursiveLinkedList.set: invalid index = " + index);

		// index is valid for set operation
		return recSet(head, index, e);
	}

	/* DO NOT REMOVE, JUNIT TESTER WILL FAIL */
	@Override
	@SuppressWarnings("unchecked")
	public E[] toArray() {
		E[] theArray = (E[]) new Object[size()];
		int i = 0;
		for (Node<E> curNode = head; curNode != null; curNode = curNode.getNext())
			theArray[i++] = curNode.getValue();
		return theArray;
	}

	/* DO NOT MODIFY ANY OF THE METHODS ABOVE UNLESS IT HAS A TODO */

	/*******************************/
	/* Auxiliary recursive methods */
	/*******************************/

	/**
	 * Returns the value in the node corresponding to the index value i in
	 * the linked list whose first node is being referenced by f. On any such
	 * list the first node is the one associated to index 0, the second node
	 * is the one associated with index 1, and so on. It presumes that the
	 * list whose first node is f has at least i+1 nodes.
	 * 
	 * @param f First node of linked list to traverse
	 * @param i Index value of node whose value should be returned
	 * @return Value within node at index i
	 */
	private static <E> E recGet(Node<E> f, int i) {
		if (i == 0)
			return f.getValue();
		else
			return recGet(f.getNext(), i - 1);
	}

	/**
	 * TODO EXERCISE 1: DONE
	 * This method recursively removes a node from a linked list, given the starting
	 * node and the position of the node to remove.
	 * If the index is 0, the node is removed by updating the reference to the
	 * starting node and clearing the node that was removed.
	 * Otherwise, the method recurses on the next node in the list until the node to
	 * remove is found.
	 * 
	 * @param f the starting node of the linked list
	 * @param i the position of the node to remove
	 * @return The starting node of the updated linked list
	 */
	private static <E> Node<E> recRemove(Node<E> f, int i) {
		if (i == 0) {
			Node<E> ntd = f;
			f = f.getNext();
			ntd.clear();
		} else
			f.setNext(recRemove(f.getNext(), i - 1));
		return f;
	}

	/**
	 * Inserts a new node in the linked list whose first node is being
	 * referenced by f so that the new node contains the data element e and it
	 * ends up occupying the position with index value i. Finally, it returns
	 * the reference to the first node of the list that results after the
	 * insertion is completed. It presumes that the list whose first node is
	 * f has at least i nodes.
	 * 
	 * @param f First node of linked list where node must be inserted
	 * @param i Index value of where new node must be inserted
	 * @param e Value or element that must be contained within the new node
	 */
	private static <E> Node<E> recAdd(Node<E> first, int index, E e) {
		if (index == 0)
			return new Node<>(e, first);
		else {
			first.setNext(recAdd(first.getNext(), index - 1, e));
			return first;
		}
	}

	/**
	 * TODO EXERCISE 1: DONE
	 * Recursively sets the value of a Node in a linked list to a new value,
	 * given its index.
	 *
	 * This method recursively traverses a linked list to find the Node at the
	 * specified index, sets its value to the new value provided, and returns the
	 * original value of the Node. If the specified index is out of bounds, an
	 * IndexOutOfBoundsException will be thrown.
	 *
	 * @param first the first Node of the linked list to be searched
	 * @param index the index of the Node's value we're changing
	 * @param e     the new value to be set for the specified Node
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 * @return the original value of the Node before it was updated
	 */
	private static <E> E recSet(Node<E> first, int index, E e) {
		if (index == 0) {
			E original = first.getValue();
			first.setValue(e);
			return original;
		} else
			return recSet(first.getNext(), index - 1, e);
	}

	/************************************************************/
	/* Add as many auxiliary recursive methods as you want here */
	/************************************************************/

	/* TODO EXERCISE 3: ADD THE INSERTION SORT FUNCTIONALITY HERE */

	/**
	 * Sorts the elements in non-decreasing order based on the
	 * comparator object received.
	 *
	 * @param cmp Comparator object that establishes the relation order
	 *            upon which the ordering will be based.
	 */
	@Override
	public void sort(Comparator<E> cmp) {
		if (size() <= 1) // Empty or only one node?
			return; // List is already sorted
		else
			head = recInsertionSort(head, cmp);
	}

	/**
	 * Recursively sorts the linked list whose first node is referenced by
	 * variable “first” based on the given comparator object.
	 * It finally returns the reference to first node of the sorted list.
	 *
	 * @param first Reference to the first node of the list to be sorted
	 * @param cmp   Comparator object that establishes the relation order
	 *              upon which the ordering will be based.
	 * @return Reference to the first node of the sorted list
	 **/
	private static <E> Node<E> recInsertionSort(Node<E> first, Comparator<E> cmp) {
		if (first.getNext() == null)
			return first;
		else {
			/*
			 * Start by recursively sorting the sub-list starting at the
			 * current list's second node (the node after first).
			 */
			Node<E> first2 = recInsertionSort(first.getNext(), cmp);
			/*
			 * Now the sub-list must be sorted. Just need to insert the
			 * first node of the current list into that sorted sub-list.
			 * Then, return the first node of the final sorted list.
			 */
			return recInsertByOrder(first, first2, cmp);
		}
	}

	/**
	 * Inserts a new node into a SLL whose first node is given and which is
	 * assumed ordered in non-decreasing order based on comparator given.
	 *
	 * @param nti   Node to insert into the linked list given
	 * @param first First node of sorted list where insertion takes place
	 * @param cmp   Comparator upon which the sorting is based
	 * @return Reference to first node of the resulting sorted list
	 */
	private static <E> Node<E> recInsertByOrder(Node<E> nti, Node<E> first, Comparator<E> cmp) {
		if (first == null) {
			nti.setNext(null);
			return nti;
		} else if (cmp.compare(nti.getValue(), first.getValue()) <= 0) {
			nti.setNext(first);
			return nti;
		} else {
			first.setNext(recInsertByOrder(nti, first.getNext(), cmp));
			return first;
		}
	}
}