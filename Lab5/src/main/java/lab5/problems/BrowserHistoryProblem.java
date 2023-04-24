package lab5.problems;
import java.util.ArrayList;
import java.util.List;

import lab5.util.dataStructures.LinkedStack;
import lab5.util.interfaces.Stack;

public class BrowserHistoryProblem {
	/**
	 * TODO EXERCISE 1:
	 * 
	 * In your Internet browser (e.g. Google Chrome), you can use the "back" and "forward" arrows 
	 * to navigate through your browsing history.
	 * 
	 * Implement a non-member method called browserHistory(String[] clicks) that takes 
	 * a list of strings called clicks containing a mix of pages visited and back/forward arrow clicks, 
	 * and returns a list of the pages visited in order.
	 * 
	 * It may be helpful to open a new Google Chrome tab and experiment with using the backward and forward buttons.
	 * 
	 * Example: 
	 * Assume clicks = {"Google", "Facebook", "Instagram", "<", ">"}, 
	 * then a call to browserHistory(clicks) returns {"Google", "Facebook", "Instagram", "Facebook", "Instagram"} 
	 * because the user went backward to Facebook, then forward to Instagram.
	 * 
	 * Here are some additional edge cases to consider:
	 * 
	 * 1) The user may hit the back or forward buttons multiple times in a row.
	 * 2) If the user tries to click the "back" or "forward" arrow even though 
	 *    there is nowhere to go / it is greyed out, keep them on the same page without reloading the page.
	 * 3) The user might refresh the page / visit the same page multiple times in a row. 
	 *    Count these as a single visit.
	 * 4) The user might visit the same page multiple times, but not necessarily in a row. Consider these distinct.
	 * 
	 * Hint: Store the result and its values using an ArrayList<String> (import from java.util) as you 
	 * make your solution and return them as an array of strings using result.toArray(new String[result.size()])
	 * 
	 * WARNING: You MUST use a stack, implementations that use indices (or pointers) will receive ZERO credit.
	 * 
	 * @param clicks - Array of strings denoting the web pages and clicks user made in their browser
	 * @return The Array of strings with clicks made with the forward and back buttons replaced with 
	 * 		   the corresponding page
	 */
	public String[] browserHistory(String[] clicks) {
		Stack<String> backStack = new LinkedStack<>();
		Stack<String> forwardStack = new LinkedStack<>();
		List<String> resultList = new ArrayList<>();
		String currentPage = clicks[0];
		resultList.add(currentPage);

		for (int i = 1; i < clicks.length; i++) {
			String click = clicks[i];
			if (click.equals("<")) {    // if the click is a back button
				if (!backStack.isEmpty()) {
					forwardStack.push(currentPage);
					currentPage = backStack.pop();
					resultList.add(currentPage);
				} else {
					continue; // Don't add the website
				}
			} else if (click.equals(">")) {    // if the click is a forward button
				if (!forwardStack.isEmpty()) {
					backStack.push(currentPage);
					currentPage = forwardStack.pop();
					resultList.add(currentPage);
				} else {
					continue; // Don't add the website
				}
			} else {    // if the click is a page
				if (!click.equals(currentPage)) {    // if the page is not the same as the current page
					backStack.push(currentPage);
					currentPage = click;
					resultList.add(currentPage);
					forwardStack.clear();
				}
			}
		}
		return resultList.toArray(new String[resultList.size()]);
	}
}