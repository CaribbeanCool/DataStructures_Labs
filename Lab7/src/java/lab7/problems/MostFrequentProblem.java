package lab7.problems;

import lab7.interfaces.Map;
import lab7.util.hashTable.HashTableOA;
import lab7.util.hashTable.SimpleHashFunction;

public class MostFrequentProblem {
	/**
	 * TODO EXERCISE 4:
	 * You are given an integer array nums. 
	 * You are also given an integer key, which is present in nums.
	 * 
	 * For every unique integer target in nums, count the number of times 
	 * target immediately follows an occurrence of key in nums. 
	 * 
	 * In other words, count the number of indices i such that:
	 * 1) 0 <= i <= nums.length - 2
	 * 2) nums[i] == key
	 * 3) nums[i + 1] == target
	 * 
	 * Return the target with the maximum count. 
	 * The test cases will be generated such that the target with maximum count is unique.
	 * @param nums
	 * @param key
	 * @return Target with the maximum count
	 */
	public int mostFrequent(int[] nums, int key) {
		Map<Integer, Integer> hashMap = new HashTableOA<>(11, new SimpleHashFunction<>());
		for (int i = 0; i < nums.length - 1; i++) {
			if (nums[i] == key) {
				if (hashMap.containsKey(nums[i + 1])) {
					hashMap.put(nums[i + 1], hashMap.get(nums[i + 1]) + 1);
				} else {
					hashMap.put(nums[i + 1], 1);
				}
			}
		}
		int max = -1;
		int result = -1;
		for (int num : hashMap.getKeys()) {
			if (hashMap.get(num) > max) {
				max = hashMap.get(num);
				result = num;
			}
		}
		return result;
	}
}