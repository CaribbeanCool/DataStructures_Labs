package problems;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CPUWrapper {

	public static interface Heap<K, V> {
		public void add(K key, V value);

		public Entry<K, V> removeMin();

		public Entry<K, V> getMin();

		public void clear();

		public int size();

		public boolean isEmpty();

		public void print(int minWidth);
	}

	public static interface Entry<K, V> {
		public K getKey();

		public V getValue();
	}

	public static interface PriorityQueue<K, V> {
		public int size();

		public boolean isEmpty();

		public void insert(K key, V value) throws IllegalArgumentException;

		public Entry<K, V> min();

		public Entry<K, V> removeMin();
	}

	public static class HeapEntry<K, V> implements Entry<K, V> {
		private K key;
		private V value;

		public HeapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return "(" + key + "," + value + ")";
		}

	}

	public static class EmptyHeapException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public EmptyHeapException(String s) {
			super(s);
		}

		public EmptyHeapException() {
			this("");
		}

	}

	public static interface List<E> extends Iterable<E> {

		public void add(E obj);

		public void add(int index, E obj);

		public boolean remove(E obj);

		public boolean remove(int index);

		public int removeAll(E obj);

		public E get(int index);

		public E set(int index, E obj);

		public E first();

		public E last();

		public int firstIndex(E obj);

		public int lastIndex(E obj);

		public int size();

		public boolean isEmpty();

		public boolean contains(E obj);

		public void clear();

	}

	public static class ArrayList<E> implements List<E> {

		// private fields
		private E elements[];
		private int currentSize;
		private static final int DEFAULT_SIZE = 15;

		private class ListIterator implements Iterator<E> {
			private int currentPosition;

			public ListIterator() {
				this.currentPosition = 0;
			}

			@Override
			public boolean hasNext() {
				return this.currentPosition < size();
			}

			@Override
			public E next() {
				if (this.hasNext()) {
					return (E) elements[this.currentPosition++];
				} else
					throw new NoSuchElementException();
			}
		}

		@SuppressWarnings("unchecked")
		public ArrayList(int initialCapacity) {
			if (initialCapacity < 1)
				throw new IllegalArgumentException("Capacity must be at least 1.");
			this.currentSize = 0;
			this.elements = (E[]) new Object[initialCapacity];
		}

		public ArrayList() {
			this(DEFAULT_SIZE);
		}

		@Override
		public void add(E obj) {
			if (obj == null)
				throw new IllegalArgumentException("Object cannot be null.");
			else {
				if (this.size() == this.elements.length)
					reAllocate();
				this.elements[this.currentSize++] = obj;
			}
		}

		@SuppressWarnings("unchecked")
		private void reAllocate() {
			// create a new array with twice the size
			E newElements[] = (E[]) new Object[2 * this.elements.length];
			for (int i = 0; i < this.size(); i++)
				newElements[i] = this.elements[i];
			this.elements = newElements;
		}

		@Override
		public void add(int index, E obj) {
			if (obj == null)
				throw new IllegalArgumentException("Object cannot be null.");
			if (index == this.currentSize)
				this.add(obj); // Use other method to "append"
			else {
				if (index >= 0 && index < this.currentSize) {
					if (this.currentSize == this.elements.length)
						reAllocate();
					// move everybody one spot to the back
					for (int i = this.currentSize; i > index; i--)
						this.elements[i] = this.elements[i - 1];
					// add element at position index
					this.elements[index] = obj;
					this.currentSize++;
				} else
					throw new ArrayIndexOutOfBoundsException();
			}
		}

		@Override
		public boolean remove(E obj) {
			if (obj == null)
				throw new IllegalArgumentException("Object cannot be null.");
			// first find obj in the array
			int position = this.firstIndex(obj);
			if (position >= 0) // found it
				return this.remove(position);
			else
				return false;
		}

		@Override
		public boolean remove(int index) {
			if (index >= 0 && index < this.currentSize) {
				// move everybody one spot to the front
				for (int i = index; i < this.currentSize - 1; i++)
					this.elements[i] = this.elements[i + 1];
				this.elements[--this.currentSize] = null;
				return true;
			} else
				return false;
		}

		@Override
		public int removeAll(E obj) {
			int counter = 0;
			while (this.remove(obj))
				counter++;
			return counter;
		}

		@Override
		public E get(int index) {
			if (index >= 0 && index < this.size())
				return this.elements[index];
			else
				throw new ArrayIndexOutOfBoundsException();
		}

		@Override
		public E set(int index, E obj) {
			if (obj == null)
				throw new IllegalArgumentException("Object cannot be null.");
			if (index >= 0 && index < this.size()) {
				E temp = this.elements[index];
				this.elements[index] = obj;
				return temp;
			} else
				throw new ArrayIndexOutOfBoundsException();
		}

		@Override
		public E first() {
			if (this.isEmpty())
				return null;
			else
				return this.elements[0];
		}

		@Override
		public E last() {
			if (this.isEmpty())
				return null;
			else
				return this.elements[this.currentSize - 1];
		}

		@Override
		public int firstIndex(E obj) {
			for (int i = 0; i < this.size(); i++)
				if (this.elements[i].equals(obj))
					return i;
			return -1;
		}

		@Override
		public int lastIndex(E obj) {
			for (int i = this.size() - 1; i >= 0; i--)
				if (this.elements[i].equals(obj))
					return i;
			return -1;
		}

		@Override
		public int size() {
			return this.currentSize;
		}

		@Override
		public boolean isEmpty() {
			return this.size() == 0;
		}

		@Override
		public boolean contains(E obj) {
			return this.firstIndex(obj) >= 0;
		}

		@Override
		public void clear() {
			for (int i = 0; i < this.currentSize; i++)
				this.elements[i] = null;
			this.currentSize = 0;
		}

		@Override
		public Iterator<E> iterator() {
			return new ListIterator();
		}

	}

	public static class ListHeap<K, V> implements Heap<K, V> {
		private List<Entry<K, V>> elements;
		private Comparator<K> comparator;

		/**
		 * Constructor to declare an empty Heap
		 * 
		 * @param initialCapacity
		 * @param elements
		 */
		public ListHeap(Comparator<K> comparator) {
			this.elements = new ArrayList<>();
			this.comparator = comparator;
		}

		/**
		 * Constructor to declare a heap with n elements using bottom-up heap
		 * construction
		 * 
		 * @param randomElements
		 */
		@SuppressWarnings("unchecked")
		public ListHeap(List<Entry<K, V>> randomElements) {
			this.comparator = (k1, k2) -> ((Comparable<K>) k1).compareTo(k2);
			bottomUpHeapConstruction(randomElements);
		}

		private void bottomUpHeapConstruction(List<Entry<K, V>> heap) {
			this.elements = heap;
			for (int i = (heap.size() + 1) / 2; i >= 0; i--)
				downHeap(i);
		}

		@Override
		public void add(K key, V value) {
			Entry<K, V> entry = new HeapEntry<>(key, value);
			elements.add(entry);
			upHeap(elements.size() - 1);
		}

		@Override
		public Entry<K, V> removeMin() {
			if (elements.isEmpty())
				return null;
			Entry<K, V> min = elements.get(0);
			elements.set(0, elements.get(elements.size() - 1));
			elements.remove(elements.size() - 1);
			downHeap(0);
			return min;
		}

		@Override
		public Entry<K, V> getMin() {
			return elements.get(0);
		}

		@Override
		public void clear() {
			while (!elements.isEmpty())
				elements.remove(0);
		}

		@Override
		public int size() {
			return elements.size();
		}

		@Override
		public boolean isEmpty() {
			return elements.isEmpty();
		}

		private void upHeap(int i) {
			int parent = (i - 1) / 2;
			if (i > 0 && comparator.compare(elements.get(i).getKey(), elements.get(parent).getKey()) < 0) {
				Entry<K, V> temp = elements.get(i);
				elements.set(i, elements.get(parent));
				elements.set(parent, temp);
				upHeap(parent);
			}
		}

		private void downHeap(int i) {
			int left = 2 * i + 1;
			int right = 2 * i + 2;
			int min = i;
			if (left < elements.size()
					&& comparator.compare(elements.get(left).getKey(), elements.get(min).getKey()) < 0)
				min = left;
			if (right < elements.size()
					&& comparator.compare(elements.get(right).getKey(), elements.get(min).getKey()) < 0)
				min = right;
			if (min != i) {
				Entry<K, V> temp = elements.get(i);
				elements.set(i, elements.get(min));
				elements.set(min, temp);
				downHeap(min);
			}
		}

		@Override
		public String toString() {
			String s = "[";
			for (int i = 0; i < size() - 1; i++)
				s += elements.get(i).getKey() + ", ";

			s += elements.get(size() - 1).getKey() + "]";

			return s;
		}

		@Override
		public void print(int minWidth) {

			int size = size();

			int level = (int) (Math.log(size) / Math.log(2));
			int maxLength = (int) Math.pow(2, level) * minWidth;
			int currentLevel = -1;
			int width = maxLength;

			for (int i = 0; i < size; i++) {
				if ((int) (Math.log(i + 1) / Math.log(2)) > currentLevel) {
					currentLevel++;
					System.out.println();
					width = maxLength / (int) Math.pow(2, currentLevel);
				}
				System.out.print(center(String.valueOf(elements.get(i).getKey()), width));
			}
			System.out.println();
		}

		private String center(String text, int len) {
			String out = String.format("%" + len + "s%s%" + len + "s", "", text, "");
			float mid = (out.length() / 2);
			float start = mid - (len / 2);
			float end = start + len;
			return out.substring((int) start, (int) end);
		}
	}

	public static class HeapPriorityQueue<K, V> implements PriorityQueue<K, V> {
		private ListHeap<K, V> heap;

		public HeapPriorityQueue(Comparator<K> comparator) {
			heap = new ListHeap<>(comparator);
		}

		public HeapPriorityQueue(List<Entry<K, V>> randomElements) {
			heap = new ListHeap<>(randomElements);
		}

		@Override
		public int size() {
			return heap.size();
		}

		@Override
		public boolean isEmpty() {
			return heap.isEmpty();
		}

		@Override
		public void insert(K key, V value) {
			heap.add(key, value);
		}

		@Override
		public Entry<K, V> min() {
			return heap.getMin();
		}

		@Override
		public Entry<K, V> removeMin() {
			return heap.removeMin();
		}

		@Override
		public String toString() {
			return heap.toString();
		}
	}

	/**
	 * TODO EXERCISE 5:
	 * 
	 * You are given n processes labeled from 0 to n - 1 represented by a 2D integer
	 * array processes, where processes[i] = [enqueueTime_i, executionTime_i] means
	 * that the ith process will be available to process at enqueueTime_i and will
	 * take executionTime_i to finish executing.
	 * 
	 * You have a single-threaded CPU that can process at most one task at a time
	 * and will act in the following way:
	 * 
	 * 1) If the CPU is idle and there are no available tasks to process, the CPU
	 * remains idle.
	 * 2) If the CPU is idle and there are available tasks, the CPU will choose
	 * the one with the shortest processing time.
	 * 3) If multiple tasks have the same shortest processing time, it will choose
	 * the task with the smallest index.
	 * 4) Once a task is started, the CPU will process the entire task without
	 * stopping.
	 * 5) The CPU can finish a task then start a new one instantly.
	 * 
	 * Return the order in which the CPU will process the tasks.
	 * 
	 * Hint: Sort the processes by enqueue time and then use a Heap/PQ to place them
	 * in order
	 * of smallest processing time (since we implemented a Min-Heap, this idea
	 * works!)
	 * 
	 * @param processes Processes to execute by CPU
	 * @return The order in which the CPU will process the tasks.
	 */
	public static int[] getProcessOrder(int[][] processes) {
		HeapPriorityQueue<Integer, Integer> pq = new HeapPriorityQueue<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (processes[o1][1] == processes[o2][1])
					return o1 - o2;
				return processes[o1][1] - processes[o2][1];
			}
		});
		int[] processOrder = new int[processes.length];
		int index = 0;
		int time = 0;
		int i = 0;
		while (i < processes.length || !pq.isEmpty()) {
			if (i < processes.length && processes[i][0] <= time) {
				pq.insert(i, null);
				i++;
			} else if (!pq.isEmpty()) {
				Entry<Integer, Integer> e = pq.removeMin();
				processOrder[index++] = e.getKey();
				time += processes[e.getKey()][1];
			} else {
				time = processes[i][0];
			}
		}
		return processOrder;
	}
}