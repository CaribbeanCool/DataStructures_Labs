package lab7.util.hashTable;

import java.io.PrintStream;

import lab7.interfaces.HashFunction;
import lab7.interfaces.List;
import lab7.interfaces.Map;
import lab7.util.LinkedList;

public class HashTableSC<K, V> implements Map<K, V> {

	@SuppressWarnings("hiding")
	private class BucketNode<K, V> {

		private K key;
		private V value;		
		public BucketNode(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public BucketNode() {
			this(null, null);
		}

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
	}

	private List<BucketNode<K, V>>[] buckets;
	private int currentSize;
	private HashFunction<K> hashFunction;
	private static final int DEFAULT_SIZE = 11;
	private static final double loadFactor = 0.75;
	private static int rehashCount = 0; /* DO NOT REMOVE, TESTS WILL FAIL */

	@SuppressWarnings("unchecked")
	public HashTableSC(int initialCapacity, HashFunction<K> hashFunction) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException("Capacity must be at least 1");
		if (hashFunction == null)
			throw new IllegalArgumentException("Hash function cannot be null");
		this.currentSize = 0;
		this.hashFunction = hashFunction;
		this.buckets = new List[initialCapacity];
		for(int i = 0; i < buckets.length; i++)
			buckets[i] = new LinkedList<BucketNode<K,V>>();
	}

	public HashTableSC() {
		this(DEFAULT_SIZE,  (key) -> {
			String temp = key.toString();
			int result = 0;
			for (int i = 0; i < temp.length(); i++)
				result += temp.charAt(i);
			return result;
		});
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
	public boolean containsKey(K key) {
		return get(key) != null;
	}

	@Override
	public V get(K key) {
		if (key == null)
			throw new IllegalArgumentException("Parameter cannot be null.");

		/* First we determine the bucket corresponding to this key */
		int targetBucket = hashFunction.hashCode(key) % buckets.length;
		/* Within that bucket there is a linked list, since we're using Separate Chaining */
		List<BucketNode<K, V>> L = buckets[targetBucket];
		/* Look for the key within the nodes of that linked list */
		for (BucketNode<K, V> BN : L) {
			if (BN.getKey().equals(key)) // Found it!
				return BN.getValue();
		}

		return null; // Did not find it
	}

	@Override
	public void put(K key, V value) {
		if(key == null || value == null)
			throw new IllegalArgumentException("Parameters cannot be null");

		remove(key);

		/* TODO EXERCISE 1(DONE): UNCOMMENT AND ADD YOUR LOGIC TO TRIGGER THE rehash() METHOD
		if(conditionToTriggerRehash) {
			rehashCount++; // DO NOT REMOVE, TESTS WILL FAIL
		}
		*/
		// System.out.println("Adding Element with key: " + key); // Uncomment for debugging

		if ((double) this.currentSize / buckets.length >= loadFactor) {
			rehash();
			rehashCount++;
		}

		/* Determine the bucket corresponding to this key */
		int targetBucket = hashFunction.hashCode(key) % buckets.length;

		/**
		 * Within that bucket there is a linked list, 
		 * since we're using Separate Chaining 
		 */
		List<BucketNode<K, V>> L = buckets[targetBucket];

		/* Finally, add the key/value to the linked list */
		L.add(0, new BucketNode<K, V>(key, value));
		currentSize++;
	}

	/* TODO EXERCISE 1 (DONE): ADD YOUR rehash() METHOD IMPLEMETNATION HERE */
	private void rehash() {
		int newSize = buckets.length * 2;
		List<BucketNode<K, V>>[] newBuckets = new List[newSize];
		for (int i = 0; i < newBuckets.length; i++)
			newBuckets[i] = new LinkedList<BucketNode<K, V>>();
		for (List<BucketNode<K, V>> l : buckets) {
			for (BucketNode<K, V> node : l) {
				int targetBucket = hashFunction.hashCode(node.getKey()) % newBuckets.length;
				newBuckets[targetBucket].add(0, node);
			}
		}
		buckets = newBuckets;
	}

	@Override
	public V remove(K key) {
		if(key == null)
			throw new IllegalArgumentException("Key must not be null");

		/* First we determine the bucket corresponding to this key */
		int targetBucket = hashFunction.hashCode(key) % buckets.length;
		/**
		 * Within that bucket there is a linked list, 
		 * since we're using Separate Chaining 
		 */
		List<BucketNode<K, V>> L = buckets[targetBucket];
		int pos = 0;
		for (BucketNode<K, V> BN : L) {
			if(BN.getKey().equals(key)) {
				L.remove(pos);
				currentSize--;
				return BN.getValue();
			} else pos++;
		}
		return null;
	}

	@Override
	public List<K> getKeys() {
		List<K> keys = new LinkedList<>();
		for(List<BucketNode<K, V>> l : buckets) {
			for (BucketNode<K, V> node : l) {
				keys.add(0, node.getKey());
			}
		}
		return keys;
	}

	@Override
	public List<V> getValues() {
		List<V> values = new LinkedList<>();
		for(List<BucketNode<K, V>> l : buckets) {
			for (BucketNode<K, V> node : l) {
				values.add(0, node.getValue());
			}
		}
		return values;
	}

	@Override
	public void clear() {
		for(int i = 0; i < buckets.length; i++)
			buckets[i].clear();
		currentSize = 0;
	}

	@Override
	public void print(PrintStream out) {
		/* For each bucket in the hash table, print the elements in that linked list */
		for (int i = 0; i < buckets.length; i++)
			for (BucketNode<K, V> BN : buckets[i])
				out.printf("(%s, %s)\n", BN.getKey(), BN.getValue());	
	}	
	
	/* DO NOT REMOVE, TESTS WILL FAIL */
	public int getRehashCount() {
		return rehashCount;
	}
	
	public void setRehashCount(int count) {
		rehashCount = count;
	}
	
	public Object[] toArray() {
		List<V> elements = getValues();
		Object[] result = new Object[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			result[i] = elements.get(i);
		}
		
		return result;
	}
}
