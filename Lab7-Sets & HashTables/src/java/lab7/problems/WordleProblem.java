package lab7.problems;

import lab7.interfaces.List;
import lab7.interfaces.Map;
import lab7.util.LinkedList;
import lab7.util.hashTable.HashTableOA;
import lab7.util.hashTable.SimpleHashFunction;

public class WordleProblem {
	/**
	 * TODO EXERCISE 2:
	 * 
	 * In the game of Wordle, you try to guess the secret word. 
	 * Every Time you guess a word, you receive a response 
	 * consisting of green, yellow and black tiles.
	 * 
	 * The rules are as follows:
	 * 
	 * 1) A green tile at position i indicates that the letter at position i 
	 * 	  in the guess word is also in the secret word at that position.
	 * 2) A yellow tile at position i indicates that the letter at position i 
	 *    in the guess word is in the secret word, but at a different position.
	 * 3) A black tile at position i indicates that the letter at position i 
	 *    in the guess word is not in the secret word at all.
	 * 
	 * Write a static non-member method called wordle(String guess, String secretWord) 
	 * that takes a string guess and a string secretWord and returns the response as 
	 * as string "G" for green, "Y" for yellow and "B" for black.
	 * 
	 * Assumptions:
	 * 1) The input words are the same length
	 * 2) The input words are all in upper-case
	 * 3) The input words contain only letters (no numbers, symbols, etc.)
	 * 4) The input words will not be empty strings
	 * 
	 * @param guess  		Input guess user made
	 * @param secretWord 	Input secret word user must guess
	 * @return				Response as a string using "G", "Y" or "B"
	 */
	public String wordle(String guess, String secretWord) {
		StringBuilder response = new StringBuilder();

		// Use a hash table to count the occurrences of each character in the secret
		// word
		Map<Character, Integer> charCounts = new HashTableOA<>(11, new SimpleHashFunction<>());
		for (int i = 0; i < secretWord.length(); i++) {
			char c = secretWord.charAt(i);
			if (charCounts.containsKey(c)) {
				charCounts.put(c, charCounts.get(c) + 1);
			} else {
				charCounts.put(c, 1);
			}
		}

		// Check each character in the guess word
		for (int i = 0; i < guess.length(); i++) {
			char c = guess.charAt(i);

			// Case 1: Character matches in both position and value
			if (c == secretWord.charAt(i)) {
				response.append("G");
				charCounts.put(c, charCounts.get(c) - 1);
			}

			// Case 2: Character matches in value but not in position
			else if (charCounts.containsKey(c) && charCounts.get(c) > 0) {
				response.append("Y");
				charCounts.put(c, charCounts.get(c) - 1);
			}

			// Case 3: Character does not match in value or position
			else {
				response.append("B");
			}
		}

		return response.toString();
	}
}