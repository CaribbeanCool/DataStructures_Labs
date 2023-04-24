package lab5.problems;

import java.util.Comparator;

import lab5.util.dataStructures.LinkedStack;
import lab5.util.interfaces.Stack;

public class StackSortProblem<E extends Comparable<E>> {
	
	/********************************************************
	 * DO NOT REMOVE NOR MODIFY, TESTS WILL FAIL.		    *	
	 * YOU MUST USE THE COMPARATOR FOR YOUR IMPLEMENTATION. *
	 ********************************************************/
	private Comparator<E> comparator = (o1, o2) -> o1.compareTo(o2);
	
	/**
	 * TODO EXERCISE 2:
	 * 
	 * Implement a non-member method called stackSort. 
	 * The method takes as a parameter an array of integers and returns the array sorted in increasing order.
	 * For example consider we have an array  int[] A = {9, 11, 15, 11, 1, -1, 3, 11};
	 * 
	 * In order to sort values, we will use two stacks which will be called the left and right stacks. 
	 * The values in the stacks will be sorted (in non-descending order) 
	 * and the values in the left stack will all be less than or equal to the values in the right stack. 
	 * 
	 * The following example illustrates a possible state for our two stacks:
	 * 
	 * 							[  ] [ 9]
	 * 							[  ] [11] 
	 * 							[ 3] [11]
	 * 							[ 1] [11]
	 * 							[-1] [15]
	 * 
	 * Notice that the values in the left stack are sorted so that the smallest value is at the bottom of the stack. 
	 * The values in the right stack are sorted so that the smallest value is at the top of the stack.
	 * If we read the values up the left stack and then down the right stack, 
	 * we get int[] sortedA = {-1, 1, 3, 9, 11, 11, 11, 15}; which is in sorted order.
	 * 
	 * WARNING: YOU MUST USE THE TWO STACKS DECLARED FOR YOU, IMPLEMENTATIONS THAT 
	 * USE Arrays.sort(); OR ANY SORTING ALGORITHM (Bubble Sort, Selection Sort, etc.) 
	 * TO AVOID USING STACKS WILL NOT BE GIVEN CREDIT.
	 * 
	 * NOTE: YOU MUST USE THE COMPARATOR DECLARED AS A PRIVATE FIELD IN THIS CLASS 
	 * TO COMPARE ELEMENTS, NOT THE elm.compareTo() FROM THE Comparable<E> INTERFACE. 
	 * (The Comparator declared as a private field already uses it, that's why)
	 *  
	 * @param array - Array to sort
	 * @return The input array, but in sorted order
	 */
	@SuppressWarnings("unchecked")
	public Comparable<E>[] stackSort(Comparable<E>[] array) {

		/*********************************************
		 * DO NOT REMOVE THESE VARIABLE DECLARATIONS *
		 *********************************************/
		Comparable<E>[] result = new Comparable[array.length];

		Stack<Comparable<E>> left = new LinkedStack<>();
		Stack<Comparable<E>> right = new LinkedStack<>();

		/* TODO ADD YOUR CODE HERE */
		for (int i = 0; i < array.length; i++) {
			left.push(array[i]);
		}
		Comparable<E> min;
		while (!left.isEmpty()) {
			min = left.pop();
			while (!right.isEmpty() && comparator.compare((E) right.top(), (E) min) > 0) {
				left.push(right.pop());
			}
			right.push(min);
		}
		for (int i = result.length - 1; i >= 0 && !right.isEmpty(); i--) {
			result[i] = right.pop();
		}
		return result;
	}
}