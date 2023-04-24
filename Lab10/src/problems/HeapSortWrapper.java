package problems;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HeapSortWrapper {

	/**
	 * TODO EXERCISE 3:
	 * 
	 * Implement the Heap Sort Algorithm to sort an array of elements given as
	 * parameter.
	 * 
	 * Note: You must do this IN PLACE, DO NOT USE AN EXTENRAL HEAP/PQ
	 *
	 * @param arr Array to sort
	 * @return The Sorted Array
	 */
	public static int[] heapSort(int[] arr) {
		int size = arr.length;

		for (int i = size / 2 - 1; i >= 0; i--) {
			heapify(arr, size, i);
		}

		for (int i = size - 1; i > 0; i--) {
			int temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;

			heapify(arr, i, 0);
		}

		return arr;
	}

	private static void heapify(int[] arr, int n, int i) {
		int largest = i;
		int leftChild = 2 * i + 1;
		int rightChild = 2 * i + 2;

		if (leftChild < n && arr[leftChild] > arr[largest]) {
			largest = leftChild;
		}

		if (rightChild < n && arr[rightChild] > arr[largest]) {
			largest = rightChild;
		}

		if (largest != i) {
			int temp = arr[i];
			arr[i] = arr[largest];
			arr[largest] = temp;

			heapify(arr, n, largest);
		}
	}
}