package lab7.util.set;

import java.util.Comparator;
import java.util.Iterator;

import lab7.interfaces.List;
import lab7.interfaces.Set;
import lab7.util.hashTable.HashTableOA;

public class HashSet<E> implements Set<E> {
	
	/**
	 * TODO EXERCISE 3 (DONE):
	 * 
	 * Java includes a Set implementation called 
	 * HashSet that is backed by a hash table. 
	 * 
	 * In this exercise, youll create your own version of HashSet. 
	 * 
	 * Create an implementation called HashSet that uses 
	 * an embedded hash table (using object composition).
	 * 
	 * NOTE: MAKE SURE TO IMPLEMENT THE rehash METHOD FROM EXERCISE 1 ON HashTableOA 
	 */
	
	/* DO NOT MODIFY, TESTS WILL FAIL */
	private HashTableOA<E, Object> ht; 
	private static final int DEFAULT_SIZE = 11;
	
	public HashSet(int initialCapacity) {
		ht = new HashTableOA<>(initialCapacity, theKey -> {
			String keyToString = theKey.toString();
			int hashResult = 0;
			for (char c : keyToString.toCharArray()) {
				hashResult += c;
			}
			return hashResult;
		});
	}

	public HashSet() {
		this(DEFAULT_SIZE);
	}

	@Override
	public Iterator<E> iterator() {
		return ht.getKeys().iterator();
	}

	@Override
	public boolean add(E obj) {
		if (ht.containsKey(obj)) {
			return false;
		}
		ht.put(obj, new Object());
		return true;
	}

	@Override
	public boolean isMember(E obj) {
		return ht.containsKey(obj);
	}

	@Override
	public boolean remove(E obj) {
		return ht.remove(obj) != null;
	}

	@Override
	public boolean isEmpty() {
		return ht.isEmpty();
	}

	@Override
	public int size() {
		return ht.size();
	}

	@Override
	public void clear() {
		ht.clear();
	}

	@Override
	public Set<E> union(Set<E> S2) {
		Set<E> result = new HashSet<>();
		for (E element : this) {
			result.add(element);
		}
		for (E element : S2) {
			result.add(element);
		}
		return result;
	}

	@Override
	public Set<E> difference(Set<E> S2) {
		Set<E> result = new HashSet<>();
		for (E element : this) {
			if (!S2.isMember(element)) {
				result.add(element);
			}
		}
		return result;
	}

	@Override
	public Set<E> intersection(Set<E> S2) {
		Set<E> result = new HashSet<>();
		for (E element : this) {
			if (S2.isMember(element)) {
				result.add(element);
			}
		}
		return result;
	}

	@Override
	public boolean isSubSet(Set<E> S2) {
		for (E element : this) {
			if (!S2.isMember(element)) {
				return false;
			}
		}
		return true;
	}

	/* DO NOT REMOVE, TESTS WILL FAIL */
	public HashTableOA<E, Object> getHT() {
		return this.ht;
	}
	
	public Object[] toArray() {
		List<E> elements = ht.getKeys();
		Object[] result = new Object[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			result[i] = elements.get(i);
		}
		
		return result;
	}
}
